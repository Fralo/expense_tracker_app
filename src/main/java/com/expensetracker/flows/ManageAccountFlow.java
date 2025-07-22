package com.expensetracker.flows;

import java.util.List;

import com.expensetracker.AppInstance;
import com.expensetracker.controllers.ExpenseController;
import com.expensetracker.controllers.IncomeController;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
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
        IncomeController incomeController = new IncomeController();

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

                    expenseController.createExpense(
                            currentAccount.getId(),
                            description,
                            amount,
                            date);
                }
                case "2" -> {
                    String amount = InputReader.getInstance().readInput("Enter the amount: ");
                    String date = InputReader.getInstance().readInput("Enter the date (YYYY-MM-DD): ");
                    String description = InputReader.getInstance().readInput("Enter the description: ");

                    incomeController.createIncome(
                            currentAccount.getId(),
                            description,
                            amount,
                            date);
                }
                case "3" -> {
                    List<Expense> expenses = expenseController.findAllExpenses(currentAccount);
                    for (Expense expense : expenses) {
                        System.out.println(expense);
                    }
                }
                case "4" -> {
                    List<Income> incomes = incomeController.findAllIncomes(currentAccount);
                    for (Income income : incomes) {
                        System.out.println(income);
                    }
                }
                case "5" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.\n");
            }
        }
    }
}
