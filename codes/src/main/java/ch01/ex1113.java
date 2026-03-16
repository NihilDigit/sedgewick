package ch01;

import edu.princeton.cs.algs4.StdOut;

public class ex1113 {
    public static void main(String[] args) {
        int[][] a = {
            { 1,  2,  3,  4 },
            { 5,  6,  7,  8 },
            { 9, 10, 11, 12 }
        };

        int bHeight = a[0].length;  // a的列数 = b的行数 = 4
        int bWidth = a.length;     // a的行数 = b的列数 = 3
        int[][] b = new int[bHeight][bWidth];

        for (int i = 0; i < b.length; ++i) {
            for (int j = 0; j < b[i].length; ++j) {
                b[i][j] = a[j][i];
            }
        }

        for (int i = 0; i < b.length; ++i) {
            String output = "";
            for (int j = 0; j < b[i].length; ++j) {
                output += b[i][j] + " ";
            }
            StdOut.println(output);
        }
    }
}
