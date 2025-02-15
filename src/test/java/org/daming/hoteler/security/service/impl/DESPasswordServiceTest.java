package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.config.prop.SecretProp;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.config.service.impl.SecretPropServiceImpl;
import org.daming.hoteler.service.IErrorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(classes = DESPasswordServiceTest.TestConfig.class)
class DESPasswordServiceTest {

    @Configuration
    @ConfigurationProperties(prefix = "secret")
    static class TestConfig {

        @MockitoSpyBean
        public IErrorService errorService;

        @Bean
        public SecretProp secretProp() {
            var prop =  new SecretProp();
            prop.setSalt("dc513dcf1de6941978deb973fe98c1f6");
            prop.setPersonSalt("dc513dcf1de6941978deb973fe98c1f4");
            prop.setKey("damingerdai");

            return prop;
        }

        @Bean
        public ISecretPropService secretPropService(SecretProp secretProp) {
            return new SecretPropServiceImpl(secretProp);
        }

        @Bean
        public DESPasswordService md5PasswordService(IErrorService errorService, ISecretPropService secretPropService) {
            return new DESPasswordService(errorService, secretPropService);
        }
    }

    @Autowired
    private DESPasswordService desPasswordService;

    @MockitoBean
    private IErrorService errorService;

    @BeforeEach
    public void beforeEach() {
        System.out.println("desPasswordService" + desPasswordService);
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