package org.daming.hoteler.repository.jdbc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Transactional
@Disabled
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