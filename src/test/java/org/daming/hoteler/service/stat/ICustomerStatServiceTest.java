package org.daming.hoteler.service.stat;

import org.daming.hoteler.repository.mapper.OrderMapper;
import org.daming.hoteler.service.stat.impl.CustomerStatServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = ICustomerStatServiceTest.TestConfig.class)  // 指定测试配置类
//@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class ICustomerStatServiceTest {

    @Configuration
    static class TestConfig {
        @Bean
        public ICustomerStatService yourService(OrderMapper orderMapper) {
            return new CustomerStatServiceImpl(orderMapper);  // 传入 AService 作为依赖
        }

        @MockitoBean  // 模拟 AService
        private OrderMapper orderMapper;  // 使用 @MockBean 自动模拟 AService 的行为
    }

    @Autowired
    private ICustomerStatService customerStatService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void countPastWeekCustomerCountStatTest() {
        when(orderMapper.getUserRoomCounts(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(1);
        this.customerStatService.countPastWeekCustomerCountStat();
        verify(orderMapper, times(7)).getUserRoomCounts(any(LocalDateTime.class), any(LocalDateTime.class));
    }

}