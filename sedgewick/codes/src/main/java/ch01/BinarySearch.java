package ch01;

import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {
    public static int rank(int[] a, int key) {
        int lo = 0;
        int hi = a.length-1;
        while (hi >= lo) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == key)
                return mid;
            else if (a[mid] < key) {
                lo = mid + 1;
                mid = (hi + lo) / 2;
            }
            else if (a[mid] > key) {
                hi = mid - 1;
                mid = (hi + lo) / 2;
            }
        }
        return -1;
    }

    public static int rank_recursive(int key, int[] a, int lo, int hi, int depth) {
        int mid = lo + (hi - lo) / 2;
        String indent = " ".repeat(depth);
        StdOut.println(indent+lo+" "+hi);
        if (a[mid] == key)
            return mid;
        if (lo == hi)
            return -1;
        else if (a[mid] > key) {
            int new_hi = mid - 1;
            return rank_recursive(key, a, lo ,new_hi, depth+1);
        }
        else if (a[mid] < key) {
            int new_lo = mid + 1;
            return rank_recursive(key, a, new_lo, hi, depth+1);
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {10, 11, 12, 16, 18, 23, 29, 33, 48, 54, 57, 69, 77, 84, 98};
        int key = 23;
        StdOut.println("result: " + rank_recursive(key, a, 0, a.length-1, 0));
    }
}
