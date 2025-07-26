package com.expensetracker.services;

import java.util.List;
import java.util.Optional;

import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        this.accountDao = new JdbcAccountDao();
    }

    public Account createAccount(long userId, String name, long balance) {
        Account account = new Account(userId, name, balance);
        accountDao.save(account);
        return account;
    }

    public Optional<Account> getAccountById(long id) {
        return accountDao.findById(id);
    }

    public List<Account> getAllAccounts(User user) {
        return accountDao.findAll(user);
    }
}
