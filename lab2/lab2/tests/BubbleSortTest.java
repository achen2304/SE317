import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class BubbleSortTest {

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
        // Test 1: Already sorted array - passes because no swaps happen,
        // so the missing final pass has no effect
        int[] input = { 1, 2, 3, 4, 5, 6, 7 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        BubbleSort.bubbleSort(x);
        printResult("Test 1: Does Not Reveal Fault (Already Sorted)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testDoesNotExecuteFault2() {
        // Test 2: Partially sorted array - passes because elements happen to be
        // in correct positions after n-2 passes even though the final pass is missing
        int[] input = { 2, 1, 4, 3, 5 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5 };
        BubbleSort.bubbleSort(x);
        printResult("Test 2: Does Not Reveal Fault (Partially Sorted)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testExecutesFaultTest3() {
        // Test 3: Fully reversed array - fails because it needs all n-1 passes
        // to complete, and with only n-2 passes the first elements remain unsorted
        int[] input = { 7, 6, 5, 4, 3, 2, 1 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        BubbleSort.bubbleSort(x);
        printResult("Test 3: Shows Fault (Fully Reversed)", input, expected, x);
        assertArrayEquals(expected, x);
    }

    @Test
    public void testExecutesFaultTest4() {
        // Test 4: Partially reversed array - fails because it also needs all n-1
        // passes to complete, leaving early elements unsorted
        int[] input = { 6, 7, 5, 4, 3, 2, 1 };
        int[] x = input.clone();
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };
        BubbleSort.bubbleSort(x);
        printResult("Test 4: Shows Fault (Worst Case)", input, expected, x);
        assertArrayEquals(expected, x);
    }
}
