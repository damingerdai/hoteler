package org.daming.hoteler.utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SnowflakeIdWorkerTest {

    @Test
    void nextId() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        Set<Long> set = new HashSet<>(1000);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            set.add(id);
        }

        assertEquals(1000,set.size());
        set.add(1L);
        set.add(1L);
        assertEquals(1001,set.size());
    }
}