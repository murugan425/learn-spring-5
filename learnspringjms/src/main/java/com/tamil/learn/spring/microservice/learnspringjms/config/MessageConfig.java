package com.tamil.learn.spring.microservice.learnspringjms.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 03/11/21 - 8:30 PM
 * Created by murugan
 * Copyright 2021 NEXTURN [murugan]. All Rights Reserved.
 */
@Configuration
public class MessageConfig {

    @Value("${rabbitmq.test.queue}")
    private String queueName;

    @Value("${rabbitmq.test.exchange}")
    private String topicExchangeName;

    @Value("${rabbitmq.test.routing.key1}")
    private String routingKey;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName, true, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }

    @Bean
    public AmqpTemplate rabbitAmqpTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
