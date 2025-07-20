package com.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Common fields shared by both {@link Expense} and {@link Income}.
 */
public abstract class Transaction {
    private long id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
    private final Category category;

    protected Transaction(BigDecimal amount, LocalDate date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = null;
    }

    protected Transaction(BigDecimal amount, LocalDate date, String description, Category category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    protected Transaction(long id, BigDecimal amount, LocalDate date, String description, Category category) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
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
        return "Transaction [id=" + id + ", amount=" + amount + ", date=" + date + ", description=" + description + ", category=" + category + "]";
    }
} 