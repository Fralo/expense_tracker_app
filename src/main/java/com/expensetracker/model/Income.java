package com.expensetracker.model;

import java.time.LocalDate;

public class Income extends Transaction {
    public Income(long account_id, long amount, LocalDate date, String description) {
        super(account_id, amount, date, description); // incomes are positive values
    }

    public Income(long account_id, long amount, LocalDate date, String description, Category category) {
        super(account_id, amount, date, description, category);
    }

    public Income(long id, long account_id, long amount, LocalDate date, String description, Category category) {
        super(id, account_id, amount, date, description, category);
    }
}