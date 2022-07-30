package com.damiansiemieniec.messagebroker.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import java.net.MalformedURLException;

@Configuration
public class AppConfig {
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setPassword("admin");
        connectionFactory.setUserName("admin");

        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(false); // false for a Queue, true for a Topic

        return template;
    }

    @Bean
    public CouchDbConnector couchDbConnector() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder().url("http://admin:admin@localhost:5984").build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("message-broker", dbInstance);
        db.createDatabaseIfNotExists();

        return db;
    }
}
