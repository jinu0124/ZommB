package com.ssafy.commb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {

    private final int POOL_SIZE = 6;            // Thread Pool

    @Override
    public void configureTasks(ScheduledTaskRegistrar registry) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("CommB-Scheduled-task-");
        threadPoolTaskScheduler.initialize();

        registry.setTaskScheduler(threadPoolTaskScheduler);
    }
}
