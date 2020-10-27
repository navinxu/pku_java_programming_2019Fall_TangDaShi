/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：Week08Homework.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月29日
*   Description ：
================================================================*/
import java.net.URL;
import java.io.*;
import java.util.*;

class Week08Homework
{
    private static long begin = 0;
    private static long end = 0;
    private static long timeElapsed = 0;

    public static void main(String[] args)
        throws Exception
    {
        final URL[] urls = {
            new URL("https://www.pku.edu.cn"),
            new URL("https://www.baidu.com"),
            new URL("https://www.sina.com.cn"),
            new URL("https://chengxuzhilu.com")
        };
        final String[] files = {
            "pku.htm",
            "baidu.htm",
            "sina.htm",
            "study.htm",
        };

        /* for(int idx=0; idx<urls.length; idx++){ */
        /*     try{ */
        /*         System.out.println( urls[idx] ); */
        /*         download( urls[idx], files[idx]); */
        /*     }catch(Exception ex){ */
        /*         ex.printStackTrace(); */
        /*     } */
        /* } */

        for (int idx = 0; idx < urls.length; ++ idx) {
                final int index = idx;
                new Thread(
                    () -> {
                        try {
                            Week08Homework.download(urls[index], files[index]);
                            Thread.sleep(1000);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (index == urls.length - 1)
                                System.out.println("Time elapsed: " + timeElapsed + " ms");
                        }
                    }
               ).start();
        }
    }
    synchronized static void download( URL url, String file)
        throws IOException
    {
        System.out.println(url + " is downloading...");
        begin = new Date().getTime();
        try(InputStream input = url.openStream();
            OutputStream output = new FileOutputStream(file))
        {
            byte[] data = new byte[1024];
            int length;
            while((length=input.read(data))!=-1){
                output.write(data,0,length);
            }
        }
        end = new Date().getTime();
        timeElapsed += end - begin;
        System.out.println(url + " has been downloaded...");
    }
}
