import org.junit.Test;
import static org.junit.Assert.*;

public class FindLastTest {

    @Test
    public void testGivenFailingTest() {
        FindLast fl = new FindLast();
        int[] x = { 2, 3, 5 };
        assertEquals(0, fl.findLast(x, 2));
    }

    @Test
    public void testDoesNotExecuteFault() {
        FindLast fl = new FindLast();
        int[] x = { 2, 3, 5 };
        assertEquals(2, fl.findLast(x, 5));
    }

    @Test
    public void testExecutesFaultNoError() {
        FindLast fl = new FindLast();
        int[] x = { 2, 3, 5 };
        assertEquals(-1, fl.findLast(x, 6));
    }
}
