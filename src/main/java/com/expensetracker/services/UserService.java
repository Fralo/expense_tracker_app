package com.expensetracker.services;

import java.util.List;

import com.expensetracker.dao.UserDao;
import com.expensetracker.dao.jdbc.JdbcUserDao;
import com.expensetracker.model.User;

public class UserService {

    // This class is used to manage user-related operations
    // such as user retrieval, creation, and deletion.

    private final UserDao userDao;

    public UserService() {
        this.userDao = new JdbcUserDao();
    }

    public User createUser(String username) throws IllegalArgumentException {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if (userDao.findByUsername(username).orElse(null) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User user = new User(username);
        userDao.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
