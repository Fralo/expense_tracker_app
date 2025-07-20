package com.expensetracker.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                "id INTEGER PRIMARY KEY, " +
                "username TEXT UNIQUE NOT NULL " +
                ")";
        try (Connection conn = DBConnection.getInstance();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(username) VALUES (?)";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, username FROM users";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding users", e);
        }
        return list;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String uname = rs.getString("username");
        return new User(id, uname);
    }
}