package com.expensetracker;

import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public class AppInstance {
    private static AppInstance instance;
    private User currentUser;
    private Account currentAccount;

    private AppInstance() {
        // Private constructor to prevent instantiation
    }

    public static AppInstance getInstance() {
        if (instance == null) {
            instance = new AppInstance();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void clearCurrentUser() {
        this.currentUser = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }
}
