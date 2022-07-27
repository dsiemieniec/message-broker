package com.damiansiemieniec.messagebroker.controller;

import com.damiansiemieniec.messagebroker.dto.PublishRequest;
import com.damiansiemieniec.messagebroker.dto.PublishResponse;
import com.damiansiemieniec.messagebroker.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    private final MessagePublisher publisher;

    @Autowired
    public PublishController(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/publish")
    public PublishResponse publish(@RequestBody PublishRequest request) throws IllegalArgumentException {
        if (request.getCopies() <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < request.getCopies(); i++) {
            publisher.publish("#" + (i+1) + " " + request.getMessage());
        }

        return new PublishResponse(true, request.getMessage());
    }
}
