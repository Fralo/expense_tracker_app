package com.expensetracker.view;

import java.util.List;

import com.expensetracker.model.Account;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;

public interface AppView {
    void showWelcomeMessage();
    void showGoodbyeMessage();
    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    User selectUser(List<User> users);
    int showMainMenu();
    Account selectAccount(List<Account> accounts);
    int showAccountMenu(Account account);
    void showTransactions(List<Transaction> transactions);

    String askForUsername();
    String askForAccountName();
    String askForAmount();
    String askForDescription();
} 