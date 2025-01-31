package org.daming.hoteler.security.provider;


import org.daming.hoteler.pojo.User;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.utils.CommonUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

@Component
public class HotelerAuthenticationProvider extends DaoAuthenticationProvider implements AuthenticationProvider, ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        var user =  (User)userDetails;
        var passwordType = CommonUtils.isNotEmpty(user.getPasswordType()) ? user.getPasswordType() : "noop";
        var passwordService = this.getPasswordService(passwordType);
        var presentedPassword = authentication.getCredentials().toString();
        if (!user.getPassword().equals(passwordService.encodePassword(presentedPassword))) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doAfterPropertiesSet() {
        super.doAfterPropertiesSet();
        Assert.notNull(this.applicationContext, "A applicationContext must be set");
    }

    protected IPasswordService getPasswordService(String passwordType) {
        return Objects.requireNonNull(this.applicationContext).getBean(passwordType + "PasswordService", IPasswordService.class);
    }

    public HotelerAuthenticationProvider(UserDetailsService userDetailsService) {
        super();
        this.setUserDetailsService(userDetailsService);
    }
}
