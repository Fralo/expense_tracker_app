package com.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Income extends Transaction {
    public Income(BigDecimal amount, LocalDate date, String description, Category category) {
        super(amount, date, description, category);
    }
} 