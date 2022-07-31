package com.damiansiemieniec.messagebroker.application.controller;

import com.damiansiemieniec.messagebroker.application.dto.GeneralResponse;
import com.damiansiemieniec.messagebroker.application.dto.PublishRequest;
import com.damiansiemieniec.messagebroker.domain.entity.Event;
import com.damiansiemieniec.messagebroker.domain.service.EventLogger;
import com.damiansiemieniec.messagebroker.domain.service.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
