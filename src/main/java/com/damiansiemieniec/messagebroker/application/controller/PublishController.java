package com.damiansiemieniec.messagebroker.application.controller;

import com.damiansiemieniec.messagebroker.application.dto.GeneralResponse;
import com.damiansiemieniec.messagebroker.application.dto.PublishRequest;
import com.damiansiemieniec.messagebroker.domain.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {
    private final EventService service;

    @Autowired
    public PublishController(EventService service) {

        this.service = service;
    }

    @PostMapping("/publish/{topic}")
    public GeneralResponse publish(@PathVariable String topic, @RequestBody PublishRequest request) throws IllegalArgumentException {
        if (request.getCopies() <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < request.getCopies(); i++) {
            this.service.publish(topic, "#" + (i+1) + " " + request.getMessage());
        }

        return new GeneralResponse(true, request.getMessage());
    }
}
