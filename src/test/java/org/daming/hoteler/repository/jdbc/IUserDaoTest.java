package org.daming.hoteler.repository.jdbc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@EnabledIfEnvironmentVariable(named = "IS-CI", matches = "true")
@Disabled
class IUserDaoTest {

    @Autowired
    private IUserDao userDao;

    @Test
    void getUserByUsername() {
        var optional = userDao.getUserByUsername("damingerdai");
        optional.ifPresent(user -> assertEquals(user.getUsername(), "damingerdai"));

    }
}