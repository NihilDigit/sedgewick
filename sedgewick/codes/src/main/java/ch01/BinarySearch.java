package ch01;

public class BinarySearch {
    public static int rank(int[] a, int key) {
        int lo = 0;
        int hi = a.length-1;
        int mid = hi / 2;
        while (hi >= lo) {
            int mid =
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

    public static void main(String[] args) {
        int[] a = {10, 11, 12, 16, 18, 23, 29, 33, 48, 54, 57, 69, 77, 84, 98};
        int key = 23;
    }
}
