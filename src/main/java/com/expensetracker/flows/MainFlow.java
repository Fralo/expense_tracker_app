package com.expensetracker.flows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.expensetracker.AppInstance;
import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;
import com.expensetracker.singleton.InputReader;

public class MainFlow extends Flow {

    public MainFlow() {
        super("Expense Tracker");
    }

    @Override
    public void execute() {

        User currentUser = AppInstance.getInstance().getCurrentUser();
        if (currentUser == null) {
            System.out.println("No user is currently logged in. Please log in first.");
            return;
        }

        System.out.println("Welcome, " + currentUser.getUsername() + "!");

        boolean exitLoop = false;
        while (!exitLoop) {
            System.out.println("\n\n");
            System.out.println("=== What would you like to do? ===");
            System.out.println("1) Manage Account");
            System.out.println("2) Create Account");
            System.out.println("3) Backup Data");
            System.out.println("4) Restore Data");
            System.out.println("5) Logout");

            System.out.print("Choice: ");

            String input = InputReader.getInstance().readLine();
            switch (input) {
                case "1" -> {

                    // Clear console for better user experience
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println("Select an account to manage:");

                    AccountDao accountDao = new JdbcAccountDao();
                    List<Account> accounts = accountDao.findAll(currentUser);

                    Map<String, Account> accountMap = new HashMap<>();
                    for (int i = 0; i < accounts.size(); i++) {
                        String optionChoice = String.valueOf(i + 1);
                        accountMap.put(optionChoice, accounts.get(i));
                    }

                    for (Map.Entry<String, Account> entry : accountMap.entrySet()) {
                        System.out.println("[" + entry.getKey() + "] " + entry.getValue().getName());
                    }
                    System.out.println("[" + (accountMap.size() + 1) + "] Create New Account");
                    System.out.print("Choice: ");
                    String choice = InputReader.getInstance().readLine();

                    if (accountMap.containsKey(choice)) {
                        Account selectedAccount = accountMap.get(choice);
                        AppInstance.getInstance().setCurrentAccount(selectedAccount);
                        System.out.println("You have selected the account: " + selectedAccount.getName());
                    } else if (choice.equals(String.valueOf(accountMap.size() + 1))) {
                        System.out.println("Creating a new account...");
                        // Logic to create a new account can be added here
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                        return;
                    }

                    ManageAccountFlow manageAccountFlow = new ManageAccountFlow();
                    manageAccountFlow.start();
                }
                case "2" -> {
                    CreateAccountFlow createAccountFlow = new CreateAccountFlow();
                    createAccountFlow.start();
                }
                case "3" -> System.out.println("[TODO] Backup Data chosen\n");
                case "4" -> System.out.println("[TODO] Restore Data chosen\n");
                case "5" -> {
                    System.out.println("Exiting the application. Goodbye!");
                    exitLoop = true;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        AppInstance.getInstance().clearCurrentUser();
        return;
    }

}
