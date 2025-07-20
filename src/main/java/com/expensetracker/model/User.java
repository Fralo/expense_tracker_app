package com.expensetracker.model;

public class User {
    private long id;
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
}