package org.daming.hoteler.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DESPasswordServiceTest {
    @Autowired
    private DESPasswordService desPasswordService;

    @BeforeEach
    public void beforeEach() {
        assertNotNull(desPasswordService);
    }

    @Test
    @DisplayName("DESPasswordService加密密码单元测试")
    public void testEncodePassword() {
        var password = "123456";
        var encodePassword = desPasswordService.encodePassword(password);
        assertEquals("fCKPkk5uWIQ=", encodePassword);
    }


    @Test
    @DisplayName("DESPasswordService解密密码单元测试")
    public void testDecodePassword() {
        var decodeContent = "fCKPkk5uWIQ=";
        var content = desPasswordService.decodePassword(decodeContent);
        assertEquals("123456", content);
    }
}