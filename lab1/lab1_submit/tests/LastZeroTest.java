import org.junit.Test;
import static org.junit.Assert.*;

public class LastZeroTest {

    @Test
    public void testGivenFailingTest() {
        int[] x = { 0, 1, 0 };
        assertEquals(2, LastZero.lastZero(x));
    }

    @Test
    public void testExecutesFaultNoError() {
        int[] x = { 1, 2, 0 };
        assertEquals(2, LastZero.lastZero(x));
    }

    @Test
    public void testErrorNotFailure() {
        int[] x = { 0, 1, 2 };
        assertEquals(0, LastZero.lastZero(x));
    }
}
