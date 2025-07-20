package com.expensetracker.observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper observer implementation that stores all balance updates in a list so
 * that unit tests can make assertions on them.
 */
public class TestBalanceObserver implements BalanceObserver {

    private final List<BigDecimal> receivedBalances = new ArrayList<>();

    @Override
    public void onBalanceChange(BigDecimal newBalance) {
        receivedBalances.add(newBalance);
    }

    public List<BigDecimal> getReceivedBalances() {
        return List.copyOf(receivedBalances);
    }
} 