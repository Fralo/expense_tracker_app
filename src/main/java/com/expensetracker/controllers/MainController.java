package com.expensetracker.controllers;

import com.expensetracker.AppInstance;
import com.expensetracker.model.User;
import com.expensetracker.view.AppView;
import com.expensetracker.view.CliView;

public class MainController {
    private final AppView view;
    private final UserController userController;
    private final AccountController accountController;

    public MainController() {
        this.view = new CliView();
        this.userController = new UserController(this.view);
        this.accountController = new AccountController(this.view);
    }

    public void start() {
        view.showWelcomeMessage();

        while (true) {
            User currentUser = AppInstance.getInstance().getCurrentUser();
            if (currentUser == null) {
                currentUser = userController.handleUserSelection();
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
                case 1 -> accountController.handleAccountManagement();
                case 2 -> accountController.handleCreateAccount();
                default -> view.showErrorMessage("Invalid choice. Please try again.");
            }
        }

        view.showGoodbyeMessage();
    }
}