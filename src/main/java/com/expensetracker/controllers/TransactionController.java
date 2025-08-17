package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;
import com.expensetracker.services.TransactionService;
import com.expensetracker.utils.converters.AmountConverter;
import com.expensetracker.utils.converters.AmountConverterFactory;
import com.expensetracker.view.CommonView;
import com.expensetracker.view.TransactionView;

public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionView transactionView;
    private final CommonView commonView;
    private final AmountConverter amountConverter;

    public TransactionController(TransactionView transactionView, CommonView commonView,
            TransactionService transactionService) {
        this.transactionView = transactionView;
        this.commonView = commonView;
        this.transactionService = transactionService;
        this.amountConverter = AmountConverterFactory.getDefaultConverter();
    }

    public long getAmount() {
        String amountString;
        do {
            amountString = transactionView.askForAmount();
            try {
                long amountCents = amountConverter.convertToCents(amountString);
                return amountCents;
            } catch (IllegalArgumentException e) {
                transactionView.showErrorMessage("Invalid amount. Please enter a valid number.");
            }
        } while (true);
    }

    public void handleAddExpense(Account account) {
        long amount = getAmount();
        String description = transactionView.askForDescription();
        transactionService.addExpense(account, amount, description);
        commonView.showSuccessMessage("Expense added successfully.");
    }

    public void handleAddIncome(Account account) {
        long amount = getAmount();
        String description = transactionView.askForDescription();
        transactionService.addIncome(account, amount, description);
        commonView.showSuccessMessage("Income added successfully.");
    }

    public void handleViewTransactions(Account account) {
        List<Transaction> transactions = transactionService.getAllTransactions(account);
        transactionView.showTransactions(transactions);
    }
}
