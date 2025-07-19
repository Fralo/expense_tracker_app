package com.expensetracker;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.expensetracker.db.DBConnection;

class DBConnectionTest {
    @Test
    void connectionCouldBeEstablished() {
        try {
            Connection connection = DBConnection.getInstance();
            Assertions.assertNotNull(connection, "Connection should not be null");
        } catch (SQLException e) {
            Assertions.fail("Should connect to DB (ensure Postgres is running and env vars are set): " + e.getMessage());
        }
    }
} 