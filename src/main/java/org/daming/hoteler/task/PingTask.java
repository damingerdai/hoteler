package org.daming.hoteler.task;

import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.service.IPingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PingTask {

    private final HotelerLogger logger = LoggerManager.getJobLogger();

    private final IPingService pingService;

    @Scheduled(cron = "0 0 * * * ?")
    public void runPing() {
        try {
            this.logger.info("run ping redis and db task");
            this.pingService.ping();
            this.logger.info("complete ping redis and db task");
        } catch (Exception ex) {
            this.logger.error("fail to complete ping redis and db task: {}", ex.getMessage());
        }

    }

    public PingTask(IPingService pingService) {
        this.pingService = pingService;
    }
}
