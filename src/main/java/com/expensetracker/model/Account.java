package com.expensetracker.model;

public class Account {

    private long id;
    private long userId;
    private final String name;
    private final long balance;

    public Account(long userId, String name, long balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public Account(long id, long userId, String name, long balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}