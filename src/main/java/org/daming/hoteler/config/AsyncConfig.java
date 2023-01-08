package org.daming.hoteler.config;

import org.daming.hoteler.base.exceptions.HotelerAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author gming001
 * @version 2023-01-08 14:18
 */
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2 * Runtime.getRuntime().availableProcessors() + 1);

        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.taskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new HotelerAsyncExceptionHandler();
    }
}
