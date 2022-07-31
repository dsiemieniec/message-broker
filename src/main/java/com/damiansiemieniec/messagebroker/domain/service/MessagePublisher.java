package com.damiansiemieniec.messagebroker.domain.service;

public interface MessagePublisher {
    void publish(String topic, String message);
}
