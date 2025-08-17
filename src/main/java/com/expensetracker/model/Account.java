package com.expensetracker.model;

public class Account extends BaseModel {

    private long userId;
    private final String name;
    private long balance;

    public Account(long userId, String name, long balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public Account(long id, long userId, String name, long balance) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.balance = balance;
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

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + getId() +
                ", userId=" + getUserId() +
                ", name='" + getName() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}