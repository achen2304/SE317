package lab5;

// Sensor reading interval: 30 seconds in real deployment

//
// REFACTORING:
// The original implementation duplicated the max, min, and trend logic for
// both humidity and temperature.
//
// ORIGINAL (before refactor) — duplicated fields and methods:
//
// private int currentHumidity, maxHumidity, minHumidity, prevHumidity;
// private int currentTemperature, maxTemperature, minTemperature, prevTemperature;
// private boolean humidityHasPrev, temperatureHasPrev;
// private boolean humidityHasReading, temperatureHasReading;
//
// public void readSensors(int h, int t) {
//     if (!humidityHasReading) { maxHumidity = h; minHumidity = h; }
//     else { prevHumidity = currentHumidity; humidityHasPrev = true;
//            if (h > maxHumidity) maxHumidity = h;
//            if (h < minHumidity) minHumidity = h; }
//     currentHumidity = h; humidityHasReading = true;
//
//     if (!temperatureHasReading) { maxTemperature = t; minTemperature = t; }
//     else { prevTemperature = currentTemperature; temperatureHasPrev = true;
//            if (t > maxTemperature) maxTemperature = t;
//            if (t < minTemperature) minTemperature = t; }
//     currentTemperature = t; temperatureHasReading = true;
// }
//
// public String getHumidityTrend() {
//     if (!humidityHasPrev) return "Stable";
//     if (currentHumidity > prevHumidity) return "Increasing";
//     if (currentHumidity < prevHumidity) return "Decreasing";
//     return "Stable";
// }
//
// public String getTemperatureTrend() {
//     if (!temperatureHasPrev) return "Stable";
//     if (currentTemperature > prevTemperature) return "Increasing";
//     if (currentTemperature < prevTemperature) return "Decreasing";
//     return "Stable";
// }

// REFACTORED: both sensors now use SensorState for max, min, and trend.
public class Device {

    public static final int READING_INTERVAL_SECONDS = 30;

    private final SensorState humidity = new SensorState();
    private final SensorState temperature = new SensorState();

    public void reset() {
        humidity.reset();
        temperature.reset();
    }

    public void readSensors(int humidityValue, int temperatureValue) {
        if (humidityValue < 0 || humidityValue > 100)
            throw new IllegalArgumentException("Humidity out of range: " + humidityValue);
        if (temperatureValue < 0 || temperatureValue > 150)
            throw new IllegalArgumentException("Temperature out of range: " + temperatureValue);

        humidity.update(humidityValue);
        temperature.update(temperatureValue);
    }

    public int getCurrentHumidity() {
        return humidity.getCurrent();
    }

    public int getMaxHumidity() {
        return humidity.getMax();
    }

    public int getMinHumidity() {
        return humidity.getMin();
    }

    public String getHumidityTrend() {
        return humidity.getTrend();
    }

    public String getHumidityStatus() {
        int h = humidity.getCurrent();
        if (h > 55)
            return "High";
        if (h < 25)
            return "Low";
        return "OK";
    }

    public int getCurrentTemperature() {
        return temperature.getCurrent();
    }

    public int getMaxTemperature() {
        return temperature.getMax();
    }

    public int getMinTemperature() {
        return temperature.getMin();
    }

    public String getTemperatureTrend() {
        return temperature.getTrend();
    }
}
