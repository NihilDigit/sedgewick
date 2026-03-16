package ch01;

public class ex1115 {
    public static int[] histogram(int[] a, int M) {
        int[] result = new int[M];

        for (int i = 0; i < a.length; ++i) {
            result[a[i]]++;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 1, 2, 3, 3, 3, 4};
        histogram(a, 5);
    }

}
