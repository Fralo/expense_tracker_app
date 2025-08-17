package com.expensetracker.observer;

import com.expensetracker.model.Transaction;

public interface TransactionObserver {
    void update(Transaction transaction);
} 