package com.expensetracker;

import java.sql.Connection;
import java.sql.SQLException;

import com.expensetracker.db.DBConnection;
import com.expensetracker.flows.AppFlow;
import com.expensetracker.flows.Flow;

/**
 * Simple CLI entry point. Currently, it just verifies the DB connection and
 * then asks which high-level operation the user wants to perform. The actual
 * implementations will be provided incrementally.
 */
public class Main {

    public static void main(String[] args) {
        try {
            String dbFile = System.getProperty("db.file", "expense_tracker.db");
            DBConnection.initialize(dbFile);
            Connection connection = DBConnection.getInstance();
            System.out.println("✅ Successfully connected to the SQLite database!\n");
            runCli();
            connection.close();
        } catch (SQLException e) {
            System.err.println("❌ Could not connect to the database: " + e.getMessage());
        }
    }

    private static void runCli() {
        Flow appFlow = new AppFlow();
        appFlow.execute();
    }
}