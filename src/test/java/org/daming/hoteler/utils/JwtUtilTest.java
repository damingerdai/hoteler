package org.daming.hoteler.utils;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void createJWT() {
        var k = "daming";
        var key = JwtUtil.generalKey(k);
        var id = "daming";
        var subject = "damingerdai";
        var ttlMillis = 60 * 1000L;
        var map = new HashMap<String, Object>(4);
        map.put("username", "daming");
        var token = JwtUtil.createJWT(id, subject, ttlMillis, key, map);
        System.out.println(token);
        assertNotNull(token);
    }

    @Test
    void parseJwt() {
        var k = "daming";
        var key = JwtUtil.generalKey(k);
        var id = "daming";
        var subject = "damingerdai";
        var ttlMillis = 60 * 1000L;
        var map = new HashMap<String, Object>(4);
        map.put("username", "daming");
        var token = JwtUtil.createJWT(id, subject, ttlMillis, key, map);
        var claims = JwtUtil.parseJwt(token, key);
        assertEquals(id, claims.getId());
        assertEquals(subject, claims.getSubject());
        assertEquals("daming", claims.get("username"));
        try {
            TimeUnit.SECONDS.sleep(65L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertThrows(ExpiredJwtException.class, () -> {
            JwtUtil.parseJwt(token, key);
        });
    }

    @Test
    void generalKey() {
        var k = "damingerdai";
        var key = JwtUtil.generalKey(k);
        System.out.println(key);
        assertNotNull(key);
    }
}