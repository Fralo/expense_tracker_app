package com.expensetracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton responsible for creating and providing a single {@link Connection}
 * to the underlying SQLite database.
 */
public final class DBConnection {

    private static volatile Connection instance;
    private static String databasePath;

    private DBConnection() {
        // prevent instantiation
    }

    /**
     * Initializes the database connection with a specific file path.
     * This method must be called before {@link #getInstance()}.
     *
     * @param path the path to the SQLite database file.
     */
    public static void initialize(String path) {
        databasePath = path;
    }

    /**
     * Lazily creates (or returns cached) JDBC {@link Connection} instance.
     *
     * @return a live {@link Connection}
     * @throws SQLException if the connection cannot be established.
     */
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