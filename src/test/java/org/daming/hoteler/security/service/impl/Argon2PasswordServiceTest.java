package org.daming.hoteler.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Argon2PasswordServiceTest {

    @Autowired
    private Argon2PasswordService argon2PasswordService;

    @BeforeEach
    public void beforeEach() {
        assertNotNull(argon2PasswordService);
    }

    @Test
    @DisplayName("Argon2PasswordService加密密码单元测试")
    public void testEncodePassword() throws InterruptedException {
        var password = "123456";
        var encodePassword = argon2PasswordService.encodePassword(password);
        Thread.sleep(1000L);
        var encodePassword2 = argon2PasswordService.encodePassword(password);
        System.out.println("Argon2PasswordService加密密码单元测试");
        System.out.println(encodePassword);
        assertEquals(encodePassword2, encodePassword);
        // Argon2PasswordEncoder
    }

}