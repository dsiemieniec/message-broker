package com.damiansiemieniec.messagebroker.application.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;

import javax.jms.*;

public class EventConsumer implements Runnable, ExceptionListener {

    private final ActiveMQConnectionFactory connectionFactory;
    private final String topic;
    private final MessageHandler messageHandler;

    public EventConsumer(ActiveMQConnectionFactory connectionFactory, String topic, MessageHandler messageHandler) {
        this.connectionFactory = connectionFactory;
        this.topic = topic;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(topic);
            MessageConsumer consumer = session.createConsumer(destination);
            Message message;
            do {
                message = consumer.receive();
                onMessage(message);
            } while (message != null);

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    private void onMessage(Message message) throws JMSException {
        if (message instanceof TextMessage textMessage) {
            var json = new JSONObject(textMessage.getText());
            messageHandler.onMessage(topic, json);
        } else {
            System.out.println("Received " + message.toString());
        }
    }

    @Override
    public void onException(JMSException e) {
        System.out.println("JMS Exception occured. Shutting down client.");
    }
}
