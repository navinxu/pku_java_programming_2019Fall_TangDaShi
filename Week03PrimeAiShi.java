/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：AiShi.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月11日
*   Description ：
用“埃氏筛法”求2～100以内的素数。2～100以内的数，先去掉2的倍数，再去掉3的倍数，再去掉5的倍数，……依此类推，最后剩下的就是素数。要求使用数组。

依照学术诚信条款，我保证此回答为本人原创，所有回答中引用的外部材料已经做了出处标记。

1（10分）
用“埃氏筛法”求2～100以内的素数。2～100以内的数，先去掉2的倍数，再去掉3的倍数，再去掉5的倍数，……依此类推，最后剩下的就是素数。

要求使用数组及增强的for语句。

提示：可以使用一个boolean类型的数组，所以“将某个数i去掉”，可以表示成a[i]=false。当然也可以使用其他方法。

请注意代码风格：类名、变量名的命名，以及必要注释等等；

请上传压缩后的源代码文件（为防上传失败，请同时把代码贴到文本框中）。

评分标准：

使用了数组（3分）；

使用增强的for语句（2分）；

筛法求素数的功能正确（5分）。
================================================================*/

public class Week03PrimeAiShi {
    public static void main(String[] args) {
        // 之所以是 100 + 1，是因为数组以 0 开头，
        // 且求素数的最大数字为 100
        boolean[] prime_flags = new boolean[100+1];

        // 将索引比 2 大的数字都设为“是素数”，
        // 因为 0 和 1 很明显不是素数，
        // 而 boolean 数组的默认值是 false，
        // 所以忽略 0 和 1。
        for (int i = 2; i <= 100; ++i)
            prime_flags[i] = true;

        /* for (boolean elem : prime_flags) */
        /*     System.out.print(elem + " "); */

        // 求出比 100 小，且最近 100 的素数
        boolean max_prime = true;
        int max = 0;
        outer2:
            for (int i = 100; i >= 90; -- i) {
                max_prime = true;
                for (int j = 2; j * j <= i; ++ j) {
                    if (i % j == 0) {
                        max_prime = false;
                        continue outer2;
                    }
                }

                if (max_prime) {
                    max = i;
                    break;
                }
            }
        //System.out.println(max);
        int k = 0;
        outer:
            // j 的作用是设置需要去掉倍数的素数
            for (int i = 4, j = 2; i <= 100; ++i) {
                // 每一次 ++j 都是一次从 i 到 100 的迭代
                // 当 j 是 max 时，就可以退出循环。
                if (j == max) {
                    break outer;
                }

                if (k == 0)
                    k = i;

                if (i != j && i % j == 0 && prime_flags[i] == true) {
                    prime_flags[i] = false;
                    /* System.out.print(j + " "); */
                }
                
                if (i == 100) {
                    // 当 i 迭代到 100 时，
                    // j 就要更新，
                    // 从 2 到 3, 从3 到 5, 从 5 到 7 等等。
                    if (j == 2) {
                        ++ j;
                    } else {
                        j += 2;
                        // 这里对题目不理解，到底是指去掉每个素数的倍数，
                        // 还是去掉每个比2大的奇数的倍数？
                        
                        // 若是前者，那么要解注释以下的代码，
                        // 才满足题目的要求
                        //
                        /* // 获取最近比上一个大的素数 */
                        /* boolean is_prime = true; */
                        /* while (true) { */
                        /*     is_prime = true; */
                        /*     for (int p = 3; p < j; ++p) { */
                        /*         if (j % p == 0) { */
                        /*             is_prime = false; */
                        /*             break; */
                        /*         } */
                        /*     } */
                        /*  */
                        /*     if (!is_prime) */
                        /*         j += 2; */
                        /*     else */
                        /*         break; */
                        /* } */
                    }
                    //System.out.println("\t" + j);
                    // 
                    /* System.out.print(j + " "); */
                    i = k;
                    k = 0;
                }
            }

        System.out.println();
        int cnt = 0;
        for (boolean elem : prime_flags) {
            if (elem == true)
                System.out.print(cnt + " ");
            ++ cnt;
        }
    }
}
