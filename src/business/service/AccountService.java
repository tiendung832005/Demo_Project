package business.service;

import business.dao.AccountDAO;
import business.model.Account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountService {
    private AccountDAO accountDAO;
    private Account currentAccount;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public boolean login(String username, String password) {
        Account account = accountDAO.findByUsername(username);

        if (account != null && account.getStatus() == Account.AccountStatus.ACTIVE) {
            String hashedPassword = hashPassword(password);
            if (account.getPassword().equals(hashedPassword)) {
                this.currentAccount = account;
                return true;
            }
        }

        return false;
    }

    public void logout() {
        this.currentAccount = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public boolean isLoggedIn() {
        return currentAccount != null;
    }

    public boolean isAdmin() {
        return isLoggedIn() && currentAccount.getRole() == Account.AccountRole.ADMIN;
    }

    public boolean isHR() {
        return isLoggedIn() && currentAccount.getRole() == Account.AccountRole.HR;
    }

    public boolean register(String username, String password, Account.AccountRole role) {
        if (accountDAO.findByUsername(username) != null) {
            return false;
        }

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(hashPassword(password));
        account.setStatus(Account.AccountStatus.ACTIVE);
        account.setRole(role);

        return accountDAO.create(account);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

