package com.expensetracker.dao;

import java.util.List;
import java.util.Optional;

import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public interface AccountDao {
    void save(Account user);

    void update(Account account);

    Optional<Account> findById(long id);

    List<Account> findAll(User user);
}