package model;

public class PasswordEntry {
    private int entryId;
    private int userId;
    private String accountName;
    private String username;
    private String password;

    public PasswordEntry() {}

    public PasswordEntry(int userId, String accountName, String username, String password) {
        this.userId = userId;
        this.accountName = accountName;
        this.username = username;
        this.password = password;
    }

    public int getEntryId() {
        return entryId;
    }
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
}
