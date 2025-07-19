package com.expensetracker.dao;

import java.util.List;
import java.util.UUID;

import com.expensetracker.model.Transaction;

public interface TransactionDao {
    void save(Transaction transaction);
    List<Transaction> findAll();
    Transaction findById(UUID id);
} 