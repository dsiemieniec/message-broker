package com.damiansiemieniec.messagebroker.controller;

import com.damiansiemieniec.messagebroker.dto.Event;
import com.damiansiemieniec.messagebroker.dto.PublishRequest;
import com.damiansiemieniec.messagebroker.dto.GeneralResponse;
import com.damiansiemieniec.messagebroker.publisher.MessagePublisher;
import com.damiansiemieniec.messagebroker.service.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublishController {

    private final MessagePublisher publisher;
    private final EventLogger eventLogger;

    @Autowired
    public PublishController(MessagePublisher publisher, EventLogger eventLogger) {
        this.publisher = publisher;
        this.eventLogger = eventLogger;
    }

    @PostMapping("/publish/{topic}")
    public GeneralResponse publish(@PathVariable String topic, @RequestBody PublishRequest request) throws IllegalArgumentException {
        if (request.getCopies() <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < request.getCopies(); i++) {
            var event = new Event("#" + (i+1) + " " + request.getMessage());
            publisher.publish(topic, event.toJson().toString());
            this.eventLogger.indexMessage(event, "Event published");
        }

        return new GeneralResponse(true, request.getMessage());
    }
}
