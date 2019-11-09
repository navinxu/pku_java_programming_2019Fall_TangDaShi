/**
 *
 * 本程序会生成一个本地文件，以保存爬取下来的数据
 *
 * 本程序仅对豆瓣电影上的《我和我的祖国》资料有效，
 * 若用于其他影片的爬取，如果没有得到理想的结果，
 * 本人概不负责！
 */
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.*;
public class Week09Homework2 {

    public void start() {
        String strUrl = "https://movie.douban.com/subject/32659890/";
        System.out.println("正在下载网页...");
        String content = getContentFromUrl( strUrl );

        this.writeToFile("我和我的祖国-豆瓣资料.txt", content);
    }

    public void writeToFile(String filename, String content) {
        System.out.println("正在解析网页...");

        try {
            PrintWriter out = getOutputStream(filename);

            // 导演
            out.println("导演：\n\t" + this.getDirectors(content));
            // 编剧
            out.println("编剧：\n\t" + this.getPlaywright(content));
            // 主演
            out.println("主演：\n\t" + this.getActors(content));
            // 电影类型
            out.println("电影类型：\n\t" + this.getMovieType(content));
            // 制片国家/地区
            out.println("制片国家/地区：\n\t" + this.getRegion(content));
            // 语言
            out.println("语言：\n\t" + this.getLanguage(content));
            // 上映日期
            out.println("上映日期：\n\t" + this.getReleaseDate(content));
            // 片长
            out.println("片长：\n\t" + this.getMins(content));
            // 又名
            out.println("又名：\n\t" + this.getNickname(content));
            // IMDb 链接
            out.println("IMDb 链接：\n\t" + this.getIMDb(content));
            // 豆瓣评分
            out.println("豆瓣评分：\n\t" + this.getDoubanRating(content));

            out.close();
            System.out.println("成功获取所有信息！");
            System.out.println("成功写入文件！");
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static PrintWriter getOutputStream(String filename) throws IOException{
        File file = new File(filename);

        PrintWriter out = new PrintWriter(
                new FileWriter(file)
                );

        return out;
    }

    /**
     *
     *
     * 豆瓣评分
     */
    public String getDoubanRating(String html) {
        System.out.println("正在获取豆瓣评分...");
        String rating = "";

        Pattern p = Pattern.compile("<strong class=\"ll rating_num\" property=\"v:average\">([^<]+)</strong>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            rating = m.group(1);
        }

        return rating;
    }

    /**
     *
     *
     * IMDb 链接
     */
    public String getIMDb(String html) {
        System.out.println("正在获取 IMDb 链接...");
        String imdb = "";

        Pattern p = Pattern.compile("<span class=\"pl\">IMDb链接:</span> <a href=\"https://www.imdb.com/title/[^\"]+\" target=\"_blank\" rel=\"nofollow\">([^<]+)</a>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            imdb = m.group(1);
        }

        return imdb;
    }

    /**
     *
     *
     * 又名
     */
    public String getNickname(String html) {
        System.out.println("正在获取电影昵称...");
        String nickname = "";

        Pattern p = Pattern.compile("<span class=\"pl\">又名:</span> ([^<]+)<br/>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            nickname = m.group(1);
        }

        return nickname;
    }

    /**
     *
     * 
     * 片长
     */
    public String getMins(String html) {
        System.out.println("正在获取电影片长...");
        String mins = "";

        Pattern p = Pattern.compile("<span class=\"pl\">片长:</span> <span property=\"v:runtime\" content=\"[^\"]+\">([^<]+)</span>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            mins = m.group(1);
        }

        return mins;
    }

    /**
     *
     *
     * 上映日期
     */
    public String getReleaseDate(String html) {
        System.out.println("正在获取上映日期...");
        String releaseDate = "";

        Pattern p = Pattern.compile("<span class=\"pl\">上映日期:</span> <span property=\"v:initialReleaseDate\" content=\"[^\"]+\">([^<]+)</span>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            releaseDate = m.group(1);
        }

        return releaseDate;
    }

    /**
     *
     *
     * 语言
     */
    public String getLanguage(String html) {
        System.out.println("正在获取电影语言...");
        String lang = "";

        Pattern p = Pattern.compile("<span class=\"pl\">语言:</span> ([^<]+)<br/>");
        Matcher m = p.matcher(html);
        
        if (m.find()) {
            lang = m.group(1);
        }

        return lang;
    }

    /**
     *
     *
     * 制片国家/地区
     */
    public String getRegion(String html) {
        System.out.println("正在获取制片国家或者地区...");
        String region = "";

        Pattern p = Pattern.compile("<span class=\"pl\">制片国家/地区:</span> ([^<]+)<br/>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            region = m.group(1);
        }

        return region;
    }

    /**
     *
     *
     * 获取影片类型
     */
    public String getMovieType(String html) {
        System.out.println("正在获取影片类型...");

        String movieType = "";
        String content = this.getTargetHTML(html, "<span class=\"pl\">类型:</span> <span property=\"v:genre\">[^<]+</span>");

        if (!content.isEmpty()) {
            Pattern p = Pattern.compile("<span property=\"v:genre\">([^<]+)</span>");
            Matcher m = p.matcher(content);

            if (m.find()) {
                movieType = m.group(1);
            }
        }

        return movieType;
    }

    /**
     *
     *
     * 获取主演列表
     */
    public String getActors(String html) {
        System.out.println("正在获取主演列表...");
        StringBuilder actors = new StringBuilder("");

        String content = getTargetHTML(html, "<span class='pl'>主演</span>: <span class='attrs'>(?:(?:<a href=\"/celebrity/\\d+/\" rel=\"v:starring\">[^<]+</a>)|( / ))+</span>");

        if (!content.isEmpty()) {
            Pattern p = Pattern.compile("<a href=\"/celebrity/\\d+/\" rel=\"v:starring\">([^<]+)</a>");
            Matcher m = p.matcher(content);

            boolean isFirst = true;
            while (m.find()) {
                if (!isFirst)
                    actors.append(", ");
                isFirst = false;
                actors.append(m.group(1));
            }
        }

        return actors.toString();
    }

    /**
     *
     *
     * 获取编剧列表
     */
    public String getPlaywright(String html) {
        System.out.println("正在获取编剧列表...");
        StringBuilder playwrights = new StringBuilder("");

        String content = this.getTargetHTML(html, "<span class='pl'>编剧</span>: <span class='attrs'>(?:(?:<a href=\"/celebrity/\\d+/\">[^<]+</a>)|(?: / ))+</span>");
        if (!content.isEmpty()) {
            Pattern p = Pattern.compile("(?:<a href=\"/celebrity/\\d+/\">([^<]+)</a>)+");
            Matcher m = p.matcher(content);

            boolean isFirst = true;
            while (m.find()) {
                if (!isFirst)
                    playwrights.append(", ");
                isFirst = false;
                playwrights.append(m.group(1));
            }
        }

        return playwrights.toString();
    }

    /**
     *
     *
     * 获取导演列表
     */
    public String getDirectors(String html) {
        System.out.println("正在获取导演列表...");
        StringBuilder directors = new StringBuilder("");

        //Pattern p = Pattern.compile("<span class='pl'>导演</span>: <span class='attrs'>(?:(?:<a href=\"/celebrity/\\d+/\" rel=\"v:directedBy\">([^<]+)</a>)|(?: / ))+</span>");
        String content = this.getTargetHTML(html, "<span class='pl'>导演</span>: <span class='attrs'>(?:(?:<a href=\"/celebrity/\\d+/\" rel=\"v:directedBy\">[^<]+</a>)|(?: / ))+</span>");

        if (!content.isEmpty()) {
            Pattern p = Pattern.compile("(?:<a href=\"/celebrity/\\d+/\" rel=\"v:directedBy\">([^<]+)</a>)+");
            Matcher m = p.matcher(content);

            boolean isFirst = true;
            while (m.find()) {
                if (!isFirst)
                    directors.append(", ");
                isFirst = false;
                directors.append(m.group(1));
            }
        }

        return directors.toString();

    }

    /**
     *
     * 获取目标部分的 HTML
     * 从而继续进行正则表达式解析
     */
    public String getTargetHTML(String html, String pattern) {
        if (html.isEmpty() || pattern.isEmpty()) {
            return "";
        }

        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(html);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }


    public static String getContentFromUrl( String strUrl )
    {
        try {
            URL url = new URL(strUrl);
            InputStream stream = url.openStream();

            String content = readAll( stream,"UTF-8" ); //常见的编码包括 GB2312, UTF-8
            return content;

        }catch ( MalformedURLException e) {
            System.out.println("URL格式有错" );
        }catch (IOException ioe) {
            System.out.println("IO异常" );
        }
        System.out.println("成功下载网页 HTML 代码");
        return "";
    }

    public static String readAll( InputStream stream, String charcode ) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charcode));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line+"\n");
        }
        return sb.toString();
    }

    public static void main( String[] args){
        Week09Homework hw = new Week09Homework();
        hw.start();
    }
}

