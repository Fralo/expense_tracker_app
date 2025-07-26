package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;
import com.expensetracker.services.TransactionService;
import com.expensetracker.view.AppView;

public class TransactionController {
    private final TransactionService transactionService;
    private final AppView view;

    public TransactionController(AppView view) {
        this.view = view;
        this.transactionService = new TransactionService();
    }

    public void handleAddExpense(Account account) {
        String amount = view.askForAmount();
        String description = view.askForDescription();
        transactionService.addExpense(account, amount, description);
        view.showSuccessMessage("Expense added successfully.");
    }

    public void handleAddIncome(Account account) {
        String amount = view.askForAmount();
        String description = view.askForDescription();
        transactionService.addIncome(account, amount, description);
        view.showSuccessMessage("Income added successfully.");
    }

    public void handleViewTransactions(Account account) {
        List<Transaction> transactions = transactionService.getAllTransactions(account);
        view.showTransactions(transactions);
    }
}
