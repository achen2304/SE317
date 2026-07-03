package atm;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BankTest {
    private BankStorage bankStorage;
    private UtilityStorage utilityStorage;
    private final String bankFile = "test_bank_data.txt";
    private final String utilFile = "test_utility_data.txt";

    @Before
    public void setUp() throws IOException {
        // Clear test files
        new File(bankFile).delete();
        new File(utilFile).delete();

        bankStorage = new BankStorage(bankFile);
        utilityStorage = new UtilityStorage(utilFile);
        utilityStorage.initializeDefaultsIfMissing();
    }

    @Test
    public void testCheckingDeposit() {
        bankStorage.depositChecking(1000);
        assertEquals(1000, bankStorage.checkingBalance, 0.001);
        
        bankStorage.depositChecking(4000);
        assertEquals(5000, bankStorage.checkingBalance, 0.001);

        try {
            bankStorage.depositChecking(1); // Exceeds 5000 limit
            fail("Expected IllegalArgumentException for exceeding deposit limit");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Daily deposit limit"));
        }
    }

    @Test
    public void testCheckingWithdrawal() {
        bankStorage.checkingBalance = 1000;
        
        bankStorage.withdrawChecking(200);
        assertEquals(800, bankStorage.checkingBalance, 0.001);
        
        bankStorage.withdrawChecking(300);
        assertEquals(500, bankStorage.checkingBalance, 0.001);

        try {
            bankStorage.withdrawChecking(1); // Exceeds 500 limit
            fail("Expected IllegalArgumentException for exceeding withdrawal limit");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Daily withdrawal limit"));
        }
    }

    @Test
    public void testNoOverdraft() {
        bankStorage.checkingBalance = 100;
        try {
            bankStorage.withdrawChecking(200);
            fail("Expected IllegalArgumentException for insufficient funds");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Insufficient funds"));
        }
        assertEquals(100, bankStorage.checkingBalance, 0.001);
    }

    @Test
    public void testTransferToSaving() {
        bankStorage.checkingBalance = 500;
        bankStorage.transferToSaving(200);
        assertEquals(300, bankStorage.checkingBalance, 0.001);
        assertEquals(200, bankStorage.savingBalance, 0.001);
    }

    @Test
    public void testSavingDeposit() {
        bankStorage.depositSaving(5000);
        assertEquals(5000, bankStorage.savingBalance, 0.001);

        try {
            bankStorage.depositSaving(1);
            fail("Expected IllegalArgumentException for exceeding deposit limit");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Daily deposit limit"));
        }
    }

    @Test
    public void testTransferToChecking() {
        bankStorage.savingBalance = 500;
        bankStorage.transferToChecking(100);
        assertEquals(400, bankStorage.savingBalance, 0.001);
        assertEquals(100, bankStorage.checkingBalance, 0.001);

        try {
            bankStorage.transferToChecking(1);
            fail("Expected IllegalArgumentException for exceeding transfer limit");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Daily transfer limit"));
        }
    }

    @Test
    public void testUtilityBillPayment() throws IOException {
        bankStorage.checkingBalance = 500;
        bankStorage.payUtilityBill(100, utilityStorage);
        
        assertEquals(400, bankStorage.checkingBalance, 0.001);
        
        List<String> history = utilityStorage.getHistory();
        assertTrue(history.get(history.size() - 2).contains("Payment: $100.00"));
        assertTrue(history.get(history.size() - 1).contains("Payment Date: Day 1"));
    }

    @Test
    public void testSimulateNextDay() {
        bankStorage.depositChecking(5000);
        bankStorage.checkingBalance = 6000;
        bankStorage.withdrawChecking(500);
        
        bankStorage.simulateNextDay();
        assertEquals(2, bankStorage.currentDay);
        
        // Limits should be reset, so we can deposit and withdraw again
        bankStorage.depositChecking(1000);
        bankStorage.withdrawChecking(100);
        
        assertEquals(6400, bankStorage.checkingBalance, 0.001);
    }

    @Test
    public void testPersistence() throws IOException {
        bankStorage.checkingBalance = 1234.56;
        bankStorage.savingBalance = 789.01;
        bankStorage.depositChecking(100); // changes checkingDepositedToday
        bankStorage.save();
        
        BankStorage newStorage = new BankStorage(bankFile);
        newStorage.load();
        
        assertEquals(1334.56, newStorage.checkingBalance, 0.001);
        assertEquals(789.01, newStorage.savingBalance, 0.001);
        assertEquals(100, newStorage.checkingDepositedToday, 0.001);
    }
}
