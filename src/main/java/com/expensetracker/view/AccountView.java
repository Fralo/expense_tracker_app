package com.expensetracker.view;

import java.util.List;

import com.expensetracker.model.Account;

public interface AccountView {
    Account selectAccount(List<Account> accounts);
    int showAccountMenu(Account account);
    String askForAccountName();
    String askForAccountInitialBalance();
} 