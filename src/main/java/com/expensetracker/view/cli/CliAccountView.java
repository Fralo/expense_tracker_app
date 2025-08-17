package com.expensetracker.view.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.expensetracker.model.Account;
import com.expensetracker.utils.InputReader;
import com.expensetracker.view.AccountView;

public class CliAccountView extends CliBaseView implements AccountView {

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
    public String askForAccountName() {
        System.out.print("Enter account name: ");
        return InputReader.getInstance().readLine();
    }

    @Override
    public String askForAccountInitialBalance() {
        System.out.print("Enter initial balance: ");
        return InputReader.getInstance().readLine();
    }
} 