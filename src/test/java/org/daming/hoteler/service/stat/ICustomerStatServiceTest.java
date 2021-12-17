package org.daming.hoteler.service.stat;

import org.daming.hoteler.repository.mapper.CustomerCheckinRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

// @SpringBootTest
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class ICustomerStatServiceTest {

    @Autowired
    private ICustomerStatService customerStatService;

    @MockBean
    private CustomerCheckinRecordMapper customerCheckinRecordMapper;

    @Test
    public void countPastWeekCustomerCountStatTest() {
        when(customerCheckinRecordMapper.getUserRoomCounts(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(1);
        this.customerStatService.countPastWeekCustomerCountStat();
        verify(customerCheckinRecordMapper, times(7)).getUserRoomCounts(any(LocalDateTime.class), any(LocalDateTime.class));
    }

}