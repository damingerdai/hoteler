package org.daming.hoteler.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

public class User implements Serializable {
    private static final long serialVersionUID = 120505218967153077L;

    // 该注解是必须的， long类型前端会有精度损失
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    private String username;

    private String password;

    private List<Role> roles;

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

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
