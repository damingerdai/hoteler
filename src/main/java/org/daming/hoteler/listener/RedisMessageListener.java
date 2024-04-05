package org.daming.hoteler.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.service.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author daming
 * @version 2023-07-03 20:55
 **/
@Component
public class RedisMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(RedisMessageListener.class);

    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper jsonMapper;

    private IEventService eventService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            logger.info("channel:" + new String(pattern));
            logger.info("message obj:" +new String(message.getBody()));
            logger.info("message channel:" +new String(message.getChannel()));

            var body = this.redisTemplate.getValueSerializer().deserialize(message.getBody());
            if (body instanceof String) {
                var hotlerMessage = this.jsonMapper.readValue((String) body, HotelerMessage.class);
                this.eventService.receiveEvent(hotlerMessage);
            } else if (body instanceof HotelerMessage) {
                this.eventService.receiveEvent((HotelerMessage)body);
            } else {
                logger.warn("unsupported message: " + Arrays.toString(message.getBody()));
            }
        } catch (Exception ex) {
            logger.error("fail to parse message: " + Arrays.toString(message.getBody()), ex);
        }

    }

    public RedisMessageListener(RedisTemplate<String, Object> redisTemplate, ObjectMapper jsonMapper, IEventService eventService) {
        super();
        this.redisTemplate = redisTemplate;
        this.jsonMapper = jsonMapper;
        this.eventService = eventService;
    }
}
