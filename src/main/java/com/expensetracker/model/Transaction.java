package com.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Common fields shared by both {@link Expense} and {@link Income}.
 */
public abstract class Transaction {
    private final UUID id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
    private final Category category;

    protected Transaction(BigDecimal amount, LocalDate date, String description, Category category) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public UUID getId() {
        return id;
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
} 