package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Subscriber;
import com.damiansiemieniec.messagebroker.domain.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public void addSubscriber(String topic, String url) {
        var subscriber = new Subscriber();
        subscriber.setTopic(topic);
        subscriber.setUrl(url);
        subscriberRepository.save(subscriber);
    }

    public List<Subscriber> findByTopic(String topic) {
        return this.subscriberRepository.findByTopic(topic);
    }
}
