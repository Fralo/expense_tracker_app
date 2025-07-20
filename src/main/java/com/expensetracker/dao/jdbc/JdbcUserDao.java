package com.expensetracker.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

import com.expensetracker.dao.UserDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.User;

public class JdbcUserDao implements UserDao {

    public JdbcUserDao() {
        // Ensure table exists on construction
        try {
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to ensure users table", e);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS users (" +
                "id TEXT PRIMARY KEY, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password_hash TEXT NOT NULL" +
                ")";
        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(id, username, password_hash) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getId().toString());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPasswordHash());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String uname = rs.getString("username");
                    String pwd = rs.getString("password_hash");
                    User user = new User(id, uname, pwd);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user", e);
        }
        return Optional.empty();
    }
} 