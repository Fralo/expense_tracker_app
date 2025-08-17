package com.expensetracker.view.cli;

import com.expensetracker.view.CommonView;

public class CliCommonView extends CliBaseView implements CommonView {

    @Override
    public void showWelcomeMessage() {
        System.out.println("Welcome to the Expense Tracker!");
    }

    @Override
    public void showGoodbyeMessage() {
        System.out.println("Exiting the application. Goodbye!");
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    @Override
    public void showSuccessMessage(String message) {
        System.out.println("Success: " + message);
    }
} 