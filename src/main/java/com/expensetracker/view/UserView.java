package com.expensetracker.view;

import java.util.List;

import com.expensetracker.model.User;

public interface UserView {
    User selectUser(List<User> users);
    String askForUsername();
} 