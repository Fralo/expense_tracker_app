package com.expensetracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.expensetracker.db.DBConnection;

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
                case "1" -> System.out.println("[TODO] Add Expense chosen\n");
                case "2" -> System.out.println("[TODO] Add Income chosen\n");
                case "3" -> System.out.println("[TODO] List Expenses chosen\n");
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