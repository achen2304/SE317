package atm;

import java.io.IOException;
import java.util.Scanner;

public class BankApp {
    private static BankStorage storage = new BankStorage("bank_data.txt");
    private static UtilityStorage utilityStorage = new UtilityStorage("utility_data.txt");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            storage.load();
            utilityStorage.initializeDefaultsIfMissing();
        } catch (IOException e) {
            System.out.println("Error initializing storage: " + e.getMessage());
            return;
        }

        System.out.println("Welcome to the Bank ATM");
        System.out.print("Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        if (!user.equals("se3170user") || !pass.equals("summer2025")) {
            System.out.println("Invalid credentials. Exiting.");
            return;
        }

        System.out.println("Login successful.");
        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu - Day " + storage.currentDay);
            System.out.println("1. Checking Account");
            System.out.println("2. Saving Account");
            System.out.println("3. Simulate Next Day");
            System.out.println("4. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    checkingMenu(scanner);
                    break;
                case "2":
                    savingMenu(scanner);
                    break;
                case "3":
                    storage.simulateNextDay();
                    System.out.println("Advanced to Day " + storage.currentDay);
                    saveData();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        System.out.println("Thank you for using the Bank ATM.");
    }

    private static void checkingMenu(Scanner scanner) {
        boolean inChecking = true;
        while (inChecking) {
            System.out.println("\nChecking Account");
            System.out.println("Balance: $" + String.format("%.2f", storage.checkingBalance));
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer to Saving");
            System.out.println("4. Pay Utility Bill");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Amount to deposit: ");
                        double dep = Double.parseDouble(scanner.nextLine().trim());
                        if (dep <= 0) {
                            System.out.println("Invalid amount.");
                        } else {
                            try {
                                storage.depositChecking(dep);
                                System.out.println("Deposit successful.");
                                saveData();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "2":
                        System.out.print("Amount to withdraw: ");
                        double wd = Double.parseDouble(scanner.nextLine().trim());
                        if (wd <= 0) {
                            System.out.println("Invalid amount.");
                        } else {
                            try {
                                storage.withdrawChecking(wd);
                                System.out.println("Withdrawal successful.");
                                saveData();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "3":
                        System.out.print("Amount to transfer to Saving: ");
                        double trans = Double.parseDouble(scanner.nextLine().trim());
                        if (trans <= 0) {
                            System.out.println("Invalid amount.");
                        } else {
                            try {
                                storage.transferToSaving(trans);
                                System.out.println("Transfer successful.");
                                saveData();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "4":
                        System.out.print("Amount to pay Utility Company: ");
                        double bill = Double.parseDouble(scanner.nextLine().trim());
                        if (bill <= 0) {
                            System.out.println("Invalid amount.");
                        } else {
                            try {
                                storage.payUtilityBill(bill, utilityStorage);
                                System.out.println("Bill payment sent to Utility Company.");
                                saveData();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            } catch (IOException e) {
                                System.out.println("Error appending payment: " + e.getMessage());
                            }
                        }
                        break;
                    case "5":
                        inChecking = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void savingMenu(Scanner scanner) {
        boolean inSaving = true;
        while (inSaving) {
            System.out.println("\nSaving Account");
            System.out.println("Balance: $" + String.format("%.2f", storage.savingBalance));
            System.out.println("1. Deposit");
            System.out.println("2. Transfer to Checking");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Amount to deposit: ");
                        double dep = Double.parseDouble(scanner.nextLine().trim());
                        if (dep <= 0) {
                            System.out.println("Invalid amount.");
                        } else {
                            try {
                                storage.depositSaving(dep);
                                System.out.println("Deposit successful.");
                                saveData();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "2":
                        System.out.print("Amount to transfer to Checking: ");
                        double trans = Double.parseDouble(scanner.nextLine().trim());
                        if (trans <= 0) {
                            System.out.println("Invalid amount.");
                        } else {
                            try {
                                storage.transferToChecking(trans);
                                System.out.println("Transfer successful.");
                                saveData();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "3":
                        inSaving = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void saveData() {
        try {
            storage.save();
        } catch (IOException e) {
            System.out.println("Error saving bank data: " + e.getMessage());
        }
    }
}
