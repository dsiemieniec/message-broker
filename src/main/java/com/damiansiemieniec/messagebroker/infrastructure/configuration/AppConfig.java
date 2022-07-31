package com.damiansiemieniec.messagebroker.infrastructure.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import java.net.MalformedURLException;

@Configuration
public class AppConfig {
    @Value("${message-broker.activemq.broker-url}")
    private String activemqBrokerUrl;
    @Value("${message-broker.activemq.user}")
    private String activemqUser;
    @Value("${message-broker.activemq.password}")
    private String activemqPassword;
    @Value("${message-broker.couchdb.url}")
    private String couchdbUrl;
    @Value("${message-broker.couchdb.database-name}")
    private String couchdbDatabaseName;
    @Value("${message-broker.solr.base-url}")
    private String solrBaseUrl;

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

    @Bean
    public CouchDbConnector couchDbConnector() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder().url(couchdbUrl).build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector(couchdbDatabaseName, dbInstance);
        db.createDatabaseIfNotExists();

        return db;
    }

    @Bean
    public Http2SolrClient solrClient() {
        return new Http2SolrClient.Builder(solrBaseUrl).build();
    }
}
