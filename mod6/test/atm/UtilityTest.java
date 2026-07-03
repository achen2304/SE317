package atm;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UtilityTest {
    private UtilityStorage utilityStorage;
    private final String utilFile = "test_utility_data_2.txt";

    @Before
    public void setUp() {
        new File(utilFile).delete();
        utilityStorage = new UtilityStorage(utilFile);
    }

    @Test
    public void testInitialization() throws IOException {
        utilityStorage.initializeDefaultsIfMissing();
        List<String> history = utilityStorage.getHistory();
        
        assertFalse(history.isEmpty());
        assertEquals("January Bill: $24.00", history.get(0));
    }

    @Test
    public void testAppendPayment() throws IOException {
        utilityStorage.initializeDefaultsIfMissing();
        utilityStorage.appendPayment(50.0, 5);
        
        List<String> history = utilityStorage.getHistory();
        assertTrue(history.get(history.size() - 2).contains("Payment: $50.00"));
        assertTrue(history.get(history.size() - 1).contains("Payment Date: Day 5"));
    }
}
