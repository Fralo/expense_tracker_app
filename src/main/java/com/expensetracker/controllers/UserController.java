package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.AppInstance;
import com.expensetracker.model.User;
import com.expensetracker.services.UserService;
import com.expensetracker.view.AppView;

public class UserController {

    private final UserService userService;
    private final AppView view;

    public UserController(AppView view) {
        this.view = view;
        this.userService = new UserService();
    }

    public User handleUserSelection() {
        List<User> users = userService.getAllUsers();
        User selectedUser = null;

        if (users.isEmpty()) {
            view.showSuccessMessage("No users found. Let's create the first one.");

            boolean userCreated = false;
            while (!userCreated) {
                try {
                    String newUsername = view.askForUsername();
                    if (newUsername.equalsIgnoreCase("exit")) {
                        return null;
                    }
                    selectedUser = userService.createUser(newUsername);
                    view.showSuccessMessage("User created successfully.");
                    userCreated = true;
                } catch (IllegalArgumentException e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        } else {
            selectedUser = view.selectUser(users);
            if (selectedUser == null) { // User wants to exit
                return null;
            }

            if (selectedUser.getId() == -1) {
                String newUsername = view.askForUsername();
                if (newUsername.equalsIgnoreCase("exit")) {
                    return null;
                }
                selectedUser = userService.createUser(newUsername);
                view.showSuccessMessage("User created successfully.");
            }
        }

        if (selectedUser != null) {
            AppInstance.getInstance().setCurrentUser(selectedUser);
        }

        return selectedUser;
    }

}
