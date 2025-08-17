package com.expensetracker.view.cli;

import com.expensetracker.utils.InputReader;
import com.expensetracker.view.MainView;

public class CliMainView extends CliBaseView implements MainView {
    @Override
    public int showMainMenu() {
        clearConsole();

        System.out.println("\n");
        System.out.println("=== What would you like to do? ===");
        System.out.println("1) Manage Account");
        System.out.println("2) Create Account");
        System.out.println("3) Logout");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}