package ch01;

import edu.princeton.cs.algs4.StdOut;

public class ex1111 {
    public static void main(String[] args) {
        boolean[][] a = {
            { true, false, true,  false },
            { false, true, false, true  },
            { true, true, false, false  }
        };

        String tableTop = "  ";
        for (int i = 0; i < a[0].length; i++)
            tableTop += ((i+1) + " ");
        StdOut.println(tableTop);

        for (int i = 0; i < a.length; i++) {
            String tableLine = "";
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j])
                    tableLine += "* ";
                else
                    tableLine += "  ";
            }
            StdOut.println((i + 1) + " " + tableLine);
        }
    }
}
