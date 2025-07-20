package com.expensetracker.dao.jdbc;

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

import com.expensetracker.dao.TransactionDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;

public class JdbcTransactionDao implements TransactionDao {

    public JdbcTransactionDao() {
        try {
            ensureTable();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create transactions table", e);
        }
    }

    private void ensureTable() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY, " +
                "amount REAL NOT NULL, " +
                "date TEXT NOT NULL, " +
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
        String sql = "INSERT INTO transactions(amount, date, description, category, type) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (transaction instanceof Expense) {
                ps.setBigDecimal(1, transaction.getAmount().abs()); // store positive
            } else {
                ps.setBigDecimal(1, transaction.getAmount());
            }
            ps.setDate(2, Date.valueOf(transaction.getDate()));
            ps.setString(3, transaction.getDescription());
            ps.setString(4, transaction.getCategory() != null ? transaction.getCategory().getName() : null);
            ps.setString(5, transaction instanceof Expense ? "EXPENSE" : "INCOME");
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getLong(1));
                }
            }
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
    public Transaction findById(long id) {
        String sql = "SELECT id, amount, date, description, category, type FROM transactions WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
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

    private Date parseDate(String rowDate) {
        return new Date(Long.parseLong(rowDate));
    }
  
    private Transaction mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        BigDecimal amount = rs.getBigDecimal("amount");

        Date date = parseDate(rs.getString("date"));
        LocalDate localDate = date.toLocalDate();

        String desc = rs.getString("description");
        String catName = rs.getString("category");
        Category category = catName != null ? new Category(catName) : null;
        String type = rs.getString("type");
        if ("EXPENSE".equals(type)) {
            return new Expense(id, amount, localDate, desc, category);
        }
        return new Income(id, amount, localDate, desc, category);
    }
} 