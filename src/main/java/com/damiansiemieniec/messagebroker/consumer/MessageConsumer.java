package com.damiansiemieniec.messagebroker.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @JmsListener(destination = "message_broker")
    public void processMessage(String content) {
        System.out.println("Received message " + content);
    }
}
