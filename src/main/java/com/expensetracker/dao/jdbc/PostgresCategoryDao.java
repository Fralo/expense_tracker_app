package com.expensetracker.dao.jdbc;

import com.expensetracker.dao.CategoryDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresCategoryDao implements CategoryDao {

    public PostgresCategoryDao() {
        try {
            ensureTable();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create categories table", e);
        }
    }

    private void ensureTable() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS categories (" +
                "id UUID PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "parent_id UUID NULL" +
                ")";
        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    @Override
    public void save(Category category) {
        String sql = "INSERT INTO categories(id, name, parent_id) VALUES (?,?,?) " +
                "ON CONFLICT(id) DO UPDATE SET name = EXCLUDED.name, parent_id = EXCLUDED.parent_id";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, UUID.randomUUID()); // generate a random id for now
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