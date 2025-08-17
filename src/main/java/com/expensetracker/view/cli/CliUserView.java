package com.expensetracker.view.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.expensetracker.model.User;
import com.expensetracker.utils.InputReader;
import com.expensetracker.view.UserView;

public class CliUserView extends CliBaseView implements UserView {
    @Override
    public User selectUser(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return null;
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
        System.out.println("[" + (userMap.size() + 1) + "] Create New User");
        System.out.println("[" + (userMap.size() + 2) + "] Exit");
        System.out.print("Choice: ");
        String choice = InputReader.getInstance().readLine();

        if (userMap.containsKey(choice)) {
            return userMap.get(choice);
        } else if (choice.equals(String.valueOf(userMap.size() + 1))) {
            return new User(-1, "new_user"); // Special user to indicate creation
        } else if (choice.equals(String.valueOf(userMap.size() + 2)) || choice.equalsIgnoreCase("exit")) {
            return null;
        }
        return null;
    }

    @Override
    public String askForUsername() {
        System.out.print("Enter username: ");
        return InputReader.getInstance().readLine();
    }
}