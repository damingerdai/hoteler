package org.daming.hoteler.service.impl;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.expression.FieldExpressionFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.pojo.enums.HotelerEvent;
import org.daming.hoteler.service.IEventService;
import org.daming.hoteler.service.IQuartzService;
import org.daming.hoteler.task.job.CheckInTimeEventJob;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.concurrent.Executors;

@Service("DisruptorEventService")
public class DisruptorEventServiceImpl implements IEventService, InitializingBean, DisposableBean {

    private Disruptor<HotelerMessage> disruptor;

    private JsonMapper jsonMapper;
    private IQuartzService quartzService;

    @Override
    public void publishEvent(HotelerMessage message) throws HotelerException {
        RingBuffer<HotelerMessage> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            HotelerMessage ms = ringBuffer.get(sequence);
            ms.setEvent(message.getEvent());
            ms.setContent(message.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    @Override
    public void receiveEvent(HotelerMessage message) throws HotelerException {
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

    private void processCheckInTimeEvent(HotelerEvent type, Order record) throws SchedulerException {
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


    @Override
    public void afterPropertiesSet() throws Exception {
        this.disruptor =new Disruptor<>(
                HotelerMessage::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );
        this.disruptor.start();
    }

    @Override
    public void destroy() throws Exception {
        this.disruptor.shutdown();
    }

    public DisruptorEventServiceImpl(IQuartzService quartzService, JsonMapper jsonMapper) {
        this.quartzService = quartzService;
        this.jsonMapper = jsonMapper;
    }
}
