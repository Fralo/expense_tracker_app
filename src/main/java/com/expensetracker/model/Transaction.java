package com.expensetracker.model;

import java.time.LocalDate;

/**
 * Common fields shared by both {@link Expense} and {@link Income}.
 */
public abstract class Transaction {
    private long id;
    private long account_id;
    private final long amount;
    private final LocalDate date;
    private final String description;
    private final Category category;

    protected Transaction(long account_id, long amount, LocalDate date, String description) {
        this.account_id = account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = null;
    }

    protected Transaction(long account_id, long amount, LocalDate date, String description, Category category) {
        this.account_id = account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    protected Transaction(long id, long account_id, long amount, LocalDate date, String description,
            Category category) {
        this.id = id;
        this.account_id = account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public long getAccountId() {
        return account_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", amount=" + amount + ", date=" + date + ", description=" + description
                + ", category=" + category + "]";
    }
}