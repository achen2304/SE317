/**
 * Quick Sort - Faulty Version
 *
 * FAULT: the recursive call for the right sub-array is missing.
 * This means the left side of the array will be sorted but
 * Not right one.
 */
public class QuickSort {

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            // FAULT: missing quickSort(arr, pi + 1, high)
        }
    }
}
