package com.expensetracker.flows;

import com.expensetracker.AppInstance;
import com.expensetracker.dao.UserDao;
import com.expensetracker.dao.jdbc.JdbcUserDao;
import com.expensetracker.model.User;

public class OnboardingFlow extends Flow {

    public OnboardingFlow() {
        super("Onboarding");
    }

    @Override
    public void execute() {
        System.out.println("As a new user, you will need to set up your account.");
        System.out.println("Please follow the prompts to complete your onboarding process.\n\n");

        System.out.println("How should we call you?");
        String username = System.console().readLine();

        User user = new User(username);
        UserDao userDao = new JdbcUserDao();
        userDao.save(user);
        AppInstance.getInstance().setCurrentUser(user);

        System.out.println("Great, " + username + "! Let's set up your first account.");

        Flow createAccountFlow = new CreateAccountFlow();
        createAccountFlow.start();

        System.out.println("You can now start tracking your expenses and managing your finances.");
        System.out.println("Thank you for using the Expense Tracker!");
    }
}
