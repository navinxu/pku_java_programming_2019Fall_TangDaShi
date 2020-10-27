/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：Test.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月12日
*   Description ：
================================================================*/
public class Sushu
{
public static void main(String[] args)
{
    boolean []a=new boolean[101];
    for(int i=1;i<=100;i++)
    {
        if(i%2==0||i%3==0||i%5==0||i%7==0)
        a[i]=false;
        else
        a[i]=true;
    }
    for(int i=1;i<=100;i++)
    {
    if(a[i])
    System.out.println(i);
    }
}
}
