package com.tkachuk.jobnetwork.kafka;

import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    @Autowired
    KafkaTemplate kafkaTemplate;

    private static final String TOPIC = "NewTopic";

    public void sendMessage(KafkaTemplate kafkaTemplate, User user) {
        kafkaTemplate.send(TOPIC, user);
    }

    public void sendMessageWrapper(User user) {
       sendMessage(kafkaTemplate, user);
    }
}
