package com.expensetracker.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.expensetracker.dao.CategoryDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Category;

public class JdbcCategoryDao implements CategoryDao {

    public JdbcCategoryDao() {
        try {
            ensureTable();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create categories table", e);
        }
    }

    private void ensureTable() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS categories (" +
                "id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "parent_id TEXT NULL" +
                ")";
        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    @Override
    public void save(Category category) {
        String sql = "INSERT INTO categories(id, name, parent_id) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, UUID.randomUUID().toString()); // generate a random id for now
            ps.setString(2, category.getName());
            ps.setObject(3, null); // parent handling simplified
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save category", e);
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT name FROM categories";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch categories", e);
        }
        return list;
    }
} 