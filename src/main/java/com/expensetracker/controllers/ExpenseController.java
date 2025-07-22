package com.expensetracker.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Transaction;

public class ExpenseController extends TransactionController {

    public ExpenseController() {
        super();
    }

    public Expense createExpense(long accountId, String description, String amount, String date) {
        long amountInCents = this.amountConverter.convertToCents(amount);
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        Expense expense = new Expense(accountId, amountInCents, localDate, description);
        return (Expense) super.createTransaction(expense);
    }

    public List<Expense> findAllExpenses(Account account) {
        List<Transaction> transactions = super.getAllTransactions(account, new ArrayList<>(List.of("EXPENSE")));
        return transactions.stream()
                .filter(transaction -> transaction instanceof Expense)
                .map(transaction -> (Expense) transaction)
                .toList();
    }

}
