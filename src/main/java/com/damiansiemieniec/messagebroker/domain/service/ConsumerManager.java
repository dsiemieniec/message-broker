package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.consumer.EventConsumer;
import com.damiansiemieniec.messagebroker.domain.consumer.EventConsumerFactory;

import java.util.HashMap;
import java.util.Map;

public class ConsumerManager {
    private static ConsumerManager instance;
    private final Map<String, Thread> consumers = new HashMap<>();
    private final EventConsumerFactory eventConsumerFactory;

    private ConsumerManager(EventConsumerFactory eventConsumerFactory) {
        this.eventConsumerFactory = eventConsumerFactory;
    }

    public static ConsumerManager getInstance(EventConsumerFactory eventConsumerFactory) {
        if (instance == null) {
            instance = new ConsumerManager(eventConsumerFactory);
        }

        return instance;
    }

    public void startConsumer(String topic) {
        if (!consumers.containsKey(topic)) {
            var thread = createThread(createConsumer(topic));
            thread.start();
            consumers.put(topic, thread);
        }
    }

    private EventConsumer createConsumer(String topic) {
        return this.eventConsumerFactory.create(topic);
    }

    private Thread createThread(Runnable runnable) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(false);

        return brokerThread;
    }
}
