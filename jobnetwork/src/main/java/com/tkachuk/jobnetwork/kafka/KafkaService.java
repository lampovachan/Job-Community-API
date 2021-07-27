package com.tkachuk.jobnetwork.kafka;

import com.tkachuk.jobnetwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for sending message via Kafka.
 *
 * @author Svitlana Tkachuk
 */
@Service
public class KafkaService {
    @Autowired
    KafkaTemplate kafkaTemplate;

    private static final String TOPIC = "NewTopic";

    public void sendMessage(@Autowired KafkaTemplate kafkaTemplate, User user) {
        kafkaTemplate.send(TOPIC, user);
    }

    public void sendMessageWrapper(User user) {
        sendMessage(kafkaTemplate, user);
    }
}
