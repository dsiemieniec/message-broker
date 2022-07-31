package com.damiansiemieniec.messagebroker.infrastructure.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMqConfig {
    @Value("${message-broker.activemq.broker-url}")
    private String activemqBrokerUrl;
    @Value("${message-broker.activemq.user}")
    private String activemqUser;
    @Value("${message-broker.activemq.password}")
    private String activemqPassword;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activemqBrokerUrl);
        connectionFactory.setPassword(activemqPassword);
        connectionFactory.setUserName(activemqUser);

        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(false); // false for a Queue, true for a Topic

        return template;
    }
}
