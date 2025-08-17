package com.expensetracker.model;

import java.time.LocalDate;

public abstract class Transaction extends BaseModel {
    private long account_id;
    private final long amount;
    private final LocalDate date;
    private final String description;

    protected Transaction(long account_id, long amount, LocalDate date, String description) {
        this.account_id = account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    protected Transaction(long id, long account_id, long amount, LocalDate date, String description) {
        super(id);
        this.account_id = account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public long getAccountId() {
        return account_id;
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

    @Override
    public String toString() {
        return "Transaction [id=" + getId() + ", amount=" + getAmount() + ", date=" + getDate() + ", description=" + getDescription()
                + "]";
    }
}