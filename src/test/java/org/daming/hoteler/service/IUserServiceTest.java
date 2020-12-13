package org.daming.hoteler.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void list() {
        assertNotNull(userService);
        userService.list().forEach(System.out::println);
    }
}