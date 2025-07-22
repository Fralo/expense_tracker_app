package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.AppInstance;
import com.expensetracker.converters.AmountConverter;
import com.expensetracker.converters.AmountConverterFactory;
import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.view.AppView;
import com.expensetracker.view.CliView;

public class MainController {
    private final AppView view;
    private final UserController userController;
    private final AccountController accountController;
    private final TransactionController transactionController;
    private final AmountConverter amountConverter;
    private final AccountDao accountDao;

    public MainController() {
        this.view = new CliView();
        this.userController = new UserController();
        this.accountController = new AccountController();
        this.transactionController = new TransactionController();
        this.amountConverter = AmountConverterFactory.getDefaultConverter();
        this.accountDao = new JdbcAccountDao();
    }

    public void start() {
        view.showWelcomeMessage();

        while (true) {
            User currentUser = AppInstance.getInstance().getCurrentUser();
            if (currentUser == null) {
                currentUser = handleUserSelection();
                if (currentUser == null) {
                    break;
                }
            }

            int choice = view.showMainMenu();
            if (choice == 3) { // Logout
                AppInstance.getInstance().clearCurrentUser();
                continue;
            }

            switch (choice) {
                case 1 -> handleAccountManagement();
                case 2 -> handleCreateAccount();
                default -> view.showErrorMessage("Invalid choice. Please try again.");
            }
        }

        view.showGoodbyeMessage();
    }

    private User handleUserSelection() {
        List<User> users = userController.getAllUsers();
        User selectedUser;

        if (users.isEmpty()) {
            view.showSuccessMessage("No users found. Let's create the first one.");
            String newUsername = view.askForUsername();
            if (newUsername.equalsIgnoreCase("exit")) {
                return null;
            }
            selectedUser = userController.createUser(newUsername);
            view.showSuccessMessage("User created successfully.");
        } else {
            selectedUser = view.selectUser(users);
            if (selectedUser == null) { // User wants to exit
                return null;
            }

            if (selectedUser.getId() == -1) {
                String newUsername = view.askForUsername();
                if (newUsername.equalsIgnoreCase("exit")) {
                    return null;
                }
                selectedUser = userController.createUser(newUsername);
                view.showSuccessMessage("User created successfully.");
            }
        }

        if (selectedUser != null) {
            AppInstance.getInstance().setCurrentUser(selectedUser);
        }

        return selectedUser;
    }



    private void handleAccountManagement() {
        User currentUser = AppInstance.getInstance().getCurrentUser();
        List<Account> accounts = accountController.getAllAccounts(currentUser);
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
                case 1 -> handleAddExpense(account);
                case 2 -> handleAddIncome(account);
                case 3 -> handleViewTransactions(account);
                case 4 -> back = true;
                default -> view.showErrorMessage("Invalid choice. Please try again.");
            }
            if (!back) {
                account = accountDao.findById(account.getId()).orElse(null);
                if (account == null) {
                    view.showErrorMessage("Account not found.");
                    back = true;
                }
            }
        }
    }

    private void handleCreateAccount() {
        User currentUser = AppInstance.getInstance().getCurrentUser();
        String accountName = view.askForAccountName();
        String initialBalance = view.askForAmount();
        accountController.createAccount(currentUser.getId(), accountName, amountConverter.convertToCents(initialBalance));
        view.showSuccessMessage("Account created successfully.");
    }

    private void handleAddExpense(Account account) {
        String amount = view.askForAmount();
        String description = view.askForDescription();
        transactionController.addExpense(account, amount, description);
        view.showSuccessMessage("Expense added successfully.");
    }

    private void handleAddIncome(Account account) {
        String amount = view.askForAmount();
        String description = view.askForDescription();
        transactionController.addIncome(account, amount, description);
        view.showSuccessMessage("Income added successfully.");
    }

    private void handleViewTransactions(Account account) {
        List<Transaction> transactions = transactionController.getAllTransactions(account);
        view.showTransactions(transactions);
    }
} 