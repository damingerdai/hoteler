package org.daming.hoteler.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

public class User implements Serializable {
    private static final long serialVersionUID = 120505218967153077L;

    // 该注解是必须的， long类型前端会有精度损失
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String passwordType;

    private int failedLoginAttempts;

    private boolean accountNonLocked;

    private LocalDateTime lockTime;

    private List<Role> roles;

    private List<Permission> permissions;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordType() {
        return passwordType;
    }

    public User setPasswordType(String passwordType) {
        this.passwordType = passwordType;
        return this;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public User setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public User setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public User setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public User setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public User() {
        super();
    }

    public User(long id, String username, String password, List<Role> roles) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(long id, String username, String password, String passwordType, int failedLoginAttempts, boolean accountNonLocked, LocalDateTime lockTime, List<Role> roles, List<Permission> permissions) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordType = passwordType;
        this.failedLoginAttempts = failedLoginAttempts;
        this.accountNonLocked = accountNonLocked;
        this.lockTime = lockTime;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User(long id, String username, String password, String passwordType, List<Role> roles, List<Permission> permissions) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordType = passwordType;
        this.roles = roles;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("passwordType='" + passwordType + "'")
                .add("failedLoginAttempts=" + failedLoginAttempts)
                .add("accountNonLocked=" + accountNonLocked)
                .add("lockTime=" + lockTime)
                .add("roles=" + roles)
                .add("permissions=" + permissions)
                .toString();
    }
}
