package com.expensetracker.services;

import java.util.List;
import java.util.Optional;

import com.expensetracker.dao.AccountDao;
import com.expensetracker.dao.jdbc.JdbcAccountDao;
import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.observer.TransactionObserver;

public class AccountService implements TransactionObserver {
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

    @Override
    public void update(Transaction transaction) {
        Optional<Account> accountOpt = accountDao.findById(transaction.getAccountId());
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setBalance(account.getBalance() + transaction.getAmount());
            accountDao.update(account);
        }
    }
}
