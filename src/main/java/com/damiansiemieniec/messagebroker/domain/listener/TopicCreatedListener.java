package com.damiansiemieniec.messagebroker.domain.listener;

import com.damiansiemieniec.messagebroker.domain.consumer.EventConsumerFactory;
import com.damiansiemieniec.messagebroker.domain.event.TopicCreated;
import com.damiansiemieniec.messagebroker.domain.service.ConsumerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TopicCreatedListener implements ApplicationListener<TopicCreated> {
    private final EventConsumerFactory eventConsumerFactory;

    @Autowired
    public TopicCreatedListener(EventConsumerFactory eventConsumerFactory) {
        this.eventConsumerFactory = eventConsumerFactory;
    }

    @Override
    public void onApplicationEvent(TopicCreated event) {
        System.out.println("Staring consumer for " + event.getTopic().getName());
        ConsumerManager.getInstance(eventConsumerFactory).startConsumer(event.getTopic().getName());
        System.out.println("Consumer started");
    }
}
