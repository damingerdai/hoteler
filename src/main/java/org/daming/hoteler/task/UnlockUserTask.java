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

    private IUserService userService;

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void unlockUsers() {
        logger.info("begin unlock users task");
        List<User> lockedUsers = this.userService.getUnlockUsers();
        logger.debug("find {} unlock users", lockedUsers.size());
        for (User user: lockedUsers) {
            logger.info("unlock user: ", user.getId());
            this.userService.resetFailedAttempts(user);
            logger.info("complete unlock user: {}", user.getId());
        }
        logger.info("complete unlock user task ");
    }

    public UnlockUserTask(IUserService userService) {
        super();
        this.userService = userService;
    }
}
