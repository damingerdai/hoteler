package org.daming.hoteler.dao.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void list() {
        assertNotNull(userMapper);
        userMapper.list().forEach(System.out::println);
    }
}