package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.service.IErrorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = Md5PasswordServiceTest.TestConfig.class)
class Md5PasswordServiceTest {

    @Configuration
    static class TestConfig {

        @MockBean
        public IErrorService errorService;

        @Bean
        public Md5PasswordService md5PasswordService(IErrorService errorService) {
            return new Md5PasswordService(errorService);
        }
    }

    @Autowired
    private Md5PasswordService md5PasswordService;

    @Autowired
    private IErrorService errorService;

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

    @Test
    @DisplayName("Md5PasswordService解密密码单元测试")
    public void testDecodePassword() {
        var he = new HotelerException();
        he.setMessage("md5 algorithm encrypt is not supported");
        when(errorService.createHotelerSystemException(any(String.class), any(Throwable.class))).thenReturn(he);
        var thrown  = assertThrows(HotelerException.class, () -> md5PasswordService.decodePassword("e10adc3949ba59abbe56e057f20f883e"));
        assertEquals(thrown.getMessage(), he.getMessage());
    }

}