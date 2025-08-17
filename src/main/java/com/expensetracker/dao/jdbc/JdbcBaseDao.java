package com.expensetracker.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.expensetracker.db.DBConnection;

public abstract class JdbcBaseDao {

    public JdbcBaseDao() {
        // Ensure table exists on construction
        try {
            ensureTable();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to ensure base table", e);
        }
    }

    abstract void ensureTable() throws SQLException;

    protected void ensureTableExists(String ddl) throws SQLException {
        try (Connection conn = DBConnection.getInstance();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }
}
