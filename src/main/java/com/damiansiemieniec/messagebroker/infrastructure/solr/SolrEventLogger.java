package com.damiansiemieniec.messagebroker.infrastructure.solr;

import com.damiansiemieniec.messagebroker.domain.entity.Event;
import com.damiansiemieniec.messagebroker.domain.service.EventLogger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolrEventLogger implements EventLogger {
    private final Http2SolrClient client;

    @Autowired
    public SolrEventLogger(Http2SolrClient client) {
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
