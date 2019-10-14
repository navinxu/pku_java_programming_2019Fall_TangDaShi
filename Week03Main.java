public class Week03Main {
 
    public static void main(String[] args) {
        int aa[] = new int[101];
        aa[2] = 0;
        int k = 2, tt = 0;
        while (tt < 101) {
            for (int i = 1; i < aa.length; i++) {
                if (i % k == 0 && i != k) {
                    aa[i] = 1;
                }
            }
            for (int i = 1; i < aa.length; i++) {
                if (i > k && aa[i] == 0) {
                    k = i;
                    break;
                }
            }
            tt++;
        }
        for (int i = 1; i < aa.length; i++) {
            if (aa[i] == 0) {
                System.out.printf("%d ", i);
            }
        }
    }
}
