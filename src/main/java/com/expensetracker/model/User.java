package com.expensetracker.model;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String username;
    private final String passwordHash;

    public User(String username, String passwordHash) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
} 