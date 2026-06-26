package lab5;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Part2aTest {

    private Device device;

    @Before
    public void setUp() {
        device = new Device();
    }

    private static void printResult(String testName, String input, String expected, String actual) {
        System.out.println("---------------------------------------");
        System.out.println(testName);
        System.out.println("---------------------------------------");
        System.out.println("Input:    " + input);
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println("Status:   " + (expected.equals(actual) ? "PASS" : "FAIL"));
        System.out.println();
    }

    private static void printResult(String testName, String input1, String input2, String expected, String actual) {
        System.out.println("---------------------------------------");
        System.out.println(testName);
        System.out.println("---------------------------------------");
        System.out.println("Input 1:  " + input1);
        System.out.println("Input 2:  " + input2);
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println("Status:   " + (expected.equals(actual) ? "PASS" : "FAIL"));
        System.out.println();
    }

    @Test
    public void currentHumidity_MidRange() {
        device.readSensors(50, 75);
        int actual = device.getCurrentHumidity();
        printResult("currentHumidity_MidRange",
                "humidity=50, temp=75",
                "50", String.valueOf(actual));
        assertEquals(50, actual);
    }

    @Test
    public void currentHumidity_LowerBound() {
        device.readSensors(0, 75);
        int actual = device.getCurrentHumidity();
        printResult("currentHumidity_LowerBound",
                "humidity=0, temp=75",
                "0", String.valueOf(actual));
        assertEquals(0, actual);
    }

    @Test
    public void maxHumidity_Updates() {
        device.readSensors(40, 70);
        device.readSensors(60, 70);
        int actual = device.getMaxHumidity();
        printResult("maxHumidity_Updates",
                "humidity=40, temp=70",
                "humidity=60, temp=70",
                "60", String.valueOf(actual));
        assertEquals(60, actual);
    }

    @Test
    public void maxHumidity_Unchanged() {
        device.readSensors(60, 70);
        device.readSensors(40, 70);
        int actual = device.getMaxHumidity();
        printResult("maxHumidity_Unchanged",
                "humidity=60, temp=70",
                "humidity=40, temp=70",
                "60", String.valueOf(actual));
        assertEquals(60, actual);
    }

    @Test
    public void minHumidity_Updates() {
        device.readSensors(60, 70);
        device.readSensors(40, 70);
        int actual = device.getMinHumidity();
        printResult("minHumidity_Updates",
                "humidity=60, temp=70",
                "humidity=40, temp=70",
                "40", String.valueOf(actual));
        assertEquals(40, actual);
    }

    @Test
    public void minHumidity_Unchanged() {
        device.readSensors(40, 70);
        device.readSensors(60, 70);
        int actual = device.getMinHumidity();
        printResult("minHumidity_Unchanged",
                "humidity=40, temp=70",
                "humidity=60, temp=70",
                "40", String.valueOf(actual));
        assertEquals(40, actual);
    }

    @Test
    public void humidityTrend_Increasing() {
        device.readSensors(40, 70);
        device.readSensors(50, 70);
        String actual = device.getHumidityTrend();
        printResult("humidityTrend_Increasing (50 > 40)",
                "humidity=40, temp=70",
                "humidity=50, temp=70",
                "Increasing", actual);
        assertEquals("Increasing", actual);
    }

    @Test
    public void humidityTrend_Decreasing() {
        device.readSensors(50, 70);
        device.readSensors(40, 70);
        String actual = device.getHumidityTrend();
        printResult("humidityTrend_Decreasing (40 < 50)",
                "humidity=50, temp=70",
                "humidity=40, temp=70",
                "Decreasing", actual);
        assertEquals("Decreasing", actual);
    }

    @Test
    public void humidityStatus_High() {
        device.readSensors(60, 70);
        String actual = device.getHumidityStatus();
        printResult("humidityStatus_High",
                "humidity=60 (above 55)",
                "High", actual);
        assertEquals("High", actual);
    }

    @Test
    public void humidityStatus_OK() {
        device.readSensors(40, 70);
        String actual = device.getHumidityStatus();
        printResult("humidityStatus_OK",
                "humidity=40 (between 25 and 55)",
                "OK", actual);
        assertEquals("OK", actual);
    }

    @Test
    public void currentTemperature_MidRange() {
        device.readSensors(50, 75);
        int actual = device.getCurrentTemperature();
        printResult("currentTemperature_MidRange",
                "humidity=50, temp=75",
                "75", String.valueOf(actual));
        assertEquals(75, actual);
    }

    @Test
    public void currentTemperature_LowerBound() {
        device.readSensors(50, 0);
        int actual = device.getCurrentTemperature();
        printResult("currentTemperature_LowerBound",
                "humidity=50, temp=0",
                "0", String.valueOf(actual));
        assertEquals(0, actual);
    }

    @Test
    public void maxTemperature_Updates() {
        device.readSensors(50, 68);
        device.readSensors(50, 80);
        int actual = device.getMaxTemperature();
        printResult("maxTemperature_Updates",
                "humidity=50, temp=68",
                "humidity=50, temp=80",
                "80", String.valueOf(actual));
        assertEquals(80, actual);
    }

    @Test
    public void maxTemperature_Unchanged() {
        device.readSensors(50, 80);
        device.readSensors(50, 68);
        int actual = device.getMaxTemperature();
        printResult("maxTemperature_Unchanged",
                "humidity=50, temp=80",
                "humidity=50, temp=68",
                "80", String.valueOf(actual));
        assertEquals(80, actual);
    }

    @Test
    public void minTemperature_Updates() {
        device.readSensors(50, 80);
        device.readSensors(50, 68);
        int actual = device.getMinTemperature();
        printResult("minTemperature_Updates",
                "humidity=50, temp=80",
                "humidity=50, temp=68",
                "68", String.valueOf(actual));
        assertEquals(68, actual);
    }

    @Test
    public void minTemperature_Unchanged() {
        device.readSensors(50, 68);
        device.readSensors(50, 80);
        int actual = device.getMinTemperature();
        printResult("minTemperature_Unchanged",
                "humidity=50, temp=68",
                "humidity=50, temp=80",
                "68", String.valueOf(actual));
        assertEquals(68, actual);
    }

    @Test
    public void temperatureTrend_Increasing() {
        device.readSensors(50, 68);
        device.readSensors(50, 80);
        String actual = device.getTemperatureTrend();
        printResult("temperatureTrend_Increasing (80 > 68)",
                "humidity=50, temp=68",
                "humidity=50, temp=80",
                "Increasing", actual);
        assertEquals("Increasing", actual);
    }

    @Test
    public void temperatureTrend_Decreasing() {
        device.readSensors(50, 80);
        device.readSensors(50, 68);
        String actual = device.getTemperatureTrend();
        printResult("temperatureTrend_Decreasing (68 < 80)",
                "humidity=50, temp=80",
                "humidity=50, temp=68",
                "Decreasing", actual);
        assertEquals("Decreasing", actual);
    }
}
