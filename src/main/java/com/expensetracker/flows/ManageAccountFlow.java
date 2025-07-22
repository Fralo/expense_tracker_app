package com.expensetracker.flows;

import java.util.List;

import com.expensetracker.AppInstance;
import com.expensetracker.controllers.ExpenseController;
import com.expensetracker.dao.TransactionDao;
import com.expensetracker.dao.jdbc.JdbcTransactionDao;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.singleton.InputReader;
import com.expensetracker.model.Account;

public class ManageAccountFlow extends Flow {

    private Account currentAccount;
    private User currentUser;

    public ManageAccountFlow() {
        super("Manage Account");

        currentUser = AppInstance.getInstance().getCurrentUser();
        if (currentUser == null) {
            System.out.println("No user is currently logged in. Please log in first.");
            return;
        }

        currentAccount = AppInstance.getInstance().getCurrentAccount();
        if (currentAccount == null) {
            System.out.println("No account is currently selected. Please select an account first.");
            return;
        }

    }

    @Override
    public void start() {
        super.clearConsole();
        super.printHeader();

        System.out.println("Managing account: " + currentAccount.getName());
        System.out.println("Current balance: " + currentAccount.getBalance());
        execute();
    }

    @Override
    public void execute() {

        ExpenseController expenseController = new ExpenseController();

        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1) Add Expense");
            System.out.println("2) Add Income");
            System.out.println("3) List Expenses");
            System.out.println("4) List Incomes");
            System.out.println("5) Exit");

            String input = InputReader.getInstance().readInput("> ");
            switch (input) {
                case "1" -> {
                    String amount = InputReader.getInstance().readInput("Enter the amount: ");
                    String date = InputReader.getInstance().readInput("Enter the date (YYYY-MM-DD): ");
                    String description = InputReader.getInstance().readInput("Enter the description: ");

                    Expense expense = expenseController.createExpense(
                            currentAccount.getId(),
                            description,
                            amount,
                            date);
                }
                case "2" -> System.out.println("[TODO] Add Income chosen\n");
                case "3" -> {
                    List<Expense> expenses = expenseController.findAllExpenses(currentAccount);
                    for (Expense expense : expenses) {
                        System.out.println(expense);
                    }
                }
                case "4" -> System.out.println("[TODO] List Incomes chosen\n");
                case "5" -> {
                    System.out.println("Goodbye!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // clear console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.\n");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
