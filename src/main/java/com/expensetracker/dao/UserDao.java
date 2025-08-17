package com.expensetracker.dao;

import java.util.List;
import java.util.Optional;

import com.expensetracker.model.User;

public interface UserDao {
    void save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(long id);
    List<User> findAll();
} 