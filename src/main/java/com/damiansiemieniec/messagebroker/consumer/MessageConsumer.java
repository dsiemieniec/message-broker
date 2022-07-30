package com.damiansiemieniec.messagebroker.consumer;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.util.UUID;

@Component
public class MessageConsumer {
    @JmsListener(destination = "message_broker")
    public void processMessage(String content) {
        UUID uuid = UUID.randomUUID();

        indexMessage("Received message: " + content, uuid);

        var client = HttpClient.newHttpClient();
        var uri = URI.create("http://localhost:8000/castlemock/mock/rest/project/szSWBv/application/GwHaVy/some-funny-endpoint");
        var request = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            indexMessage("Subscriber response: " +  response.body(), uuid);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            indexMessage("Exception: " + e.getMessage(), uuid);
        }
    }

    private void indexMessage(String message, UUID uuid) {
        System.out.println(uuid.toString() +  " " + message);

        final String solrUrl = "http://localhost:8983/solr";
        var client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();

        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField("event_id", uuid.toString());
        doc.addField("message", message);
        doc.addField("timestamp", System.currentTimeMillis());

        try {
            final UpdateResponse updateResponse = client.add("gettingstarted", doc);
            client.commit("gettingstarted");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
}
