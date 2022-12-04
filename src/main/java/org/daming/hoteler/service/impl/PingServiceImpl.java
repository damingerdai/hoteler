package org.daming.hoteler.service.impl;

import org.daming.hoteler.repository.mapper.PingMapper;
import org.daming.hoteler.service.IPingService;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author gming001
 * @version 2022-12-04 08:32
 */
@Service
public class PingServiceImpl implements IPingService {

    private final RedisTemplate redisTemplate;

    private final PingMapper pingMapper;

    @Override
    public String ping() {
        this.redisTemplate.execute(RedisConnectionCommands::ping);
        return this.pingMapper.ping();
    }

    public PingServiceImpl(RedisTemplate redisTemplate, PingMapper pingMapper) {
        this.redisTemplate = redisTemplate;
        this.pingMapper = pingMapper;
    }
}
