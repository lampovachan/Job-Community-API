package com.tkachuk.jobnetwork.service;

import com.tkachuk.jobnetwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private static final String TOPIC = "NewTopic";

    public void sendMessage(@Autowired KafkaTemplate kafkaTemplate, User user) {
        kafkaTemplate.send(TOPIC, user);
    }
}
