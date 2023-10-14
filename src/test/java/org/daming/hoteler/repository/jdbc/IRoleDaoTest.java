package org.daming.hoteler.repository.jdbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@EnabledIfEnvironmentVariable(named = "IS-CI", matches = "true")
class IRoleDaoTest {

    @Autowired
    private IRoleDao roleDao;

    @Test
    void list() {
         var roles = roleDao.list();
         roles.forEach(System.out::println);
    }

    @Test
    void testList() {
        var ids = List.of(1L, 2L);
        var roles = roleDao.list(ids);
        roles.forEach(System.out::println);
    }

    @Test
    void get() {
        var id = 1L;
        var role = roleDao.get(id);
        System.out.println(role);
    }
}