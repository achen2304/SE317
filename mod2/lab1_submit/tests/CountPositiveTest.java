import org.junit.Test;
import static org.junit.Assert.*;

public class CountPositiveTest {

    @Test
    public void testGivenFailingTest() {
        CountPositive cp = new CountPositive();
        int[] x = { -4, 2, 0, 2 };
        assertEquals(2, cp.countPositive(x));
    }

    @Test
    public void testDoesNotExecuteFault() {
        CountPositive cp = new CountPositive();
        int[] x = { -4, 2, 3, 2 };
        assertEquals(3, cp.countPositive(x));
    }

    @Test
    public void testExecutesFaultNoError() {
        CountPositive cp = new CountPositive();
        int[] x = { -3, -1, -4 };
        assertEquals(0, cp.countPositive(x));
    }
}
