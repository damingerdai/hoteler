package org.daming.hoteler.service.stat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ICustomerStatServiceTest {

    @Autowired
    private ICustomerStatService customerStatService;

    @Test
    public void countPastWeekCustomerCountStatTest() {
        this.customerStatService.countPastWeekCustomerCountStat();
    }

}