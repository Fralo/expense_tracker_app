package com.expensetracker.flows;

import com.expensetracker.AppInstance;
import com.expensetracker.controllers.AccountController;
import com.expensetracker.model.User;
import com.expensetracker.singleton.InputReader;

public class CreateAccountFlow extends Flow {

    private User user;

    public CreateAccountFlow() {
        super("Create Account");
        user = AppInstance.getInstance().getCurrentUser();
        if (user == null) {
            System.out.println("No user is currently logged in. Please log in first.");
            return;
        }
    }

    @Override
    public void execute() {

        AccountController accountController = new AccountController();

        System.out.println("To create a new account, please provide the following details:");

        System.out.println("What would be the name of the account?");
        String accountName = InputReader.getInstance().readInput("> ");

        String initialBalance = InputReader.getInstance().readInput("What is the initial balance of the account?", "0");

        accountController.createAccount(user.getId(), accountName, initialBalance);

        System.out.println("Congratulations " + user.getUsername() + "! Your account has been set up successfully.");
    }

}
