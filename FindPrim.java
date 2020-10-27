import java.util.ArrayList;



public class FindPrim {

    public static void main(String[] args) {

        int[] arr = new int[101];

        for (int i = 2; i<=100; i++){

            arr[i] = i;

        }



        int p,q;

        for (int i = 2; i <= 100; i++){

            if (arr[i] != 0){

                for (q=2; (p=q*i) <= 100; q++){

                    arr[p] = 0;

                }

            }

        }



        //使用增强for将arr中的元素输出

        for(int i : arr){

            if (i != 0){

                System.out.print(i + " ");

            }

        }

    }

}
