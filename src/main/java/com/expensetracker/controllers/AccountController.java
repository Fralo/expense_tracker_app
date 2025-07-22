package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public class AccountController {
    private final AccountDao accountDao;

    public AccountController() {
        this.accountDao = new JdbcAccountDao();
    }

    public Account createAccount(long userId, String name, long balance) {
        Account account = new Account(userId, name, balance);
        accountDao.save(account);
        return account;
    }

    public Account getAccountById(long id) {
        return accountDao.findById(id).orElse(null);
    }

    public List<Account> getAllAccounts(User user) {
        return accountDao.findAll(user);
    }
}
