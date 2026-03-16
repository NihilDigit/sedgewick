package ch01;

import edu.princeton.cs.algs4.StdOut;

public class ex113 {
    public static void main(String[] args) {
        int item01 = Integer.parseInt(args[0]);
        int item02 = Integer.parseInt(args[1]);
        int item03 = Integer.parseInt(args[2]);

        if (item01 == item02 && item02 == item03)
            StdOut.println("equal");
        else
            StdOut.println("not equal");
    }
}
