package com.expensetracker.dao;

import java.util.List;

import com.expensetracker.model.Transaction;

public interface TransactionDao {
    void save(Transaction transaction);

    List<Transaction> findAll();

    List<Transaction> findAll(String type);

    Transaction findById(long id);
}