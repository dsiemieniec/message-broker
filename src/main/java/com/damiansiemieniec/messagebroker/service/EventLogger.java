package com.damiansiemieniec.messagebroker.service;

import com.damiansiemieniec.messagebroker.dto.Event;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventLogger {
    private final Http2SolrClient client;

    @Autowired
    public EventLogger(Http2SolrClient client) {
        this.client = client;
    }

    public void indexMessage(Event event, String message) {
        System.out.println(event.getId().toString() +  " " + message);

        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField("event_id", event.getId().toString());
        doc.addField("event_content", event.getContent());
        doc.addField("log_message", message);
        doc.addField("timestamp", System.currentTimeMillis());

        try {
            final UpdateResponse updateResponse = client.add("gettingstarted", doc);
            client.commit("gettingstarted");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
}
