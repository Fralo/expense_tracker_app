package com.expensetracker.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;

public class IncomeController extends TransactionController {

    public IncomeController() {
        super();
    }

    public Income createIncome(long accountId, String description, String amount, String date) {
        long amountInCents = this.amountConverter.convertToCents(amount);
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        Income income = new Income(accountId, amountInCents, localDate, description);
        return (Income) super.createTransaction(income);
    }

    public List<Income> findAllIncomes(Account account) {
        List<Transaction> transactions = super.getAllTransactions(account, new ArrayList<>(List.of("INCOME")));
        return transactions.stream()
                .filter(transaction -> transaction instanceof Income)
                .map(transaction -> (Income) transaction)
                .toList();
    }
}
