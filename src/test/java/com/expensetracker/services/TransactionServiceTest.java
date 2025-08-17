package com.expensetracker.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Account;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;

class TransactionServiceTest {

    private TransactionService transactionService;
    private AccountService accountService;
    private UserService userService;

    private Account testAccount;
    private User testUser;

    @BeforeEach
    void setUp() throws SQLException {
        DBConnection.initialize("test.db");
        transactionService = new TransactionService();
        accountService = new AccountService();
        transactionService.subscribe(accountService);
        userService = new UserService();

        testUser = userService.createUser("testuser");
        testAccount = accountService.createAccount(testUser.getId(), "Test Account", 100000L);
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DBConnection.getInstance();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS transactions");
            stmt.executeUpdate("DROP TABLE IF EXISTS accounts");
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
        }
    }

    @Test
    void testCreateExpense() {
        Transaction transaction = new Expense(testAccount.getId(), 5000L, LocalDate.now(), "Test Expense");
        transactionService.createTransaction(transaction);
        assertEquals(-5000L, transaction.getAmount());
        assertNotNull(transaction.getId());

        Transaction retrievedTransaction = transactionService.getTransactionById(transaction.getId());
        assertNotNull(retrievedTransaction);
        assertEquals(transaction.getId(), retrievedTransaction.getId());
        assertEquals(-5000L, retrievedTransaction.getAmount());
    }

    @Test
    void testCreateIncome() {
        Transaction transaction = new Income(testAccount.getId(), 5000L, LocalDate.now(), "Test Income");
        transactionService.createTransaction(transaction);
        assertNotNull(transaction.getId());

        Transaction retrievedTransaction = transactionService.getTransactionById(transaction.getId());
        assertNotNull(retrievedTransaction);
        assertEquals(transaction.getId(), retrievedTransaction.getId());
        assertEquals(5000L, retrievedTransaction.getAmount());
    }

    @Test
    void testGetTransactionById() {
        Transaction transaction = new Expense(testAccount.getId(), 5000L, LocalDate.now(), "Test Expense");
        transactionService.createTransaction(transaction);

        Transaction result = transactionService.getTransactionById(transaction.getId());

        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
    }

    @Test
    void testGetAllTransactions() {
        transactionService.addExpense(testAccount, 50, "Test Expense");
        transactionService.addIncome(testAccount, 100, "Test Income");

        List<Transaction> result = transactionService.getAllTransactions(testAccount);

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllTransactionsByType() {
        transactionService.addExpense(testAccount, 50, "Test Expense");
        transactionService.addIncome(testAccount, 100, "Test Income");

        List<Transaction> result = transactionService.getAllTransactions(testAccount, "EXPENSE");

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Expense);
    }

    @Test
    void testGetAllTransactionsByTypeList() {
        transactionService.addExpense(testAccount, 50, "Test Expense");
        transactionService.addIncome(testAccount, 100, "Test Income");
        ArrayList<String> expectedTypes = new ArrayList<>(List.of("INCOME"));

        List<Transaction> result = transactionService.getAllTransactions(testAccount, expectedTypes);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Income);
    }

    @Test
    void testGetAllTransactions_NullAccount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.getAllTransactions(null, new ArrayList<>());
        });
        assertEquals("Account cannot be null", exception.getMessage());
    }

    @Test
    void testObserverPattern() {
        long initialBalance = testAccount.getBalance();
        transactionService.addExpense(testAccount, 5000, "Test Expense");

        Account updatedAccount = accountService.getAccountById(testAccount.getId()).orElse(null);
        assertNotNull(updatedAccount);
        assertEquals(initialBalance - 5000, updatedAccount.getBalance());

        List<Transaction> transactions = transactionService.getAllTransactions(testAccount, "EXPENSE");
        assertEquals(1, transactions.size());
        assertEquals("Test Expense", transactions.get(0).getDescription());
    }
}