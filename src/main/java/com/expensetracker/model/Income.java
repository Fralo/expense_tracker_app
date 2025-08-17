package com.expensetracker.model;

import java.time.LocalDate;

public class Income extends Transaction {
    public Income(long account_id, long amount, LocalDate date, String description) {
        super(account_id, Math.abs(amount), date, description); // incomes are positive values
    }

    public Income(long id, long account_id, long amount, LocalDate date, String description) {
        super(id, account_id, Math.abs(amount), date, description);
    }
}