package com.damiansiemieniec.messagebroker.infrastructure.solr;

import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {
    @Value("${message-broker.solr.base-url}")
    private String solrBaseUrl;

    @Bean
    public Http2SolrClient solrClient() {
        return new Http2SolrClient.Builder(solrBaseUrl).build();
    }
}
