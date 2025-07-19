package com.expensetracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton responsible for creating and providing a single {@link Connection}
 * to the underlying PostgreSQL database. Connection parameters are picked from
 * environment variables so they can be changed without recompilation.
 * <p>
 * Required environment variables:
 * <ul>
 * <li>POSTGRES_DB</li>
 * <li>POSTGRES_USER</li>
 * <li>POSTGRES_PASSWORD</li>
 * <li>HOST</li>
 * <li>PORT</li>
 * </ul>
 */
public final class DBConnection {

    private static volatile Connection instance;

    private DBConnection() {
        // prevent instantiation
    }

    /**
     * Lazily creates (or returns cached) JDBC {@link Connection} instance.
     *
     * @return a live {@link Connection}
     * @throws SQLException if the connection cannot be established.
     */
    public static Connection getInstance() throws SQLException {
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
        String db = System.getenv().getOrDefault("POSTGRES_DB", "expense_tracker");
        String user = System.getenv().getOrDefault("POSTGRES_USER", "postgres");
        String password = System.getenv().getOrDefault("POSTGRES_PASSWORD", "password");
        String host = System.getenv().getOrDefault("HOST", "localhost");
        String port = System.getenv().getOrDefault("PORT", "5432");

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, db);
        return DriverManager.getConnection(url, user, password);
    }
}