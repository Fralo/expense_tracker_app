package com.expensetracker.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.expensetracker.db.DBConnection;
import com.expensetracker.model.User;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setup() throws SQLException {
        DBConnection.initialize("test.db");
        userService = new UserService();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DBConnection.getInstance();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
        }
    }

    @Test
    void testCreateUser() {
        User user = userService.createUser("testuser");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("testuser", user.getUsername());
        Assertions.assertTrue(user.getId() > 0);
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {
        userService.createUser("testuser");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("testuser");
        });
    }

    @Test
    void testCreateUser_NullUsername() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null);
        });
    }

    @Test
    void testCreateUser_EmptyUsername() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("");
        });
    }

    @Test
    void testCreateUser_WhitespaceUsername() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("  ");
        });
    }
}