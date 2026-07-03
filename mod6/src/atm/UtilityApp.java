package atm;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UtilityApp {
    public static void main(String[] args) {
        UtilityStorage storage = new UtilityStorage("utility_data.txt");
        try {
            storage.initializeDefaultsIfMissing();
        } catch (IOException e) {
            System.out.println("Error initializing utility data: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Utility Company");
        System.out.print("Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        if (!user.equals("SE3170User") || !pass.equals("summer2025")) {
            System.out.println("Invalid credentials. Exiting.");
            return;
        }

        System.out.println("Login successful.");
        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu");
            System.out.println("1. Check Payment History");
            System.out.println("2. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                try {
                    List<String> history = storage.getHistory();
                    System.out.println("\n--- Bill Payment History ---");
                    for (String line : history) {
                        System.out.println(line);
                    }
                    System.out.println("----------------------------");
                } catch (IOException e) {
                    System.out.println("Error reading history: " + e.getMessage());
                }
            } else if (choice.equals("2")) {
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        System.out.println("Thank you for using the Utility Company.");
    }
}
