/**
 * Bubble Sort - Fixed Version
 *
 * FIX: changed outer loop from i < n - 2 to i < n - 1 so that
 * all n-1 passes are performed and every element reaches its correct position.
 */
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {          // FIXED: was n - 2
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
