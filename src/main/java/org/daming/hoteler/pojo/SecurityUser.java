package org.daming.hoteler.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author gming001
 * @version 2022-07-28 14:45
 */
public class SecurityUser extends User implements UserDetails {

    private Set<GrantedAuthority> authorities;

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * 账号是否失效
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return Objects.isNull(this.getLockTime());
    }

    /**
     * 密码是否失效
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return Objects.isNull(this.getLockTime());
    }

    public SecurityUser(String username, String password, Set<GrantedAuthority> authorities) {
        super();
        this.setUsername(username);
        this.setPassword(password);
        this.setAuthorities(authorities);
    }

    public SecurityUser() {
        super();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authorities", authorities)
                .toString();
    }
}
