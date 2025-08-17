package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.model.User;
import com.expensetracker.services.UserService;
import com.expensetracker.utils.AppInstance;
import com.expensetracker.view.CommonView;
import com.expensetracker.view.UserView;

public class UserController {

    private final UserService userService;
    private final UserView userView;
    private final CommonView commonView;

    public UserController(UserView userView, CommonView commonView) {
        this.userView = userView;
        this.commonView = commonView;
        this.userService = new UserService();
    }

    public User handleUserSelection() {
        List<User> users = userService.getAllUsers();
        User selectedUser = null;

        if (users.isEmpty()) {
            commonView.showSuccessMessage("No users found. Let's create the first one.");

            boolean userCreated = false;
            while (!userCreated) {
                try {
                    String newUsername = userView.askForUsername();
                    if (newUsername.equalsIgnoreCase("exit")) {
                        return null;
                    }
                    selectedUser = userService.createUser(newUsername);
                    commonView.showSuccessMessage("User created successfully.");
                    userCreated = true;
                } catch (IllegalArgumentException e) {
                    commonView.showErrorMessage(e.getMessage());
                }
            }
        } else {
            selectedUser = userView.selectUser(users);
            if (selectedUser == null) { // User wants to exit
                return null;
            }

            if (selectedUser.getId() == -1) {
                boolean userCreated = false;
                while (!userCreated) {
                    try {
                        String newUsername = userView.askForUsername();
                        if (newUsername.equalsIgnoreCase("exit")) {
                            return null;
                        }
                        selectedUser = userService.createUser(newUsername);
                        commonView.showSuccessMessage("User created successfully.");
                        userCreated = true;
                    } catch (IllegalArgumentException e) {
                        commonView.showErrorMessage(e.getMessage());
                    }
                }
            }
        }

        if (selectedUser != null) {
            AppInstance.getInstance().setCurrentUser(selectedUser);
        }

        return selectedUser;
    }

}
