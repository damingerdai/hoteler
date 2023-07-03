package org.daming.hoteler.listener;

import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author daming
 * @version 2023-07-03 20:55
 **/
@Component
public class RedisMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(RedisMessageListener.class);

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("channel:" + new String(pattern));

        logger.info("message obj:" +new String(message.getBody()));

        logger.info("message channel:" +new String(message.getChannel()));

        var body = this.redisTemplate.getValueSerializer().deserialize(message.getBody());
        if (body instanceof String) {
            logger.info((String) body);
        } else if (body instanceof UserToken) {
            var userToken = (UserToken) body;
            logger.info(userToken.toString());
        } else {
            logger.info(Objects.requireNonNull(body).toString());
        }

    }

    public RedisMessageListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
