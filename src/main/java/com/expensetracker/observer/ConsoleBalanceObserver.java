package com.expensetracker.observer;

import java.math.BigDecimal;

/**
 * Simple {@link BalanceObserver} that prints the new balance to the console.
 */
public class ConsoleBalanceObserver implements BalanceObserver {

    @Override
    public void onBalanceChange(BigDecimal newBalance) {
        System.out.printf("\uD83D\uDCB0  Current balance: %s\n", newBalance);
    }
} 