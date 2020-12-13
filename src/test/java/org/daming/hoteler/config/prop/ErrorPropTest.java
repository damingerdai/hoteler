package org.daming.hoteler.config.prop;

import org.daming.hoteler.config.DisableGraphQLWebsocketAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisableGraphQLWebsocketAutoConfiguration
class ErrorPropTest {

    @Autowired
    private ErrorProp errorProp;

    @Test
    void getErrors() {
        var errors = errorProp.getErrors();
        assertNotNull(errors);
        assertNotEquals(0, errors.size());
        errors.forEach(System.out::println);
    }

    @Test
    void setErrors() {
    }
}