package org.daming.hoteler.repository.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(true)
class UserMapperTest {

    @Autowired(required = false)
    private UserMapper userMapper;



    @Test
    void list() {
        assertNotNull(userMapper);
        userMapper.list().forEach(System.out::println);
    }

    @Test
    void get() {
        assertNotNull(userMapper);
        var user = userMapper.get(1L);
        assertNotNull(user);
    }
}