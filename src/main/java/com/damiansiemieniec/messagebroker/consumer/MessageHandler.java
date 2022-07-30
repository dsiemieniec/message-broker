package com.damiansiemieniec.messagebroker.consumer;

import com.damiansiemieniec.messagebroker.dto.Event;
import com.damiansiemieniec.messagebroker.entity.Subscriber;
import com.damiansiemieniec.messagebroker.service.EventLogger;
import com.damiansiemieniec.messagebroker.service.SubscriberService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class MessageHandler {
    private final SubscriberService subscriberService;
    private final EventLogger eventLogger;

    public MessageHandler(SubscriberService subscriberService, EventLogger eventLogger) {
        this.subscriberService = subscriberService;
        this.eventLogger = eventLogger;
    }

    public void onMessage(JSONObject message) {
        var event = Event.fromJson(message);

        this.eventLogger.indexMessage(event, "Event consumed " + event.getContent());
        try {
            for (var subscriber : subscriberService.findByTopic("message_broker")) {
                notifySubscriber(subscriber, event);
            }
        } catch (Exception exception) {
            this.eventLogger.indexMessage(event, exception.getMessage());
        }
    }

    private void notifySubscriber(Subscriber subscriber, Event event) {
        try {
            var client = HttpClient.newHttpClient();
            var uri = URI.create(subscriber.getUrl());
            var request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(event.getContent()))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.eventLogger.indexMessage(event, "Subscriber " + subscriber.getId() + " Response: " +  response.body());
        } catch (Exception e) {
            e.printStackTrace();
            this.eventLogger.indexMessage(event, "Subscriber " + subscriber.getId() + " Exception: " + e.getMessage());
        }
    }
}
