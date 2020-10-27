public class Test02
{
    public static void main(String[] args)
    {
        /**************计算根号K以内的素数***************/
        boolean bool =false;
        int sum=0;
        int[]su=new int[100];
        int l=(int) Math.sqrt(100);  //求得100的开根号值，并赋予l
        for(int m=2;m<=l;m++) {
            for(int n=2;n<=(int)Math.sqrt(m);n++)
            {
                bool=false;
                if(m%n==0) {
                    bool=true;
                    break;
                }
            }
            if(bool==false) {
                su[m-2]=m;
                sum++;             //sum用于统计根号100以内素数的数目，用于下面for循环
            }
        }
        int m=100/2;
        int[] a=new int[m+1];      //100/2=m,因此这个用于剔除倍数的数组下标定为m
        int[] b=new int[100-1];      //用于存储2-100

        /*****************在数组b中存储0-100*************/
        System.out.print("2-100的原数组：");
        for(int i=0;i<b.length;i++) {
            b[i]=i+2;
            //System.out.print(b[i]+" " );
        }
        System.out.println();
        /*****************将数组b中2、3、5......的倍数剔除*************/
        for(int q=0;q<sum;q++) {
            for(int j=2;j<m+1;j++)
            {
                a[j]=su[q]*j;
                System.out.print(a[j] + "");
                for(int i=0;i<b.length;i++) {
                    if(a[j]==b[i])
                        b[i]=0;
                }
                if(su[q]*j>100)break;
            }
            System.out.println();
        }
        /*****************显示100以内的素数*************/
        System.out.print("100+以内的素数:");
        for(int i=0;i<b.length;i++) {
            if(b[i]!=0)
                System.out.print(b[i]+" ");
        }
    }
}
