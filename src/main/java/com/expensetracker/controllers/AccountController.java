package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.AppInstance;
import com.expensetracker.converters.AmountConverter;
import com.expensetracker.converters.AmountConverterFactory;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;
import com.expensetracker.services.AccountService;
import com.expensetracker.view.AppView;

public class AccountController {
    private final AccountService accountService;
    private final AppView view;
    private final AmountConverter amountConverter;
    private final TransactionController transactionController;

    public AccountController(AppView view) {
        this.accountService = new AccountService();
        this.view = view;
        this.amountConverter = AmountConverterFactory.getDefaultConverter();
        this.transactionController = new TransactionController(view);
    }

    public void handleAccountManagement() {
        User currentUser = AppInstance.getInstance().getCurrentUser();
        List<Account> accounts = accountService.getAllAccounts(currentUser);
        Account selectedAccount = view.selectAccount(accounts);

        if (selectedAccount != null) {
            AppInstance.getInstance().setCurrentAccount(selectedAccount);
            handleAccountMenu(selectedAccount);
        }
    }

    private void handleAccountMenu(Account account) {
        boolean back = false;
        while (!back) {
            int choice = view.showAccountMenu(account);
            switch (choice) {
                case 1 -> transactionController.handleAddExpense(account);
                case 2 -> transactionController.handleAddIncome(account);
                case 3 -> transactionController.handleViewTransactions(account);
                case 4 -> back = true;
                default -> view.showErrorMessage("Invalid choice. Please try again.");
            }
            if (!back) {
                account = accountService.getAccountById(account.getId()).orElse(null);
                if (account == null) {
                    view.showErrorMessage("Account not found.");
                    back = true;
                }
            }
        }
    }

    public void handleCreateAccount() {
        User currentUser = AppInstance.getInstance().getCurrentUser();
        String accountName = view.askForAccountName();
        String initialBalance = view.askForAmount();
        accountService.createAccount(currentUser.getId(), accountName,
                amountConverter.convertToCents(initialBalance));
        view.showSuccessMessage("Account created successfully.");
    }
}
