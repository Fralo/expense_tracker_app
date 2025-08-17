package com.expensetracker.view;

import java.util.List;

import com.expensetracker.model.Transaction;

public interface TransactionView {
    void showTransactions(List<Transaction> transactions);
    String askForAmount();
    String askForDescription();
    void showErrorMessage(String message);
} 