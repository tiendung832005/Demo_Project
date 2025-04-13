package business.dao;

import business.config.DatabaseConfig;
import business.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public Account findByUsername(String username){
        String query = "SELECT * FROM Account WHERE username = ?";

        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setStatus(Account.AccountStatus.valueOf(rs.getString("status")));
                account.setRole(Account.AccountRole.valueOf(rs.getString("role")));
                return account;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean create(Account account) {
        String query = "INSERT INTO Account (username, password, status, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getStatus().toString());
            stmt.setString(4, account.getRole().toString());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setStatus(Account.AccountStatus.valueOf(rs.getString("status")));
                account.setRole(Account.AccountRole.valueOf(rs.getString("role")));
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
