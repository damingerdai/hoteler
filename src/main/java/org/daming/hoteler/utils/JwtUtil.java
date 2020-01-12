package org.daming.hoteler.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class JwtUtil {

    public static String createJWT(String id, String subject, long ttlMillis, SecretKey secretKey, Map<String, Object> claims) {
        var signatureAlgorithm = SignatureAlgorithm.HS256;
        var nowMillis = System.currentTimeMillis();
        var now = new Date(nowMillis);
        var builder = Jwts.builder();
        if (Objects.nonNull(claims) && !claims.isEmpty()) {
            builder.setClaims(claims);
        }
        builder.setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >=0L) {
            var expMillis = nowMillis + ttlMillis;
            var exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseJwt(String jwt, SecretKey secretKey) {
        var claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        return claim;
    }

    public static SecretKey generalKey(String key) {
        var encodeKey = Base64.decodeBase64(key);
        var secretKey = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return secretKey;
    }
}
