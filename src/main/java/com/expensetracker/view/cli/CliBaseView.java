package com.expensetracker.view.cli;

public class CliBaseView {
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
} 