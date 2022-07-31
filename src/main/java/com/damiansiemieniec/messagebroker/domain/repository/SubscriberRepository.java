package com.damiansiemieniec.messagebroker.domain.repository;

import com.damiansiemieniec.messagebroker.domain.entity.Subscriber;

import java.util.List;

public interface SubscriberRepository {
    void save(Subscriber subscriber);
    List<Subscriber> findByTopic(String topic);
}
