package org.daming.hoteler.service.impl;

import org.daming.hoteler.pojo.UserToken;
import org.daming.hoteler.service.ITokenService;
import org.daming.hoteler.service.IUserService;
import org.daming.hoteler.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Objects;

@Service
public class TokenServiceImpl implements ITokenService {

    private IUserService userService;

    @Value("${secret.key}")
    private String secretKey;

    @Override
    public UserToken createToken(String username, String password) {
        var user = userService.getUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new RuntimeException("no user");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("user is invalid");
        }
        var id = String.valueOf(user.getId());
        var subject = user.getId() + "@" + user.getUsername();
        var ttlMillis = Duration.ofHours(1L).toMillis();
        var key = JwtUtil.generalKey(secretKey);
        var claims = new HashMap<String, Object>();

        var accessToken = JwtUtil.createJWT(id, subject, ttlMillis, key, claims);
        var refreshToken = JwtUtil.createJWT(id, subject, ttlMillis + Duration.ofHours(23L).toMillis(), key, claims);
        var exp = Instant.now().getLong(ChronoField.MILLI_OF_SECOND) + ttlMillis;
        return new UserToken().setAccessToken(accessToken).setRefreshToken(refreshToken).setExp(exp);
    }

    @Override
    public UserToken refreshToken(String refreshToken) {
        return null;
    }

    public TokenServiceImpl(IUserService userService) {
        super();
        this.userService = userService;
    }
}
