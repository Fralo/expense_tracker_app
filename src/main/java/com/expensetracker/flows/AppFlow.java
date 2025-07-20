package com.expensetracker.flows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.expensetracker.AppInstance;
import com.expensetracker.dao.UserDao;
import com.expensetracker.dao.jdbc.JdbcUserDao;
import com.expensetracker.model.User;
import com.expensetracker.singleton.InputReader;

public class AppFlow extends Flow {

    public AppFlow() {
        super("Expense Tracker");
    }

    @Override
    public void execute() {
        UserDao userDao = new JdbcUserDao();

        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found. Starting onboarding flow...");
            Flow onboardingFlow = new OnboardingFlow();
            onboardingFlow.start();
            users = userDao.findAll();
        }

        Map<String, User> userMap = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            String optionChoice = String.valueOf(i + 1);
            userMap.put(optionChoice, users.get(i));
        }

        System.out.println("Please select a user to continue:");
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            System.out.println("[" + entry.getKey() + "] " + entry.getValue().getUsername());
        }
        System.out.println("[" + (userMap.size() + 1) + "] Exit");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();

        if (choice.equals(String.valueOf(userMap.size() + 1))) {
            System.out.println("Exiting...");
            return;
        }

        User selectedUser = userMap.get(choice);
        if (selectedUser == null) {
            System.out.println("Invalid choice. Exiting...");
            return;
        }

        AppInstance.getInstance().setCurrentUser(selectedUser);

        Flow mainUserFlow = new MainUserFlow();
        mainUserFlow.start();

        AppInstance.getInstance().clearCurrentUser();
    }

}
