package com.damiansiemieniec.messagebroker.infrastructure.couchdb;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class CouchDbConfig {
    @Value("${message-broker.couchdb.url}")
    private String couchdbUrl;
    @Value("${message-broker.couchdb.database-name}")
    private String couchdbDatabaseName;

    @Bean
    public CouchDbConnector couchDbConnector() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder().url(couchdbUrl).build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector(couchdbDatabaseName, dbInstance);
        db.createDatabaseIfNotExists();

        return db;
    }
}
