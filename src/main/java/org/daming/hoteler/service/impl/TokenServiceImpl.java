package org.daming.hoteler.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.UserToken;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ITokenService;
import org.daming.hoteler.service.IUserService;
import org.daming.hoteler.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class TokenServiceImpl implements ITokenService {

    private IUserService userService;

    private IErrorService errorService;

    private ISecretPropService secretPropService;

    @Override
    public UserToken createToken(String username, String password) {
        var user = userService.getUserByUsername(username);
        if (Objects.isNull(user) || !user.getPassword().equals(password)) {
            throw this.errorService.createHotelerException(600005);
        }
        return doCreateToken(user);
    }

    private UserToken doCreateToken(org.daming.hoteler.pojo.User user) {
        var id = String.valueOf(user.getId());
        var subject = user.getId() + "@" + user.getUsername();
        var ttlMillis = Duration.ofHours(1L).toMillis();
        var key = JwtUtil.generalKey(this.secretPropService.getKey());
        var claims = new HashMap<String, Object>();

        var accessToken = JwtUtil.createJWT(id, subject, ttlMillis, key, claims);
        var refreshToken = JwtUtil.createJWT(id, subject, ttlMillis + Duration.ofHours(23L).toMillis(), key, claims);
        var exp = Instant.now().toEpochMilli() + ttlMillis;
        return new UserToken().setAccessToken(accessToken).setRefreshToken(refreshToken).setExp(exp);
    }

    @Override
    public UserToken refreshToken(String refreshToken) {
        try {
            var key = JwtUtil.generalKey(this.secretPropService.getKey());
            var claim = JwtUtil.parseJwt(refreshToken, key);
            var sub = claim.getSubject();
            var subs = sub.split("@");
            var userId = subs[0];
            var username = subs[1];
            var user = Optional.ofNullable(userService.get(Long.parseLong(userId)))
                    .orElseThrow(() -> this.errorService.createHotelerException(600005));
            return doCreateToken(user);
        } catch (ExpiredJwtException ex) {
            throw ExceptionBuilder.buildException(600010, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw ExceptionBuilder.buildException(600001, ex.getMessage(), ex);
        }
    }

    @Override
    public User verifyToken(String token) throws HotelerException {
        try {
            var key = JwtUtil.generalKey(this.secretPropService.getKey());
            var claim = JwtUtil.parseJwt(token, key);
            var sub = claim.getSubject();
            var subs = sub.split("@");
            var userId = subs[0];
            var username = subs[1];
            var user = userService.get(Long.parseLong(userId));
            if (Objects.isNull(user)) {
                throw new RuntimeException("no user");
            }
            return user;
        } catch (ExpiredJwtException ex) {
            throw ExceptionBuilder.buildException(600010, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw ExceptionBuilder.buildException(600001, ex.getMessage(), ex);
        }

    }

    public TokenServiceImpl(IUserService userService, IErrorService errorService, ISecretPropService secretPropService) {
        super();
        this.userService = userService;
        this.errorService = errorService;
        this.secretPropService = secretPropService;
    }
}
