package com.damiansiemieniec.messagebroker.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class ConsumerManager {
    private static ConsumerManager instance;

    private final ActiveMQConnectionFactory connectionFactory;
    private final MessageHandler messageHandler;
    private final Map<String, Thread> consumers = new HashMap<>();

    private ConsumerManager(ActiveMQConnectionFactory connectionFactory, MessageHandler messageHandler) {
        this.connectionFactory = connectionFactory;
        this.messageHandler = messageHandler;
    }

    public static ConsumerManager getInstance(ActiveMQConnectionFactory connectionFactory, MessageHandler messageHandler) {
        if (instance == null) {
            instance = new ConsumerManager(connectionFactory, messageHandler);
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
        return new EventConsumer(connectionFactory, topic, messageHandler);
    }

    private Thread createThread(Runnable runnable) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(false);

        return brokerThread;
    }
}
