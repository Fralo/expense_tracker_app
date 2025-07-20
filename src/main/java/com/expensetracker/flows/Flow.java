package com.expensetracker.flows;

public abstract class Flow {

    private String name;

    public Flow(String name) {
        this.name = name;
    }

    public abstract void execute();

    protected void clearConsole() {
        // This method is a placeholder for clearing the console.
        // Actual implementation may vary based on the environment.
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected void printHeader() {
        System.out.println("=== " + name + " ===");
    }

    public void start() {
        clearConsole();
        printHeader();
        execute();
    }
}
