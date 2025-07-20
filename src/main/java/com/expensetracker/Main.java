package com.expensetracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.expensetracker.dao.UserDao;
import com.expensetracker.dao.jdbc.JdbcUserDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.flows.OnboardingFlow;
import com.expensetracker.flows.Flow;
import com.expensetracker.flows.MainFlow;
import com.expensetracker.model.User;

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

        // Clear console for better user experience
        System.out.print("\033[H\033[2J");
        System.out.flush();

        UserDao userDao = new JdbcUserDao();

        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found. Starting onboarding flow...");
            Flow onboardingFlow = new OnboardingFlow();
            onboardingFlow.execute();
        }

        Map<String, User> userMap = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            String optionChoice = String.valueOf(i + 1);
            userMap.put(optionChoice, users.get(i));
        }

        System.out.println("Please select a user to continue:");
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            System.out.println("[" + entry.getKey() + "] " + entry.getValue().getUsername());
        }
        System.out.println("[" + (userMap.size() + 1) + "] Exit");
        System.out.print("Choice: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        if (choice.equals(String.valueOf(userMap.size() + 1))) {
            System.out.println("Exiting...");
            scanner.close();
            return;
        }

        User selectedUser = userMap.get(choice);
        if (selectedUser == null) {
            System.out.println("Invalid choice. Exiting...");
            scanner.close();
            return;
        }

        AppInstance.getInstance().setCurrentUser(selectedUser);

        Flow mainFlow = new MainFlow();
        mainFlow.execute();

        AppInstance.getInstance().clearCurrentUser();
        System.out.println("Thank you for using the Expense Tracker! Goodbye!");
        scanner.close();
    }
}