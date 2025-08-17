package com.expensetracker.controllers;

import com.expensetracker.model.User;
import com.expensetracker.services.AccountService;
import com.expensetracker.services.TransactionService;
import com.expensetracker.utils.AppInstance;
import com.expensetracker.view.AccountView;
import com.expensetracker.view.CommonView;
import com.expensetracker.view.MainView;
import com.expensetracker.view.TransactionView;
import com.expensetracker.view.UserView;
import com.expensetracker.view.cli.CliAccountView;
import com.expensetracker.view.cli.CliCommonView;
import com.expensetracker.view.cli.CliMainView;
import com.expensetracker.view.cli.CliTransactionView;
import com.expensetracker.view.cli.CliUserView;

public class MainController {
    private final CommonView commonView;
    private final MainView mainView;
    private final UserController userController;
    private final AccountController accountController;

    public MainController() {
        this.commonView = new CliCommonView();
        this.mainView = new CliMainView();
        UserView userView = new CliUserView();
        AccountView accountView = new CliAccountView();
        TransactionView transactionView = new CliTransactionView();
        TransactionService transactionService = new TransactionService();
        AccountService accountService = new AccountService();
        transactionService.subscribe(accountService);
        TransactionController transactionController = new TransactionController(transactionView, commonView, transactionService);
        this.userController = new UserController(userView, commonView);
        this.accountController = new AccountController(accountView, commonView, transactionController, accountService);
    }

    public void start() {
        commonView.showWelcomeMessage();

        while (true) {
            User currentUser = AppInstance.getInstance().getCurrentUser();
            if (currentUser == null) {
                currentUser = userController.handleUserSelection();
                if (currentUser == null) {
                    break;
                }
            }

            int choice = mainView.showMainMenu();
            if (choice == 3) { // Logout
                AppInstance.getInstance().clearCurrentUser();
                continue;
            }

            switch (choice) {
                case 1 -> accountController.handleAccountManagement();
                case 2 -> accountController.handleCreateAccount();
                default -> commonView.showErrorMessage("Invalid choice. Please try again.");
            }
        }

        commonView.showGoodbyeMessage();
    }
}