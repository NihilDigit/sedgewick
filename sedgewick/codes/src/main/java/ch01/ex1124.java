package ch01;

import edu.princeton.cs.algs4.StdOut;

public class ex1124 {
    public static int gcd(int p, int q) {
        if (q == 0)
            return p;
        else {
            StdOut.println("p: "+p+" "+"q: "+q);
            return gcd(q, p%q);
        }
    }

    public static void main(String[] args) {
        int p = Integer.parseInt(args[0]);
        int q = Integer.parseInt(args[1]);
        gcd(p, q);
    }

}
