package atm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilityStorage {
    private final String filename;

    public UtilityStorage(String filename) {
        this.filename = filename;
    }

    public void initializeDefaultsIfMissing() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("January Bill: $24.00");
                writer.println("Due Date: January 31");
                writer.println("Payment: $24.00");
                writer.println("Payment Date: January 31");
                writer.println("");
                writer.println("February Bill: $28.00");
                writer.println("Due Date: February 28");
                writer.println("Payment: $28.00");
                writer.println("Payment Date: February 26");
                writer.println("");
                writer.println("March Bill: $35.00");
                writer.println("Due Date: March 31");
            }
        }
    }

    public List<String> getHistory() throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public void appendPayment(double amount, int currentDay) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.printf("Payment: $%.2f%n", amount);
            writer.printf("Payment Date: Day %d%n", currentDay);
        }
    }
}
