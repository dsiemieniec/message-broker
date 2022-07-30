package com.damiansiemieniec.messagebroker;

import com.damiansiemieniec.messagebroker.consumer.ConsumerManager;
import com.damiansiemieniec.messagebroker.consumer.MessageHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class MessageBrokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageBrokerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ActiveMQConnectionFactory connectionFactory, MessageHandler messageHandler) {
        return args -> {
            System.out.println("Command line runner works!!!!");

            var manager = ConsumerManager.getInstance(connectionFactory, messageHandler);
            manager.startConsumer("message_broker");
            manager.startConsumer("second_topic");
        };
    }
}
