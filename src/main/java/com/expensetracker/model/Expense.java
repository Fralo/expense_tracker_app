package com.expensetracker.model;

import java.time.LocalDate;

public class Expense extends Transaction {

    public Expense(long account_id, long amount, LocalDate date, String description) {
        super(account_id, amount * -1, date, description); // expenses are negative values
    }

    public Expense(long account_id, long amount, LocalDate date, String description, Category category) {
        super(account_id, amount * -1, date, description, category); // expenses are negative values
    }

    public Expense(long id, long account_id, long amount, LocalDate date, String description, Category category) {
        super(id, account_id, amount * (-1), date, description, category); // expenses are negative values
    }
}