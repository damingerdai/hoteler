package org.daming.hoteler.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.CustomerCheckinRecord;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.pojo.UserToken;
import org.daming.hoteler.pojo.enums.HotelerEvent;
import org.daming.hoteler.service.IPingService;
import org.daming.hoteler.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.daming.hoteler.constants.CommonConstants.HOTELER_ALL_EVENTS;

/**
 * @author daming
 * @version 2023-07-03 20:59
 **/
@Component
public class RedisTask {

    private final HotelerLogger logger = LoggerManager.getJobLogger();

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper jsonMapper;

    private final IPingService pingService;

    // @Scheduled(cron = "*/10 * * * * ?")
    //@Scheduled(cron =  "*/10 * * * * ?")
    public void run(){
        try {
            var m = new HotelerMessage();
            m.setEvent(HotelerEvent.CHECK_IN_TIME);
            var r = new CustomerCheckinRecord();
           // r.setUserId(1L);
            r.setRoomId(1L);
            r.setBeginDate(LocalDateTime.now());
            r.setEndDate(LocalDateTime.now().plusDays(1L));
            var c = this.jsonMapper.writeValueAsString(r);
            m.setContent(c);
            var bs = SerializationUtils.serialize(m);
            System.out.println(bs);
            this.redisTemplate.convertAndSend(HOTELER_ALL_EVENTS, m);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Scheduled(fixedRate = 5000)
    public void runPingTask() {
        try {
            this.logger.info("run ping redis and db task");
            this.pingService.ping();
            this.logger.info("complete ping redis and db task");
        } catch (Exception ex) {
            this.logger.error("fail to complete ping redis and db task: {}", ex.getMessage());
        }

    }

    public RedisTask(RedisTemplate<String, Object> redisTemplate, ObjectMapper jsonMapper, IPingService pingService) {
        super();
        this.redisTemplate = redisTemplate;
        this.jsonMapper = jsonMapper;
        this.pingService = pingService;
    }
}
