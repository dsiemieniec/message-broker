package com.damiansiemieniec.messagebroker.domain.handler;

import com.damiansiemieniec.messagebroker.domain.entity.Event;
import com.damiansiemieniec.messagebroker.domain.service.EventLogger;
import com.damiansiemieniec.messagebroker.domain.service.SubscriberService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {
    private final SubscriberService subscriberService;
    private final EventLogger eventLogger;

    public MessageHandler(SubscriberService subscriberService, EventLogger eventLogger) {
        this.subscriberService = subscriberService;
        this.eventLogger = eventLogger;
    }

    public void onMessage(String topic, JSONObject message) {
        var event = Event.fromJson(message);

        this.eventLogger.log(event, "Event consumed " + event.getContent());
        try {
            this.subscriberService.notifyEventSubscribers(topic, event);
        } catch (Exception exception) {
            this.eventLogger.log(event, exception.getMessage());
        }
    }
}
