package org.daming.hoteler.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = Base64PasswordServiceTest.TestConfig.class)
class Base64PasswordServiceTest {

    @Configuration
    static class TestConfig {

        @Bean
        public Base64PasswordService md5PasswordService() {
            return new Base64PasswordService();
        }
    }

    @Autowired
    private Base64PasswordService base64PasswordService;

    @BeforeEach
    public void beforeEach() {
        assertNotNull(base64PasswordService);
    }

    @Test
    @DisplayName("Base64PasswordService加密密码单元测试")
    public void testEncodePassword() {
        var password = "123456";
        var encodePassword = base64PasswordService.encodePassword(password);
        assertEquals("MTIzNDU2", encodePassword);
    }

    @Test
    @DisplayName("Base64PasswordService解密密码单元测试")
    public void testDecodePassword() {
        var decodeContent = "MTIzNDU2";
        var content = base64PasswordService.decodePassword(decodeContent);
        assertEquals("123456", content);
    }

}