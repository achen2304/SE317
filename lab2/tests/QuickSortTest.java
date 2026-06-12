import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class QuickSortTest {

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
        // Test 1: Sorted ascending array - passes because the pivot is always the
        // maximum, so the right partition is always empty and the missing recursive
        // call has no work
        int[] input = { 1, 2, 3, 4, 5, 6, 7 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        QuickSort.quickSort(x, 0, x.length - 1);
        printResult("Test 1: Does Not Reveal Fault (Already Sorted)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testDoesNotExecuteFault2() {
        // Test 2: Partially sorted array - passes because the pivot ends up being the
        // maximum, making the right subarray empty so the missing recursive call does
        // nothing
        int[] input = { 2, 1, 3 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3 };
        QuickSort.quickSort(x, 0, x.length - 1);
        printResult("Test 2: Does Not Reveal Fault (Partially Sorted)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testExecutesFaultTest3() {
        // Test 3: Fully reversed array - fails because the pivot is always the minimum,
        // so everything goes to the right partition which never gets sorted due to
        // the missing recursive call
        int[] input = { 7, 6, 5, 4, 3, 2, 1 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        QuickSort.quickSort(x, 0, x.length - 1);
        printResult("Test 3: Shows Fault (Fully Reversed)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testExecutesFaultTest4() {
        // Test 4: Random numbers - fails because it creates non-empty right partitions
        // with unsorted elements that never get sorted without the missing recursive
        // call
        int[] input = { 3, 1, 4, 2, 6, 5, 7 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        QuickSort.quickSort(x, 0, x.length - 1);
        printResult("Test 4: Shows Fault (Random numbers)", input, expected, x);
        assertArrayEquals(expected, x);
    }
}
