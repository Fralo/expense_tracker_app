package com.expensetracker.view;

public interface CommonView {
    void showWelcomeMessage();
    void showGoodbyeMessage();
    void showErrorMessage(String message);
    void showSuccessMessage(String message);
} 