package ch01;

import edu.princeton.cs.algs4.StdOut;

public class Fibonacci {
    public static long F(int N) {
        if (N == 0) return 0;
        if (N == 1) return 1;
        return F(N-1) + F(N-2);
    }

    public static long improvedF(int N) {
        if (N == 0) return 0;
        long[] results = new long[N + 1]; // 下标 0..N
        results[0] = 0; // F(0) = 0
        results[1] = 1; // F(1) = 1
        for (int i = 2; i <= N; i++)
            results[i] = results[i - 1] + results[i - 2];
        return results[N];
    }

    public static void main(String[] args) {
        for (int N = 0; N < 100; N++)
            StdOut.println(N + " " + F(N));
    }
}
