package org.daming.hoteler.task.job;

import org.daming.hoteler.base.logger.LoggerManager;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author gming001
 * @version 2023-08-31 21:03
 */
@DisallowConcurrentExecution //不并发执行
@Component
public class PingJob extends QuartzJobBean implements Job {

    @Value("${server.port}")
    private int port;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LoggerManager.getJobLogger().info("ping job");
        var restTemplate = new RestTemplate();
        var fooResourceUrl = String.format("http://localhost:%d/ping", port);
        var response = restTemplate.getForEntity(fooResourceUrl, String.class);
        LoggerManager.getJobLogger().info("ping job -> status code {}, body: {]", response.getStatusCode(), response.getBody());
    }
}
