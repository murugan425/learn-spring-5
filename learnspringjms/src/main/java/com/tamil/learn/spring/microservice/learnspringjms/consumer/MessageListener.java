package com.tamil.learn.spring.microservice.learnspringjms.consumer;

import com.tamil.learn.spring.microservice.learnspringjms.config.MessageConfig;
import com.tamil.learn.spring.microservice.learnspringjms.model.RabbitMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created on 04/11/21 - 5:45 PM
 * Created by murugan
 * Copyright 2021 NEXTURN [murugan]. All Rights Reserved.
 */
@Slf4j
@Component
public class MessageListener {

    @RabbitListener(queues = "testqueue")
    public void receiveMessage(RabbitMessage msg) {
        log.info("MessageListener received from Queue. " + msg);
    }
}
