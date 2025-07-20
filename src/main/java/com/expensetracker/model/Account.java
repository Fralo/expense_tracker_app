package com.expensetracker.model;

import java.math.BigInteger;

public class Account {

    private long id;
    private long userId;
    private final String name;
    private final BigInteger balance;
    private final BigInteger threshold_balance;

    public Account(long userId, String name, BigInteger balance, BigInteger threshold_balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.threshold_balance = threshold_balance;
    }

    public Account(long userId, String name, BigInteger balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.threshold_balance = null;
    }

    public Account(long id, long userId, String name, BigInteger balance, BigInteger threshold_balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.threshold_balance = threshold_balance;
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

    public BigInteger getBalance() {
        return balance;
    }

    public BigInteger getThresholdBalance() {
        return threshold_balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", threshold_balance=" + threshold_balance +
                '}';
    }
}