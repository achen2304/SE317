package lab5;

import java.util.Scanner;

public class Main {

    // Sensor reading interval: 30 seconds in real deployment
    private static final int READING_INTERVAL_SECONDS = 30;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Device device = new Device();

        System.out.println("Embedded Device Simulator");
        System.out.println("Sensor reading interval: "
                + READING_INTERVAL_SECONDS + " seconds");
        System.out.println("Commands: enter a temperature/humidity pair or 'reset'");
        System.out.println();

        while (true) {
            System.out.print("Enter temperature (0-150 °F) [or 'reset']: ");
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("reset")) {
                device.reset();
                System.out.println("-- Device reset: max/min values cleared --\n");
                continue;
            }

            int temp;
            try {
                temp = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'reset'.\n");
                continue;
            }

            System.out.print("Enter humidity  (0-100 %): ");
            int humidity;
            try {
                humidity = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.\n");
                continue;
            }

            try {
                device.readSensors(humidity, temp);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
                continue;
            }

            printDisplay(device);
        }

    }

    private static void printDisplay(Device d) {
        System.out.println();
        System.out.println("--- HUMIDITY ---");
        System.out.printf("Current Humidity:    %d%%%n", d.getCurrentHumidity());
        System.out.printf("Maximum Humidity:    %d%%%n", d.getMaxHumidity());
        System.out.printf("Minimum Humidity:    %d%%%n", d.getMinHumidity());
        System.out.printf("Humidity Trend:      %s%n", d.getHumidityTrend());
        System.out.printf("Humidity Check:      %s%n", d.getHumidityStatus());
        System.out.println();
        System.out.println("--- TEMPERATURE ---");
        System.out.printf("Current Temperature: %d°F%n", d.getCurrentTemperature());
        System.out.printf("Maximum Temperature: %d°F%n", d.getMaxTemperature());
        System.out.printf("Minimum Temperature: %d°F%n", d.getMinTemperature());
        System.out.printf("Temperature Trend:   %s%n", d.getTemperatureTrend());
        System.out.println();
    }
}
