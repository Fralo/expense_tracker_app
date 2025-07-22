package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.dao.UserDao;
import com.expensetracker.dao.jdbc.JdbcUserDao;
import com.expensetracker.model.User;

public class UserController {

    // This class is used to manage user-related operations
    // such as user retrieval, creation, and deletion.

    private final UserDao userDao;

    public UserController() {
        this.userDao = new JdbcUserDao();
    }

    public User createUser(String username) {
        User user = new User(username);
        userDao.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
