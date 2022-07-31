package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Event;
import com.damiansiemieniec.messagebroker.domain.entity.Subscriber;
import com.damiansiemieniec.messagebroker.domain.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final EventLogger eventLogger;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, EventLogger eventLogger) {
        this.subscriberRepository = subscriberRepository;
        this.eventLogger = eventLogger;
    }

    public void addSubscriber(String topic, String url) {
        var subscriber = new Subscriber();
        subscriber.setTopic(topic);
        subscriber.setUrl(url);
        subscriberRepository.save(subscriber);
    }

    public List<Subscriber> findByTopic(String topic) {
        return this.subscriberRepository.findByTopic(topic);
    }

    public void notifyEventSubscribers(String topic, Event event) {
        try {
            for (var subscriber : findByTopic(topic)) {
                notifySubscriber(subscriber, event);
            }
        } catch (Exception exception) {
            this.eventLogger.log(event, exception.getMessage());
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
            this.eventLogger.log(event, "Subscriber " + subscriber.getId() + " Response: " +  response.body());
        } catch (Exception e) {
            e.printStackTrace();
            this.eventLogger.log(event, "Subscriber " + subscriber.getId() + " Exception: " + e.getMessage());
        }
    }
}
