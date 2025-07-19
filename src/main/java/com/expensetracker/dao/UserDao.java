package com.expensetracker.dao;

import com.expensetracker.model.User;

import java.util.Optional;

public interface UserDao {
    void save(User user);
    Optional<User> findByUsername(String username);
} 