package org.kosiuk.webApp.servletPaymentsApp.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private boolean active;
    private boolean hasOrderOnCheck;
    private boolean hasBlockedAccount;
    private Set<Role> roles;

    public User(int id, String username, String email, String password, boolean active,
                boolean hasOrderOnCheck, boolean hasBlockedAccount) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.hasOrderOnCheck = hasOrderOnCheck;
        this.hasBlockedAccount = hasBlockedAccount;
    }

    public User(Integer id, String username, String email, String password, boolean active,
                boolean hasOrderOnCheck, boolean hasBlockedAccount, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.hasOrderOnCheck = hasOrderOnCheck;
        this.hasBlockedAccount = hasBlockedAccount;
        this.roles = roles;
    }

    public User() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String username;
        private String email;
        private String password;
        private boolean active;
        private boolean hasOrderOnCheck;
        private boolean hasBlockedAccount;
        private Set<Role> roles = new HashSet<>();;

        public Builder initRegistrationDetails(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
            return this;
        }

        public Builder initFlagsDefault() {
            this.hasBlockedAccount = false;
            this.hasOrderOnCheck = false;
            this.active = true;
            return this;
        }

        public Builder roles(Role ... roleArgs) {
            for (Role curRole : roleArgs) {
                roles.add(curRole);
            }
            return this;
        }

        public User build() {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setActive(active);
            user.setHasBlockedAccount(hasBlockedAccount);
            user.setHasOrderOnCheck(hasOrderOnCheck);
            user.setRoles(roles);
            return user;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User guest = (User) o;
        return active == guest.isActive() &&
                hasOrderOnCheck == guest.isHasOrderOnCheck()
                && hasBlockedAccount == guest.isHasBlockedAccount() &&
                id == guest.getId() &&
                username.equals(guest.getUsername()) &&
                email.equals(guest.getEmail()) &&
                password.equals(guest.getPassword()) &&
                roles.equals(guest.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, active, hasOrderOnCheck, hasBlockedAccount, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", hasOrderOnCheck=" + hasOrderOnCheck +
                ", hasBlockedAccount=" + hasBlockedAccount +
                ", roles=" + roles.toString() +
                '}';
    }

    public boolean hasRole(String testRole) {
        for (Role curRole : roles) {
            if (curRole.name().equals(testRole)) {
                return true;
            }
        }
        return false;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
