package org.daming.hoteler.task;

import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.UserToken;
import org.daming.hoteler.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author daming
 * @version 2023-07-03 20:59
 **/
@Component
public class RedisTask {

    private final HotelerLogger logger = LoggerManager.getJobLogger();

    private final String HOTELER_ALL_EVENTS = "HOTLER-ALL-EVENTS";

    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "*/10 * * * * ?")
    public void run() {
        var k = "daming";
        var key = JwtUtil.generalKey(k);
        var id = "daming";
        var subject = "damingerdai";
        var ttlMillis = 60 * 1000L;
        var map = new HashMap<String, Object>(4);
        map.put("username", "daming");
        var token = JwtUtil.createJWT(id, subject, ttlMillis, key, map);
        var userToken = new UserToken().setAccessToken(token);
        this.redisTemplate.convertAndSend(HOTELER_ALL_EVENTS, "damingerdai");
        this.redisTemplate.convertAndSend(HOTELER_ALL_EVENTS, token);
        this.redisTemplate.convertAndSend(HOTELER_ALL_EVENTS, userToken);
    }

    public RedisTask(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
