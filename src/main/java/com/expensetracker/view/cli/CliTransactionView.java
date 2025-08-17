package com.expensetracker.view.cli;

import java.util.List;

import com.expensetracker.model.Transaction;
import com.expensetracker.utils.InputReader;
import com.expensetracker.view.TransactionView;

public class CliTransactionView extends CliBaseView implements TransactionView {
    @Override
    public void showTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-15s | %-30s%n", "Type", "Date", "Amount", "Description");
        System.out.println("--------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            String type = transaction instanceof com.expensetracker.model.Expense ? "Expense" : "Income";
            System.out.printf("%-10s | %-12s | %-15.2f | %-30s%n",
                    type,
                    transaction.getDate(),
                    transaction.getAmount() / 100.0,
                    transaction.getDescription());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Override
    public String askForAmount() {
        System.out.print("Enter amount: ");
        return InputReader.getInstance().readLine();
    }

    @Override
    public String askForDescription() {
        System.out.print("Enter description: ");
        return InputReader.getInstance().readLine();
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
    }
}