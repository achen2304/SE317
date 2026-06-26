package lab5;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Part2bStyle1Test {

    private Device device;

    @Before
    public void setUp() {
        device = new Device();
        device.reset();

        device.readSensors(45, 72);
        device.readSensors(50, 75);
        device.readSensors(60, 78);
        device.readSensors(55, 76);
        device.readSensors(50, 73);
        device.readSensors(48, 73);
        device.readSensors(42, 80);
    }

    private static void printResult(String label, String expected, String actual) {
        System.out.printf("%-30s Expected: %-12s Actual: %-12s %s%n",
                label, expected, actual, expected.equals(actual) ? "PASS" : "FAIL");
    }

    @Test
    public void finalStateAfterSevenPairSequence() {
        System.out.println("========================================");
        System.out.println("Style 1: Final State After 7-Pair Sequence");
        System.out.println("Sequence: (72,45) (75,50) (78,60) (76,55) (73,50) (73,48) (80,42)");
        System.out.println("========================================");

        printResult("Current Humidity",    "42",          String.valueOf(device.getCurrentHumidity()));
        printResult("Max Humidity",        "60",          String.valueOf(device.getMaxHumidity()));
        printResult("Min Humidity",        "42",          String.valueOf(device.getMinHumidity()));
        printResult("Humidity Trend",      "Decreasing",  device.getHumidityTrend());
        printResult("Humidity Status",     "OK",          device.getHumidityStatus());
        printResult("Current Temperature", "80",          String.valueOf(device.getCurrentTemperature()));
        printResult("Max Temperature",     "80",          String.valueOf(device.getMaxTemperature()));
        printResult("Min Temperature",     "72",          String.valueOf(device.getMinTemperature()));
        printResult("Temperature Trend",   "Increasing",  device.getTemperatureTrend());
        System.out.println();

        assertEquals(42,           device.getCurrentHumidity());
        assertEquals(60,           device.getMaxHumidity());
        assertEquals(42,           device.getMinHumidity());
        assertEquals("Decreasing", device.getHumidityTrend());
        assertEquals("OK",         device.getHumidityStatus());
        assertEquals(80,           device.getCurrentTemperature());
        assertEquals(80,           device.getMaxTemperature());
        assertEquals(72,           device.getMinTemperature());
        assertEquals("Increasing", device.getTemperatureTrend());
    }
}
