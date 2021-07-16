package org.daming.hoteler.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Md5PasswordServiceTest {

    @Autowired
    private Md5PasswordService md5PasswordService;

    @BeforeEach
    public void beforeEach() {
        assertNotNull(md5PasswordService);
    }

    @Test
    @DisplayName("Md5PasswordService加密密码单元测试")
    public void testEncodePassword() {
        var password = "123456";
        var encodePassword = md5PasswordService.encodePassword(password);
        assertEquals("e10adc3949ba59abbe56e057f20f883e", encodePassword);
    }

}