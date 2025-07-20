package com.expensetracker.observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete subject in the Observer pattern. It maintains a running balance that
 * is updated whenever a new transaction amount is applied through
 * {@link #applyAmount(BigDecimal)} or the convenience
 * {@link #addIncome(BigDecimal)} / {@link #addExpense(BigDecimal)} methods.
 * <p>
 * After each update all registered {@link BalanceObserver}s are notified with
 * the fresh balance value.
 */
public class Account implements BalanceSubject {

    /** Current balance */
    private BigDecimal balance = BigDecimal.ZERO;

    /** Mutable list of observers */
    private final List<BalanceObserver> observers = new ArrayList<>();

    // ---------------------------------------------------------------------
    // Balance operations
    // ---------------------------------------------------------------------

    /**
     * Applies the given amount to the current balance and notifies observers.
     *
     * @param amount a positive or negative amount that should be added to the
     *               current balance
     */
    public void applyAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount cannot be null");
        }
        balance = balance.add(amount);
        notifyObservers();
    }

    /** Convenience helper for positive amounts. */
    public void addIncome(BigDecimal income) {
        if (income == null) {
            throw new IllegalArgumentException("income cannot be null");
        }
        applyAmount(income);
    }

    /** Convenience helper for expenses (passed as positive numbers). */
    public void addExpense(BigDecimal expense) {
        if (expense == null) {
            throw new IllegalArgumentException("expense cannot be null");
        }
        applyAmount(expense.negate());
    }

    /** Returns an immutable view of the current balance. */
    public BigDecimal getBalance() {
        return balance;
    }

    // ---------------------------------------------------------------------
    // Observer pattern implementation
    // ---------------------------------------------------------------------

    @Override
    public void addObserver(BalanceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BalanceObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // Create a defensive copy so observers can modify the list if they want
        for (BalanceObserver observer : List.copyOf(observers)) {
            observer.onBalanceChange(balance);
        }
    }

    /** Returns an unmodifiable list view of current observers (useful for tests). */
    public List<BalanceObserver> getObservers() {
        return Collections.unmodifiableList(observers);
    }
} 