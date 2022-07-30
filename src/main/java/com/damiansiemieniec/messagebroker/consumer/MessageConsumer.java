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
import java.util.UUID;

@Component
public class MessageConsumer {
    @JmsListener(destination = "message_broker")
    public void processMessage(String content) {
        UUID uuid = UUID.randomUUID();
        indexMessage("Received message: " + content, uuid);
        notifySubscriber("http://localhost:8000/castlemock/mock/rest/project/szSWBv/application/GwHaVy/some-funny-endpoint", content, uuid);
        notifySubscriber("http://localhost:8000/castlemock/mock/rest/project/szSWBv/application/GwHaVy/slow-endpoint", content, uuid);
    }

    private void notifySubscriber(String subscriberUrl,  String content, UUID eventId) {
        var client = HttpClient.newHttpClient();
        var uri = URI.create(subscriberUrl);
        var request = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            indexMessage("Subscriber response: " +  response.body(), eventId, subscriberUrl);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            indexMessage("Exception: " + e.getMessage(), eventId, subscriberUrl);
        }
    }

    private void indexMessage(String message, UUID uuid) {
        indexMessage(message, uuid, null);
    }

    private void indexMessage(String message, UUID uuid, String subscriberUrl) {
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
        doc.addField("subscriber_url", subscriberUrl);

        try {
            final UpdateResponse updateResponse = client.add("gettingstarted", doc);
            client.commit("gettingstarted");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
}
