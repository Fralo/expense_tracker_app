package com.expensetracker;

import com.expensetracker.controllers.MainController;
import com.expensetracker.db.DBConnection;

/**
 * Simple CLI entry point. Currently, it just verifies the DB connection and
 * then asks which high-level operation the user wants to perform. The actual
 * implementations will be provided incrementally.
 */
public class Main {

    public static void main(String[] args) {
        DBConnection.initialize("expense_tracker.db");

        MainController mainController = new MainController();
        mainController.start();
    }
}