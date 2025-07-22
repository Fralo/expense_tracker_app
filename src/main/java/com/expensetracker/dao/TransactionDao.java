package com.expensetracker.dao;

import java.util.ArrayList;
import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;

public interface TransactionDao {
    void save(Transaction transaction);

    List<Transaction> findAll();

    List<Transaction> findAll(Account account, ArrayList<String> types);

    Transaction findById(long id);
}