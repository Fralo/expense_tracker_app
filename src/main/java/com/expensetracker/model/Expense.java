package com.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Expense extends Transaction {
    public Expense(BigDecimal amount, LocalDate date, String description, Category category) {
        super(amount.negate(), date, description, category); // expenses are negative values
    }

    public Expense(UUID id, BigDecimal amount, LocalDate date, String description, Category category) {
        super(id, amount.negate(), date, description, category); // expenses are negative values
    }
} 