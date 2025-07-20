package com.expensetracker.dao.jdbc;

import java.math.BigInteger;
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
        String sql = "INSERT INTO accounts(user_id, name, balance, threshold_balance) VALUES (?,?,?,?)";
        try (Connection conn = DBConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (account.getThresholdBalance() == null) {
                System.out.println("Threshold balance is null, setting to 0");
            }
            System.out.println(account.getThresholdBalance());

            ps.setLong(1, account.getUserId()); // store positive
            ps.setString(2, account.getName());
            ps.setLong(3, Long.parseLong(account.getBalance().toString()));
            if (account.getThresholdBalance() != null) {
                ps.setLong(4, Long.parseLong(account.getThresholdBalance().toString()));
            } else {
                ps.setNull(4, java.sql.Types.BIGINT); // Use appropriate SQL type for null
            }
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
        String sql = "SELECT id, user_id, name, balance, threshold_balance FROM accounts WHERE user_id = ? ORDER BY name";
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
    public Optional<Account> findById(long id) {
        String sql = "SELECT id, amount, date, description, category, type FROM accounts WHERE id = ?";
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
        BigInteger balance = BigInteger.valueOf(rs.getLong("balance"));
        BigInteger thresholdBalance = BigInteger.valueOf(rs.getLong("threshold_balance"));
        return new Account(id, userId, name, balance, thresholdBalance);
    }
}
