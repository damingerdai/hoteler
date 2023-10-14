package org.daming.hoteler.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class JwtUtil {

    public static String createJWT(String id, String subject, long ttlMillis, SecretKey secretKey, Map<String, Object> claims) {
        var nowMillis = System.currentTimeMillis();
        var now = new Date(nowMillis);
        var builder = Jwts.builder();
        if (Objects.nonNull(claims) && !claims.isEmpty()) {
            builder.claims(claims);
        }
        builder.id(id).issuedAt(now).subject(subject).signWith(secretKey);
        if (ttlMillis >= 0L) {
            var expMillis = nowMillis + ttlMillis;
            var exp = new Date(expMillis);
            builder.expiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseJwt(String jwt, SecretKey secretKey) {
        var claim = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
        return claim;
    }

    /**
     * if meet ths issue 'Last encoded character (before the paddings if any) is a valid base 64 alphabet but not a possible value'.
     * please upgrade commons-codec-1.15
     * more detail: https://issues.apache.org/jira/browse/CODEC-263
     * @param key
     * @return
     */
    public static SecretKey generalKey(String key) {
        var signatureAlgorithm = SignatureAlgorithm.HS256;
        var encodeKey = Base64.encodeBase64(key.trim().getBytes(StandardCharsets.UTF_8));
        var bytes = new byte[Math.max(encodeKey.length, signatureAlgorithm.getMinKeyLength())];
        System.arraycopy(encodeKey, 0, bytes, 0, encodeKey.length);
        var secretKey = new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
        return secretKey;
    }
}
