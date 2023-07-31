package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.service.IEventService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * @author gming001
 * @version 2023-07-23 17:53
 */
@Service
public class EventServiceImpl implements IEventService {

    private HotelerLogger logger = LoggerManager.getCommonLogger();

    private final String HOTELER_ALL_EVENTS = "HOTLER-ALL-EVENTS";

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publishEvent(HotelerMessage message) throws HotelerException {
        try {
            this.redisTemplate.convertAndSend(HOTELER_ALL_EVENTS, message);
            logger.info("published event: {} -> {}", message.getEvent(), message.getContent());
        } catch (Exception ex) {
            logger.error(() -> String.format("fail to publish event: %s -> %s, error: %s", message.getEvent(), message.getContent(), ex.getMessage()), ex);
        }
    }

    @Override
    public void receiveEvent(HotelerMessage message) throws HotelerException{
        try {
            var type = message.getEvent();
            var content = message.getContent();
            System.out.println(type + ":" + content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public EventServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }
}
