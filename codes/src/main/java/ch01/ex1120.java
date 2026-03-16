package ch01;

public class ex1120 {
    public static double recursiveLn(int N) {
        if (N <= 1) return 0;
        else {
            return Math.log(N) + recursiveLn(N-1);
        }
    }

    public static void main(String[] args) {

    }
}
