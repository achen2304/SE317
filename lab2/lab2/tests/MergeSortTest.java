import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class MergeSortTest {

    private static void printResult(String testName, int[] input, int[] expected, int[] actual) {
        System.out.println("========================================");
        System.out.println(testName);
        System.out.println("========================================");
        System.out.println("Input:    " + Arrays.toString(input));
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual:   " + Arrays.toString(actual));
        boolean passed = Arrays.equals(expected, actual);
        System.out.println("Status:   " + (passed ? "PASS" : "FAIL"));
        System.out.println();
    }

    @Test
    public void testDoesNotExecuteFault1() {
        // Test 1: Already sorted array - passes because the right half is already
        // sorted, so it never gets processed by the missing recursive call
        int[] input = { 1, 2, 3, 4, 5, 6, 7 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        MergeSort.mergeSort(x, 0, x.length - 1);
        printResult("Test 1: Does Not Reveal Fault (Already Sorted)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testDoesNotExecuteFault2() {
        // Test 2: Partially reversed array - passes because when the right half
        // [3] is skipped, merging [1,2] with [3] still produces the correct result
        int[] input = { 2, 1, 3 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3 };
        MergeSort.mergeSort(x, 0, x.length - 1);
        printResult("Test 2: Does Not Reveal Fault (Partially Reversed)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testExecutesFaultTest3() {
        // Test 3: Fully reversed array - fails because the right half is unsorted
        // and the missing recursive call never sorts it, resulting in an incorrectly
        // merged final array
        int[] input = { 7, 6, 5, 4, 3, 2, 1 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        MergeSort.mergeSort(x, 0, x.length - 1);
        printResult("Test 3: Shows Fault (Fully Reversed)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testExecutesFaultTest4() {
        // Test 4: Random Numbers with unsorted right half - fails because the missing
        // recursive call doesn't sort the right half, leaving it unsorted for the final
        // merge
        int[] input = { 5, 3, 8, 1, 9, 2, 7 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 5, 7, 8, 9 };
        MergeSort.mergeSort(x, 0, x.length - 1);
        printResult("Test 4: Shows Fault (Random Numbers)", input, expected, x);
        assertArrayEquals(expected, x);
    }
}
