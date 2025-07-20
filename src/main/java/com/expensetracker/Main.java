package com.expensetracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.expensetracker.dao.TransactionDao;
import com.expensetracker.dao.jdbc.JdbcTransactionDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Transaction;

/**
 * Simple CLI entry point. Currently, it just verifies the DB connection and
 * then asks which high-level operation the user wants to perform. The actual
 * implementations will be provided incrementally.
 */
public class Main {

    public static void main(String[] args) {
        try {
            String dbFile = System.getProperty("db.file", "expense_tracker.db");
            DBConnection.initialize(dbFile);
            Connection connection = DBConnection.getInstance();
            System.out.println("✅ Successfully connected to the SQLite database!\n");
            runCli();
            connection.close();
        } catch (SQLException e) {
            System.err.println("❌ Could not connect to the database: " + e.getMessage());
        }
    }

    private static void runCli() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1) Add Expense");
            System.out.println("2) Add Income");
            System.out.println("3) List Expenses");
            System.out.println("4) List Incomes");
            System.out.println("5) Exit");
            System.out.print("Choice: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Enter the amount: ");
                    BigDecimal amount = new BigDecimal(scanner.nextLine());
                    System.out.println("Enter the date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter the description: ");
                    String description = scanner.nextLine();
                    Expense expense = new Expense(amount, date, description);
                    TransactionDao transactionDao = new JdbcTransactionDao();
                    transactionDao.save(expense);
                }
                case "2" -> System.out.println("[TODO] Add Income chosen\n");
                case "3" -> {
                    TransactionDao transactionDao = new JdbcTransactionDao();
                    List<Transaction> expenses = transactionDao.findAll();
                    for (Transaction expense : expenses) {
                        System.out.println(expense);
                    }
                }
                case "4" -> System.out.println("[TODO] List Incomes chosen\n");
                case "5" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.\n");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
} 