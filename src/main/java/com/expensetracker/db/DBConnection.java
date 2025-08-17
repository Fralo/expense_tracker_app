package com.expensetracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static volatile Connection instance;
    private static String databasePath;

    private DBConnection() {
    }

    public static void initialize(String path) {
        databasePath = path;
    }

    public static Connection getInstance() throws SQLException {
        if (databasePath == null) {
            throw new IllegalStateException("Database path not initialized. Call initialize() first.");
        }
        if (instance == null || instance.isClosed()) {
            synchronized (DBConnection.class) {
                if (instance == null || instance.isClosed()) {
                    instance = createConnection();
                }
            }
        }
        return instance;
    }

    private static Connection createConnection() throws SQLException {
        String url = "jdbc:sqlite:" + databasePath;
        return DriverManager.getConnection(url);
    }
}