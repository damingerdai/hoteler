package org.daming.hoteler.security.service;

import org.daming.hoteler.pojo.SecurityUser;
import org.daming.hoteler.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author gming001
 * @version 2022-07-28 14:49
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private IUserService userService;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userService.getUserByUsername(username);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("username or password is incorrect");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(user.getAuthorities());
        var securityUser = new SecurityUser(
                username,
                this.passwordEncoder.encode(user.getPassword()),
                grantedAuthorities
        );
        securityUser.setId(user.getId());
        securityUser.setPasswordType(user.getPasswordType());

        return securityUser;
    }

    public SecurityUserService(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
}
