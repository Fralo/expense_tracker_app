package com.expensetracker.observer;

import java.math.BigDecimal;

public interface BalanceObserver {
    void onBalanceChange(BigDecimal newBalance);
} 