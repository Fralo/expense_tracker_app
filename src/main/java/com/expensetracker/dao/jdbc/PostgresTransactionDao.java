package com.expensetracker.dao.jdbc;

import com.expensetracker.dao.TransactionDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresTransactionDao implements TransactionDao {

    public PostgresTransactionDao() {
        try {
            ensureTable();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create transactions table", e);
        }
    }

    private void ensureTable() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id UUID PRIMARY KEY, " +
                "amount NUMERIC NOT NULL, " +
                "date DATE NOT NULL, " +
                "description TEXT, " +
                "category TEXT, " +
                "type TEXT NOT NULL" +
                ")";
        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions(id, amount, date, description, category, type) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, transaction.getId());
            if (transaction instanceof Expense) {
                ps.setBigDecimal(2, transaction.getAmount().abs()); // store positive
            } else {
                ps.setBigDecimal(2, transaction.getAmount());
            }
            ps.setDate(3, Date.valueOf(transaction.getDate()));
            ps.setString(4, transaction.getDescription());
            ps.setString(5, transaction.getCategory() != null ? transaction.getCategory().getName() : null);
            ps.setString(6, transaction instanceof Expense ? "EXPENSE" : "INCOME");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save transaction", e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT id, amount, date, description, category, type FROM transactions ORDER BY date DESC";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to list transactions", e);
        }
        return list;
    }

    @Override
    public Transaction findById(UUID id) {
        String sql = "SELECT id, amount, date, description, category, type FROM transactions WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find transaction", e);
        }
        return null;
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        BigDecimal amount = rs.getBigDecimal("amount");
        LocalDate date = rs.getDate("date").toLocalDate();
        String desc = rs.getString("description");
        String catName = rs.getString("category");
        Category category = catName != null ? new Category(catName) : null;
        String type = rs.getString("type");
        if ("EXPENSE".equals(type)) {
            return new Expense(amount, date, desc, category); // Expense ctor will negate
        }
        return new Income(amount, date, desc, category);
    }
} 