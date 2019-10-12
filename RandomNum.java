/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：RandomNum.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月12日
*   Description ：
================================================================*/
public class RandomNum {
    public static void main(String[] args) {
        int a[] = new int[7];


        for (int i = 0; i < 7; ++ i) {
            boolean flag = true;
            outer: while (flag) {
                a[i] = (int)(Math.random() * 36) + 1;

                for (int j = 0; j < i; ++ j) 
                    if (a[j] == a[i]) continue outer;
                flag = false;
            }
        }

        for (int elem : a)
            System.out.print(elem + " ");
    }
}

