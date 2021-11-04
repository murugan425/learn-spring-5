package com.tamil.learn.spring.microservice.learnspringjms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created on 03/11/21 - 6:25 PM
 * Created by murugan
 * Copyright 2021 NEXTURN [murugan]. All Rights Reserved.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RabbitMessage implements Serializable {
    static final long serialVersionUID = 1000001L;
    private UUID id;
    private String message;
}
