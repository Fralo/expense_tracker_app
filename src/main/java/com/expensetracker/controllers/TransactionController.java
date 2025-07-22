package com.expensetracker.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.expensetracker.converters.AmountConverter;
import com.expensetracker.converters.AmountConverterFactory;
import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.TransactionDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.dao.jdbc.JdbcTransactionDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;

public class TransactionController {
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final AmountConverter amountConverter;

    public TransactionController() {
        this.transactionDao = new JdbcTransactionDao();
        this.accountDao = new JdbcAccountDao();
        this.amountConverter = AmountConverterFactory.getDefaultConverter();
    }

    public Transaction createTransaction(Transaction transaction) {
        transactionDao.save(transaction);
        return transaction;
    }

    public Transaction getTransactionById(long id) {
        return transactionDao.findById(id);
    }

    public List<Transaction> getAllTransactions(Account account, ArrayList<String> types) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        return transactionDao.findAll(account, types);
    }

    public List<Transaction> getAllTransactions(Account account, String type) {
        return getAllTransactions(account, new ArrayList<>(List.of(type)));
    }

    public void addExpense(Account account, String amount, String description) {
        long amountCents = amountConverter.convertToCents(amount);
        account.setBalance(account.getBalance() - amountCents);
        accountDao.update(account);
        Expense expense = new Expense(account.getId(), amountCents, LocalDate.now(), description);
        transactionDao.save(expense);
    }

    public void addIncome(Account account, String amount, String description) {
        long amountCents = amountConverter.convertToCents(amount);
        account.setBalance(account.getBalance() + amountCents);
        accountDao.update(account);
        Income income = new Income(account.getId(), amountCents, LocalDate.now(), description);
        transactionDao.save(income);
    }

    public List<Transaction> getAllTransactions(Account account) {
        return transactionDao.findAll(account, new ArrayList<>(List.of("expense", "income")));
    }
}
