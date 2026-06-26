package lab5;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Part2bStyle2Test {

    private final int inputHumidity;
    private final int inputTemperature;
    private final int expectedHumidity;
    private final int expectedTemperature;
    private final String expectedStatus;

    private Device device;

    @Parameters(name = "Pair {index}: temp={1}°F, hum={0}%")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { 45, 72, 45, 72, "OK"   },
            { 50, 75, 50, 75, "OK"   },
            { 60, 78, 60, 78, "High" },
            { 55, 76, 55, 76, "OK"   },
            { 50, 73, 50, 73, "OK"   },
            { 48, 73, 48, 73, "OK"   },
            { 42, 80, 42, 80, "OK"   },
        });
    }

    public Part2bStyle2Test(int inputHumidity, int inputTemperature,
                            int expectedHumidity, int expectedTemperature,
                            String expectedStatus) {
        this.inputHumidity       = inputHumidity;
        this.inputTemperature    = inputTemperature;
        this.expectedHumidity    = expectedHumidity;
        this.expectedTemperature = expectedTemperature;
        this.expectedStatus      = expectedStatus;
    }

    @Before
    public void setUp() {
        device = new Device();
        device.readSensors(inputHumidity, inputTemperature);
    }

    @Test
    public void singlePairOutputsAreCorrect() {
        int actualHum    = device.getCurrentHumidity();
        int actualTemp   = device.getCurrentTemperature();
        String actualStatus = device.getHumidityStatus();

        System.out.println("========================================");
        System.out.printf("Input: temp=%d°F, humidity=%d%%%n", inputTemperature, inputHumidity);
        System.out.println("========================================");
        System.out.printf("%-22s Expected: %-8s Actual: %-8s %s%n", "Current Humidity",    expectedHumidity,    actualHum,    expectedHumidity == actualHum    ? "PASS" : "FAIL");
        System.out.printf("%-22s Expected: %-8s Actual: %-8s %s%n", "Current Temperature", expectedTemperature, actualTemp,   expectedTemperature == actualTemp ? "PASS" : "FAIL");
        System.out.printf("%-22s Expected: %-8s Actual: %-8s %s%n", "Humidity Status",     expectedStatus,      actualStatus, expectedStatus.equals(actualStatus) ? "PASS" : "FAIL");
        System.out.println();

        assertEquals(expectedHumidity,    actualHum);
        assertEquals(expectedTemperature, actualTemp);
        assertEquals(expectedStatus,      actualStatus);
    }
}
