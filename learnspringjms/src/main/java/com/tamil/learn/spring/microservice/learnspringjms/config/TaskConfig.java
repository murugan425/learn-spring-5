package com.tamil.learn.spring.microservice.learnspringjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created on 03/11/21 - 8:04 PM
 * Created by murugan
 * Copyright 2021 NEXTURN [murugan]. All Rights Reserved.
 */
@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {

    @Bean
    TaskExecutor taskExecutor() {
       return new SimpleAsyncTaskExecutor();
    }
}
