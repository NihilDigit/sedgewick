package ch01;

import edu.princeton.cs.algs4.StdOut;

public class ex1114 {
    public static int lg(int N) {
        int counter = 0;
        while (N>=2) {
            N /= 2;
            counter++;
        }
        return counter;
    }

    public static void main(String[] args) {
        StdOut.println(lg(7));
    }
}
