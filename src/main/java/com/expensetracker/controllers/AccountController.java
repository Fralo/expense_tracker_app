package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.User;
import com.expensetracker.services.AccountService;
import com.expensetracker.utils.AppInstance;
import com.expensetracker.utils.converters.AmountConverter;
import com.expensetracker.utils.converters.AmountConverterFactory;
import com.expensetracker.view.AccountView;
import com.expensetracker.view.CommonView;

public class AccountController {
    private final AccountService accountService;
    private final AccountView accountView;
    private final CommonView commonView;
    private final TransactionController transactionController;

    public AccountController(AccountView accountView, CommonView commonView,
            TransactionController transactionController, AccountService accountService) {
        this.accountService = accountService;
        this.accountView = accountView;
        this.commonView = commonView;
        this.transactionController = transactionController;
    }

    public void handleAccountManagement() {
        User currentUser = AppInstance.getInstance().getCurrentUser();
        List<Account> accounts = accountService.getAllAccounts(currentUser);
        Account selectedAccount = accountView.selectAccount(accounts);

        if (selectedAccount != null) {
            AppInstance.getInstance().setCurrentAccount(selectedAccount);
            handleAccountMenu(selectedAccount);
        }
    }

    private void handleAccountMenu(Account account) {
        boolean back = false;
        while (!back) {
            // Refresh account data at the beginning of the loop
            Account refreshedAccount = accountService.getAccountById(account.getId()).orElse(null);
            if (refreshedAccount == null) {
                commonView.showErrorMessage("Account not found.");
                return; // Exit if the account is no longer accessible
            }
            account = refreshedAccount;

            int choice = accountView.showAccountMenu(account);
            switch (choice) {
                case 1 -> {
                    transactionController.handleAddExpense(account);
                }
                case 2 -> {
                    transactionController.handleAddIncome(account);
                }
                case 3 -> transactionController.handleViewTransactions(account);
                case 4 -> back = true;
                default -> commonView.showErrorMessage("Invalid choice. Please try again.");
            }
        }
    }

    private long getInitialBalance() {
        while (true) {
            String initialBalanceString = accountView.askForAccountInitialBalance();
            try {
                AmountConverter amountConverter = AmountConverterFactory.getDefaultConverter();
                return amountConverter.convertToCents(initialBalanceString);
            } catch (IllegalArgumentException e) {
                commonView.showErrorMessage("Invalid initial balance. Please enter a valid number.");
            }
        }
    }

    public void handleCreateAccount() {
        User currentUser = AppInstance.getInstance().getCurrentUser();
        String accountName = accountView.askForAccountName();
        long initialBalance = getInitialBalance();
        accountService.createAccount(currentUser.getId(), accountName, initialBalance);
        commonView.showSuccessMessage("Account created successfully.");
    }
}
