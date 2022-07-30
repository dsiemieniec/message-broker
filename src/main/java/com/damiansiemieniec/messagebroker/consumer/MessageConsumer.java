package com.damiansiemieniec.messagebroker.consumer;

import com.damiansiemieniec.messagebroker.dto.Event;
import com.damiansiemieniec.messagebroker.service.EventLogger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class MessageConsumer {
    private final EventLogger eventLogger;

    @Autowired
    public MessageConsumer(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    @JmsListener(destination = "message_broker")
    public void processMessage(String content) {
        var event = Event.fromJson(new JSONObject(content));

        this.eventLogger.indexMessage(event, "Event consumed");
        notifySubscriber("http://localhost:8000/castlemock/mock/rest/project/szSWBv/application/GwHaVy/some-funny-endpoint", event);
        notifySubscriber("http://localhost:8000/castlemock/mock/rest/project/szSWBv/application/GwHaVy/slow-endpoint", event);
    }

    private void notifySubscriber(String subscriberUrl, Event event) {
        var client = HttpClient.newHttpClient();
        var uri = URI.create(subscriberUrl);
        var request = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(event.getContent()))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.eventLogger.indexMessage(event, "Subscriber " + subscriberUrl + " Response: " +  response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            this.eventLogger.indexMessage(event, "Subscriber " + subscriberUrl + " Exception: " + e.getMessage());
        }
    }
}
