import org.junit.Test;
import static org.junit.Assert.*;

public class AddOrPosTest {

    @Test
    public void testGivenFailingTest() {
        int[] x = { -3, -2, 0, 1, 4 };
        assertEquals(3, AddOrPos.oddOrPos(x));
    }

    @Test
    public void testDoesNotExecuteFault() {
        int[] x = { 2, 4, 6, -2 };
        assertEquals(3, AddOrPos.oddOrPos(x));
    }

    @Test
    public void testExecutesFaultNoError() {
        int[] x = { 1, 2, 3 };
        assertEquals(3, AddOrPos.oddOrPos(x));
    }
}
