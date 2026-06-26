package lab5;

public class SensorState {

    private int current;
    private int previous;
    private int max;
    private int min;
    private boolean hasReading;
    private boolean hasPrevious;

    public void reset() {
        hasReading = false;
        hasPrevious = false;
    }

    public void update(int value) {
        if (!hasReading) {
            max = value;
            min = value;
            hasPrevious = false;
        } else {
            previous = current;
            hasPrevious = true;
            if (value > max)
                max = value;
            if (value < min)
                min = value;
        }
        current = value;
        hasReading = true;
    }

    public String getTrend() {
        if (!hasPrevious)
            return "Stable";
        if (current > previous)
            return "Increasing";
        if (current < previous)
            return "Decreasing";
        return "Stable";
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public boolean hasReading() {
        return hasReading;
    }
}
