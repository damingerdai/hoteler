package org.daming.hoteler.task;

import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IUserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gming001
 * @version 2024-07-02 17:06
 */
@Component
public class UnlockUserTask {

    private static final HotelerLogger logger = LoggerManager.getLogger(UnlockUserTask.class.getName());

    private final IUserService userService;

    // 改为每 5 分钟执行一次，避免频率过高
    @Scheduled(cron = "0 */5 * * * ?")
    public void unlockUsers() {
        logger.info("begin unlock users task");
        try {
            List<User> lockedUsers = this.userService.getUnlockUsers();
            if (lockedUsers == null || lockedUsers.isEmpty()) {
                logger.debug("no locked users found to unlock");
                return;
            }

            logger.debug("find {} unlock users", lockedUsers.size());
            for (User user : lockedUsers) {
                try {
                    logger.info("unlock user: {}", user.getId());
                    this.userService.resetFailedAttempts(user);
                    logger.info("complete unlock user: {}", user.getId());
                } catch (Exception e) {
                    // 单个用户处理失败捕获异常，确保不会中断整个任务批次
                    logger.error("failed to unlock user: " + user.getId(), e);
                }
            }
        } catch (Exception e) {
            logger.error("unlock users task execution failed", e);
        }
        logger.info("complete unlock user task");
    }

    public UnlockUserTask(IUserService userService) {
        super();
        this.userService = userService;
    }
}
