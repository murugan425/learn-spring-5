package com.tamil.learn.spring.microservice.learnspringjms.producer;

import com.tamil.learn.spring.microservice.learnspringjms.config.MessageConfig;
import com.tamil.learn.spring.microservice.learnspringjms.model.RabbitMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created on 04/11/21 - 5:05 PM
 * Created by murugan
 * Copyright 2021 NEXTURN [murugan]. All Rights Reserved.
 */
@Slf4j
@Component
@Getter
@Setter
public class MessageProducer {

    @Value("${rabbitmq.test.exchange}")
    private String topicExchangeName;

    @Value("${rabbitmq.test.routing.key1}")
    private String routingKey;

    private AmqpTemplate rabbitAmqpTemplate;

    public MessageProducer(final AmqpTemplate rabbitAmqpTemplate) {
        this.rabbitAmqpTemplate = rabbitAmqpTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void sendMessage() {
        RabbitMessage rabbitMsg = RabbitMessage.builder()
                .id(UUID.randomUUID())
                .message("Rabbit MQ Message - 5Sec")
                .build();
        rabbitAmqpTemplate.convertAndSend(topicExchangeName, routingKey, rabbitMsg);
        log.info("MessageProducer sending a message. " + rabbitMsg);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendFastMessage() {
        RabbitMessage rabbitMsg = RabbitMessage.builder()
                .id(UUID.randomUUID())
                .message("Rabbit MQ Message - 1Sec")
                .build();
        rabbitAmqpTemplate.convertAndSend(topicExchangeName, routingKey, rabbitMsg);
        log.info("MessageProducer sending a message. " + rabbitMsg);
    }

}
