/**
 * Bubble Sort - Faulty Version
 * Outer loop runs i < n - 2 instead of i < n - 1.
 * It has one less pass than required, so the two smallest
 * elements at the front of the array may never get fully sorted.
 */
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 2; i++) { // FAULT: should be n - 1
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
