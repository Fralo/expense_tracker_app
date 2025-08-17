package com.expensetracker.model;

public class User extends BaseModel {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public User(long id, String username) {
        super(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}