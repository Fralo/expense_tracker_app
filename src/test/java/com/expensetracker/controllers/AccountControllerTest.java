package com.expensetracker.controllers;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import com.expensetracker.model.Account;
import com.expensetracker.model.User;
import com.expensetracker.services.AccountService;
import com.expensetracker.utils.AppInstance;
import com.expensetracker.view.AccountView;
import com.expensetracker.view.CommonView;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountView accountView;

    @Mock
    private CommonView commonView;

    @Mock
    private TransactionController transactionController;

    @InjectMocks
    private AccountController accountController;

    private User testUser;
    private Account testAccount;

    @Before
    public void setUp() {
        testUser = new User(1, "testuser");
        testAccount = new Account(1, 1, "Test Account", 100000L);
        AppInstance.getInstance().setCurrentUser(testUser);
    }

    @Test
    public void testHandleAccountManagement() {
        when(accountService.getAllAccounts(testUser)).thenReturn(Collections.singletonList(testAccount));
        when(accountView.selectAccount(Collections.singletonList(testAccount))).thenReturn(testAccount);
        when(accountService.getAccountById(testAccount.getId())).thenReturn(Optional.of(testAccount));
        when(accountView.showAccountMenu(testAccount)).thenReturn(4); // Exit menu

        accountController.handleAccountManagement();

        verify(accountService, times(1)).getAllAccounts(testUser);
        verify(accountView, times(1)).selectAccount(Collections.singletonList(testAccount));
        verify(accountService, times(1)).getAccountById(testAccount.getId());
        verify(accountView, times(1)).showAccountMenu(testAccount);
    }

    @Test
    public void testHandleCreateAccount() {
        when(accountView.askForAccountName()).thenReturn("New Account");
        when(accountView.askForAccountInitialBalance()).thenReturn("70");

        accountController.handleCreateAccount();

        verify(accountService, times(1)).createAccount(testUser.getId(), "New Account", 7000L);
        verify(commonView, times(1)).showSuccessMessage("Account created successfully.");
    }
} 