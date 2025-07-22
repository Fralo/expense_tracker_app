package com.expensetracker.controllers;

import java.util.ArrayList;
import java.util.List;

import com.expensetracker.converters.AmountConverter;
import com.expensetracker.converters.AmountConverterFactory;
import com.expensetracker.dao.TransactionDao;
import com.expensetracker.dao.jdbc.JdbcTransactionDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;

public class TransactionController {
    private final TransactionDao transactionDao;
    protected final AmountConverter amountConverter;

    public TransactionController() {
        this.transactionDao = new JdbcTransactionDao();
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
}
