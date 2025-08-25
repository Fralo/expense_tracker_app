package com.expensetracker;

import com.expensetracker.controllers.MainController;
import com.expensetracker.db.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection.initialize("expense_tracker.db");

        MainController mainController = new MainController();
        mainController.start();
    }
}