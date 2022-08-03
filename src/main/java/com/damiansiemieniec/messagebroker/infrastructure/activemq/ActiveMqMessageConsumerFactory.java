package com.damiansiemieniec.messagebroker.infrastructure.activemq;

import com.damiansiemieniec.messagebroker.domain.consumer.EventConsumer;
import com.damiansiemieniec.messagebroker.domain.consumer.EventConsumerFactory;
import com.damiansiemieniec.messagebroker.domain.handler.MessageHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class ActiveMqMessageConsumerFactory implements EventConsumerFactory {
    private final ActiveMQConnectionFactory connectionFactory;
    private final MessageHandler messageHandler;

    public ActiveMqMessageConsumerFactory(ActiveMQConnectionFactory connectionFactory, MessageHandler messageHandler) {
        this.connectionFactory = connectionFactory;
        this.messageHandler = messageHandler;
    }

    @Override
    public EventConsumer create(String topic) {
        return new ActiveMqMessageConsumer(connectionFactory, topic, messageHandler);
    }
}
