package org.daming.hoteler.task.job;

import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.service.IOrderService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author daming
 * @version 2023-08-14 16:21
 **/
@DisallowConcurrentExecution //不并发执行
@Component
public class CheckInTimeEventJob extends QuartzJobBean implements Job {

    private IOrderService customerCheckinRecordService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        try {
            var record = (Order)jobDataMap.get("record");
            this.customerCheckinRecordService.create(record);
            LoggerManager.getJobLogger().info("run CheckInTimeEventJob: " + record);
        } catch (Exception ex) {
            LoggerManager.getJobLogger().error("fail to run CheckInTimeEventJob: " + jobDataMap.get("record"), ex);
        }
       
    }

    public CheckInTimeEventJob(IOrderService customerCheckinRecordService) {
        super();
        this.customerCheckinRecordService = customerCheckinRecordService;
    }
}
