package com.expensetracker.flows;

import java.math.BigInteger;

import com.expensetracker.AppInstance;
import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public class CreateAccountFlow extends Flow {
    @Override
    public void execute() {
        AppInstance appInstance = AppInstance.getInstance();
        if (appInstance.getCurrentUser() == null) {
            System.out.println("No user is currently logged in. Please log in first.");
            return;
        }

        User user = appInstance.getCurrentUser();

        System.out.println("=== Create Account ===");
        System.out.println("To create a new account, please provide the following details:");

        System.out.println("What would be the name of the account?");
        String accountName = System.console().readLine();

        System.out.println("What is the initial balance for this account? (Leave it empty for zero balance)");

        BigInteger initialBalanceValue = BigInteger.ZERO;
        boolean isValid = false;
        do {
            try {
                String initialBalance = System.console().readLine();
                if (initialBalance != null && !initialBalance.trim().isEmpty()) {
                    initialBalanceValue = new BigInteger(initialBalance);
                }
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance. Please enter a valid number.");
            }
        } while (!isValid);

        System.out.println(
                "You can set a threshold balance for this account. If you leave it empty, no threshold will be set.");
        BigInteger thresholdBalanceValue = null;
        isValid = false;
        do {
            try {
                String thresholdBalance = System.console().readLine();
                if (thresholdBalance != null && !thresholdBalance.trim().isEmpty()) {
                    thresholdBalanceValue = new BigInteger(thresholdBalance);
                }
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid threshold balance. Please enter a valid number.");
            }
        } while (!isValid);

        Account account = new Account(user.getId(), accountName, initialBalanceValue, thresholdBalanceValue);
        AccountDao accountDao = new JdbcAccountDao();
        accountDao.save(account);

        System.out.println("Congratulations " + user.getUsername() + "! Your account has been set up successfully.");
    }

}
