package com.expensetracker.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.expensetracker.dao.TransactionDao;
import com.expensetracker.dao.jdbc.JdbcTransactionDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;
import com.expensetracker.observer.TransactionObserver;
import com.expensetracker.observer.TransactionSubject;

public class TransactionService implements TransactionSubject {
    private final TransactionDao transactionDao;
    private final List<TransactionObserver> observers = new ArrayList<>();

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public TransactionService() {
        this.transactionDao = new JdbcTransactionDao();
    }

    @Override
    public void subscribe(TransactionObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(TransactionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Transaction transaction) {
        for (TransactionObserver observer : observers) {
            observer.update(transaction);
        }
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

    public void addExpense(Account account, long amount, String description) {
        Expense expense = new Expense(account.getId(), amount, LocalDate.now(), description);
        transactionDao.save(expense);
        notifyObservers(expense);
    }

    public void addIncome(Account account, long amount, String description) {
        Income income = new Income(account.getId(), amount, LocalDate.now(), description);
        transactionDao.save(income);
        notifyObservers(income);
    }

    public List<Transaction> getAllTransactions(Account account) {
        return transactionDao.findAll(account, new ArrayList<>(List.of("EXPENSE", "INCOME")));
    }
}
