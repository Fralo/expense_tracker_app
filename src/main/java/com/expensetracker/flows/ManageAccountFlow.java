package com.expensetracker.flows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.expensetracker.AppInstance;
import com.expensetracker.dao.TransactionDao;
import com.expensetracker.dao.jdbc.JdbcTransactionDao;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.model.Account;

public class ManageAccountFlow extends Flow {
    @Override
    public void execute() {

        User currentUser = AppInstance.getInstance().getCurrentUser();
        if (currentUser == null) {
            System.out.println("No user is currently logged in. Please log in first.");
            return;
        }

        Account currentAccount = AppInstance.getInstance().getCurrentAccount();
        if (currentAccount == null) {
            System.out.println("No account is currently selected. Please select an account first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1) Add Expense");
            System.out.println("2) Add Income");
            System.out.println("3) List Expenses");
            System.out.println("4) List Incomes");
            System.out.println("5) Exit");
            System.out.print("Choice: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Enter the amount: ");
                    BigDecimal amount = new BigDecimal(scanner.nextLine());
                    System.out.println("Enter the date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter the description: ");
                    String description = scanner.nextLine();
                    Expense expense = new Expense(amount, date, description);
                    TransactionDao transactionDao = new JdbcTransactionDao();
                    transactionDao.save(expense);
                }
                case "2" -> System.out.println("[TODO] Add Income chosen\n");
                case "3" -> {
                    TransactionDao transactionDao = new JdbcTransactionDao();
                    List<Transaction> expenses = transactionDao.findAll();
                    for (Transaction expense : expenses) {
                        System.out.println(expense);
                    }
                }
                case "4" -> System.out.println("[TODO] List Incomes chosen\n");
                case "5" -> {
                    System.out.println("Goodbye!");
                    scanner.close();
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
