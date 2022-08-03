package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Event;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final MessagePublisher publisher;
    private final EventLogger eventLogger;

    @Autowired
    public EventService(MessagePublisher publisher, EventLogger eventLogger) {
        this.publisher = publisher;
        this.eventLogger = eventLogger;
    }

    public void publish(String topic, String content) {
        var event = new Event(content);
        publisher.publish(topic, event.toJson().toString());
        this.eventLogger.log(event, "Event published");
    }
}
