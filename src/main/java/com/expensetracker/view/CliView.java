package com.expensetracker.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.singleton.InputReader;

public class CliView implements AppView {

    @Override
    public void showWelcomeMessage() {
        System.out.println("Welcome to the Expense Tracker!");
    }

    @Override
    public void showGoodbyeMessage() {
        System.out.println("Exiting the application. Goodbye!");
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    @Override
    public void showSuccessMessage(String message) {
        System.out.println("Success: " + message);
    }

    @Override
    public User selectUser(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return null;
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
        System.out.println("[" + (userMap.size() + 1) + "] Create New User");
        System.out.println("[" + (userMap.size() + 2) + "] Exit");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();

        if (userMap.containsKey(choice)) {
            return userMap.get(choice);
        } else if (choice.equals(String.valueOf(userMap.size() + 1))) {
            return new User(-1, "new_user"); // Special user to indicate creation
        } else if (choice.equals(String.valueOf(userMap.size() + 2)) || choice.equalsIgnoreCase("exit")) {
            return null;
        }
        return null;
    }

    @Override
    public int showMainMenu() {
        System.out.println("\n\n");
        System.out.println("=== What would you like to do? ===");
        System.out.println("1) Manage Account");
        System.out.println("2) Create Account");
        System.out.println("3) Logout");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public Account selectAccount(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this user.");
            return null;
        }

        Map<String, Account> accountMap = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            String optionChoice = String.valueOf(i + 1);
            accountMap.put(optionChoice, accounts.get(i));
        }

        System.out.println("Select an account to manage:");
        for (Map.Entry<String, Account> entry : accountMap.entrySet()) {
            System.out.println("[" + entry.getKey() + "] " + entry.getValue().getName());
        }
        System.out.println("[" + (accountMap.size() + 1) + "] Back");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();

        if (accountMap.containsKey(choice)) {
            return accountMap.get(choice);
        }
        return null;
    }

    @Override
    public int showAccountMenu(Account account) {
        System.out.println("\n\n");
        System.out.println("=== Managing Account: " + account.getName() + " ===");
        System.out.println("Balance: " + account.getBalance());
        System.out.println("1) Add Expense");
        System.out.println("2) Add Income");
        System.out.println("3) View Transactions");
        System.out.println("4) Back");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-15s | %-30s%n", "Type", "Date", "Amount", "Description");
        System.out.println("--------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            String type = transaction instanceof com.expensetracker.model.Expense ? "Expense" : "Income";
            System.out.printf("%-10s | %-12s | %-15.2f | %-30s%n",
                    type,
                    transaction.getDate(),
                    transaction.getAmount() / 100.0,
                    transaction.getDescription());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Override
    public String askForUsername() {
        System.out.print("Enter username: ");
        return InputReader.getInstance().readLine();
    }

    @Override
    public String askForAccountName() {
        System.out.print("Enter account name: ");
        return InputReader.getInstance().readLine();
    }

    @Override
    public String askForAmount() {
        System.out.print("Enter amount: ");
        return InputReader.getInstance().readLine();
    }

    @Override
    public String askForDescription() {
        System.out.print("Enter description: ");
        return InputReader.getInstance().readLine();
    }
} 