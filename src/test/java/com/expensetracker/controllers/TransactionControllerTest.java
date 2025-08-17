package com.expensetracker.controllers;

import com.expensetracker.model.Account;
import com.expensetracker.services.TransactionService;
import com.expensetracker.view.CommonView;
import com.expensetracker.view.TransactionView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionView transactionView;

    @Mock
    private CommonView commonView;

    @InjectMocks
    private TransactionController transactionController;

    private Account testAccount;

    @Before
    public void setUp() {
        testAccount = new Account(1, 1, "Test Account", 1000L);
    }

    @Test
    public void testHandleAddExpense() {
        when(transactionView.askForAmount()).thenReturn("100.0");
        when(transactionView.askForDescription()).thenReturn("Test Expense");

        transactionController.handleAddExpense(testAccount);

        verify(transactionService, times(1)).addExpense(testAccount, 10000, "Test Expense");
        verify(commonView, times(1)).showSuccessMessage("Expense added successfully.");
    }

    @Test
    public void testHandleAddIncome() {
        when(transactionView.askForAmount()).thenReturn("200.0");
        when(transactionView.askForDescription()).thenReturn("Test Income");

        transactionController.handleAddIncome(testAccount);

        verify(transactionService, times(1)).addIncome(testAccount, 20000, "Test Income");
        verify(commonView, times(1)).showSuccessMessage("Income added successfully.");
    }
} 