package business.model;


public class Account {
    private int id;
    private String username;
    private String password;
    private AccountStatus status;
    private AccountRole role;

    public enum AccountRole{
        ADMIN, HR
    }

    public enum AccountStatus{
        ACTIVE, INACTIVE
    }

    public Account(){}

    public Account(int id, String username, String password, AccountStatus status, AccountRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}






