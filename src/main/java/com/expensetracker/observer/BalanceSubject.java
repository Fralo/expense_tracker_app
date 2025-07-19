package com.expensetracker.observer;

public interface BalanceSubject {
    void addObserver(BalanceObserver observer);
    void removeObserver(BalanceObserver observer);
    void notifyObservers();
} 