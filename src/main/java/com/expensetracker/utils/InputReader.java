package com.expensetracker.utils;

import java.util.Scanner;

public class InputReader {
    private static InputReader instance;
    private Scanner scanner;

    private InputReader() {
        scanner = new Scanner(System.in);
    }

    public static InputReader getInstance() {
        if (instance == null) {
            instance = new InputReader();
        }
        return instance;
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public String readInput(String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    public String readInput(String prompt, String defaultValue) {
        if (defaultValue != null && !defaultValue.isEmpty()) {
            System.out.print(prompt + " (default: " + defaultValue + "): ");
        } else {
            System.out.print(prompt);
        }
        String input = readLine();
        return input.isEmpty() ? defaultValue : input;
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
