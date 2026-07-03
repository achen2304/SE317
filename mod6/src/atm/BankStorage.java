package atm;

import java.io.*;

public class BankStorage {
    private final String filename;
    
    public double checkingBalance = 0;
    public double savingBalance = 0;
    
    public double checkingDepositedToday = 0;
    public double checkingWithdrawnToday = 0;
    public double savingDepositedToday = 0;
    public double savingTransferredToday = 0;
    
    public int currentDay = 1;
    
    public BankStorage(String filename) {
        this.filename = filename;
    }
    
    public void load() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checkingBalance = Double.parseDouble(reader.readLine());
            savingBalance = Double.parseDouble(reader.readLine());
            checkingDepositedToday = Double.parseDouble(reader.readLine());
            checkingWithdrawnToday = Double.parseDouble(reader.readLine());
            savingDepositedToday = Double.parseDouble(reader.readLine());
            savingTransferredToday = Double.parseDouble(reader.readLine());
            currentDay = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IOException("Data file is corrupted or improperly formatted.");
        }
    }
    
    public void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(checkingBalance);
            writer.println(savingBalance);
            writer.println(checkingDepositedToday);
            writer.println(checkingWithdrawnToday);
            writer.println(savingDepositedToday);
            writer.println(savingTransferredToday);
            writer.println(currentDay);
        }
    }
    
    public void simulateNextDay() {
        currentDay++;
        checkingDepositedToday = 0;
        checkingWithdrawnToday = 0;
        savingDepositedToday = 0;
        savingTransferredToday = 0;
    }

    public void depositChecking(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        if (checkingDepositedToday + amount > 5000) throw new IllegalArgumentException("Daily deposit limit of $5000 exceeded.");
        checkingBalance += amount;
        checkingDepositedToday += amount;
    }

    public void withdrawChecking(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        if (checkingWithdrawnToday + amount > 500) throw new IllegalArgumentException("Daily withdrawal limit of $500 exceeded.");
        if (checkingBalance - amount < 0) throw new IllegalArgumentException("Insufficient funds (no overdraft).");
        checkingBalance -= amount;
        checkingWithdrawnToday += amount;
    }

    public void transferToSaving(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        if (checkingBalance - amount < 0) throw new IllegalArgumentException("Insufficient funds.");
        checkingBalance -= amount;
        savingBalance += amount;
    }

    public void payUtilityBill(double amount, UtilityStorage utility) throws IllegalArgumentException, IOException {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        if (checkingBalance - amount < 0) throw new IllegalArgumentException("Insufficient funds.");
        
        checkingBalance -= amount;
        try {
            utility.appendPayment(amount, currentDay);
        } catch (IOException e) {
            checkingBalance += amount; // rollback
            throw e;
        }
    }

    public void depositSaving(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        if (savingDepositedToday + amount > 5000) throw new IllegalArgumentException("Daily deposit limit of $5000 exceeded.");
        savingBalance += amount;
        savingDepositedToday += amount;
    }

    public void transferToChecking(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        if (savingTransferredToday + amount > 100) throw new IllegalArgumentException("Daily transfer limit of $100 exceeded.");
        if (savingBalance - amount < 0) throw new IllegalArgumentException("Insufficient funds (no overdraft).");
        savingBalance -= amount;
        savingTransferredToday += amount;
        checkingBalance += amount;
    }
}
