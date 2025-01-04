package org.daming.hoteler.service.impl;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.expression.FieldExpressionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.pojo.enums.HotelerEvent;
import org.daming.hoteler.service.IEventService;
import org.daming.hoteler.service.IQuartzService;
import org.daming.hoteler.task.job.CheckInTimeEventJob;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author gming001
 * @version 2023-07-23 17:53
 */
@Primary
@Service
public class EventServiceImpl implements IEventService {

    private HotelerLogger logger = LoggerManager.getCommonLogger();

    private final String HOTELER_ALL_EVENTS = "HOTLER-ALL-EVENTS";

    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper jsonMapper;

    private IQuartzService quartzService;

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
            if (type == HotelerEvent.CHECK_IN_TIME) {
                var record = this.jsonMapper.readValue(content, Order.class);;
                this.processCheckInTimeEvent(type, record);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processCheckInTimeEvent(HotelerEvent type,  Order record) throws SchedulerException {
        var beginDate = record.getBeginDate();
        var triggerName = String.valueOf( record.getCustomerId() + record.getRoomId() + record.hashCode());
        var cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withSecond(FieldExpressionFactory.on(0))
                .withMinute(FieldExpressionFactory.on(0))
                .withHour(FieldExpressionFactory.on(0))
                .withDoM(FieldExpressionFactory.on(beginDate.getDayOfMonth()))
                .withMonth(FieldExpressionFactory.on(beginDate.getMonth().getValue()))
                .withDoW(FieldExpression.questionMark())
                .withYear(FieldExpressionFactory.on(beginDate.getYear()))
                .instance();
        var cronAsString = cron.asString();
        var map = new HashMap<>(1);
        map.put("record", record);
        var jobDataMap = new JobDataMap(map);
        this.quartzService.addJob(triggerName, type.name(), cronAsString, CheckInTimeEventJob.class, jobDataMap);
        LoggerManager.getJobLogger().info("add CheckInTimeEventJob: {} -{}", cronAsString, record);
    }

    public EventServiceImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper jsonMapper, IQuartzService quartzService) {
        super();
        this.redisTemplate = redisTemplate;
        this.jsonMapper = jsonMapper;
        this.quartzService = quartzService;
    }
}
