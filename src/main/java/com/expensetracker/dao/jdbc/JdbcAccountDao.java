package com.expensetracker.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.expensetracker.dao.AccountDao;
import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Account;
import com.expensetracker.model.User;

public class JdbcAccountDao implements AccountDao {
    public JdbcAccountDao() {
        try {
            ensureTable();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create accounts table", e);
        }
    }

    private void ensureTable() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS accounts (" +
                "id INTEGER PRIMARY KEY, " +
                "user_id INTEGER, " +
                "name TEXT NOT NULL, " +
                "balance INTEGER NOT NULL, " +
                "threshold_balance INTEGER " +
                ")";
        try (Connection conn = DBConnection.getInstance();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts(user_id, name, balance) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, account.getUserId()); // store positive
            ps.setString(2, account.getName());
            ps.setLong(3, account.getBalance());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save account", e);
        }
    }

    @Override
    public List<Account> findAll(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        List<Account> list = new ArrayList<>();
        String sql = "SELECT id, user_id, name, balance FROM accounts WHERE user_id = ? ORDER BY name";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, user.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to list accounts", e);
        }
        return list;
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, account.getBalance());
            ps.setLong(2, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update account", e);
        }
    }

    @Override
    public Optional<Account> findById(long id) {
        String sql = "SELECT id, user_id, name, balance FROM accounts WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find account", e);
        }
        return Optional.empty();
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long userId = rs.getLong("user_id");
        String name = rs.getString("name");
        long balance = rs.getLong("balance");
        return new Account(id, userId, name, balance);
    }
}
