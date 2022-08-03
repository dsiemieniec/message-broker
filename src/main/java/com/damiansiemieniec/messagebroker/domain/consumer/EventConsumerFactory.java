package com.damiansiemieniec.messagebroker.domain.consumer;

public interface EventConsumerFactory {
    EventConsumer create(String topic);
}
