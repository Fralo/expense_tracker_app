package com.expensetracker.flows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.expensetracker.AppInstance;
import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public class MainFlow extends Flow {
    @Override
    public void execute() {

        User currentUser = AppInstance.getInstance().getCurrentUser();
        if (currentUser == null) {
            System.out.println("No user is currently logged in. Please log in first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

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
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> {
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
                    String choice = scanner.nextLine();

                    if (accountMap.containsKey(choice)) {
                        Account selectedAccount = accountMap.get(choice);
                        AppInstance.getInstance().setCurrentAccount(selectedAccount);
                        System.out.println("You have selected the account: " + selectedAccount.getName());
                    } else if (choice.equals(String.valueOf(accountMap.size() + 1))) {
                        System.out.println("Creating a new account...");
                        // Logic to create a new account can be added here
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                        scanner.close();
                        return;
                    }

                    ManageAccountFlow manageAccountFlow = new ManageAccountFlow();
                    manageAccountFlow.execute();
                }
                case "2" -> {
                    CreateAccountFlow createAccountFlow = new CreateAccountFlow();
                    createAccountFlow.execute();
                }
                case "3" -> System.out.println("[TODO] Restore Data chosen\n");
                case "4" -> {
                    System.out.println("Exiting the application. Goodbye!");
                    exitLoop = true;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        AppInstance.getInstance().clearCurrentUser();
        scanner.close();
        return;
    }

}
