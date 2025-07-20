package com.expensetracker.observer;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test that verifies the Observer pattern implementation around
 * {@link Account}. It registers a {@link TestBalanceObserver}, performs two
 * balance updates (one income, one expense) and asserts that the observer has
 * seen the correct intermediate balances.
 */
class AccountObserverTest {

    private Account account;
    private TestBalanceObserver observer;

    @BeforeEach
    void setUp() {
        account = new Account();
        observer = new TestBalanceObserver();
        account.addObserver(observer);
    }

    @Test
    void observerReceivesBalanceChanges() {
        // add 100 income
        account.addIncome(new BigDecimal("100"));
        // add 25 expense
        account.addExpense(new BigDecimal("25"));

        var updates = observer.getReceivedBalances();
        assertEquals(2, updates.size(), "Observer should receive two updates");
        assertTrue(updates.get(0).compareTo(new BigDecimal("100")) == 0,
                "First update should be +100");
        assertTrue(updates.get(1).compareTo(new BigDecimal("75")) == 0,
                "Second update should be 75 (100 - 25)");
    }
} 