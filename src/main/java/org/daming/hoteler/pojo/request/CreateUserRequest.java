package org.daming.hoteler.pojo.request;

import java.util.List;

public class CreateUserRequest {

    private String username;

    private String password;

    private String passwordType;

    private List<String> roles;

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

    public String getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public CreateUserRequest() {
        super();
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordType='" + passwordType + '\'' +
                ", roles=" + roles +
                '}';
    }
}
