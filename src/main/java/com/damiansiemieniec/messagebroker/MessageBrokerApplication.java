package com.damiansiemieniec.messagebroker;

import com.damiansiemieniec.messagebroker.domain.consumer.EventConsumerFactory;
import com.damiansiemieniec.messagebroker.domain.repository.TopicRepository;
import com.damiansiemieniec.messagebroker.domain.service.ConsumerManager;
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
    CommandLineRunner commandLineRunner(EventConsumerFactory consumerFactory, TopicRepository topicRepository) {
        return args -> {
            var manager = ConsumerManager.getInstance(consumerFactory);
            System.out.println("Starting consumers for defined topics...");
            for (var topic : topicRepository.findAll()) {
                manager.startConsumer(topic.getName());
            }

            System.out.println("Consumers started.");
        };
    }
}
