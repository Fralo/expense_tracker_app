package com.expensetracker.observer;

import com.expensetracker.model.Transaction;

public interface TransactionSubject {
    void subscribe(TransactionObserver observer);
    void unsubscribe(TransactionObserver observer);
    void notifyObservers(Transaction transaction);
} 