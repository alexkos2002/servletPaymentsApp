package org.kosiuk.webApp.servletPaymentsApp.controller.dto;

public class UserBasicDto {

    private int id;
    private String username;
    private String email;
    private String password;
    private boolean hasOrderOnCheck;
    private boolean hasBlockedAccount;

    public UserBasicDto(int id, String username, String email, String password,
                        boolean hasOrderOnCheck, boolean hasBlockedAccount) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.hasOrderOnCheck = hasOrderOnCheck;
        this.hasBlockedAccount = hasBlockedAccount;
    }

    public UserBasicDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHasOrderOnCheck() {
        return hasOrderOnCheck;
    }

    public void setHasOrderOnCheck(boolean hasOrderOnCheck) {
        this.hasOrderOnCheck = hasOrderOnCheck;
    }

    public boolean isHasBlockedAccount() {
        return hasBlockedAccount;
    }

    public void setHasBlockedAccount(boolean hasBlockedAccount) {
        this.hasBlockedAccount = hasBlockedAccount;
    }
}
