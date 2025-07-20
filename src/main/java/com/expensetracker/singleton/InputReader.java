package com.expensetracker.singleton;

import java.util.Scanner;

public class InputReader {
    private static InputReader instance;
    private Scanner scanner;

    private InputReader() {
        // Private constructor to prevent instantiation
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

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
