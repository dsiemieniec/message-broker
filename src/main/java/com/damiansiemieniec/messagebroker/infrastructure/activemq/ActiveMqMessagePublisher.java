package com.damiansiemieniec.messagebroker.infrastructure.activemq;

import com.damiansiemieniec.messagebroker.domain.service.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqMessagePublisher implements MessagePublisher {
    private final JmsTemplate jms;

    @Autowired
    public ActiveMqMessagePublisher(JmsTemplate jms) {
        this.jms = jms;
    }

    public void publish(String topic, String message) {
        this.jms.convertAndSend(topic, message);
    }
}
