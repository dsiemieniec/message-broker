package com.damiansiemieniec.messagebroker.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    private final JmsTemplate jms;

    @Autowired
    public MessagePublisher(JmsTemplate jms) {
        this.jms = jms;
    }

    public void publish(String message) {
        this.jms.convertAndSend("message_broker", message);
    }
}
