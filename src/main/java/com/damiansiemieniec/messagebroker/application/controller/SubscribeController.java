package com.damiansiemieniec.messagebroker.application.controller;

import com.damiansiemieniec.messagebroker.application.dto.AddSubscriberRequest;
import com.damiansiemieniec.messagebroker.application.dto.GeneralResponse;
import com.damiansiemieniec.messagebroker.domain.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscribeController {
    private final SubscriberService subscriberService;

    @Autowired
    public SubscribeController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribe")
    public GeneralResponse addSubscriber(@RequestBody AddSubscriberRequest request) {
        this.subscriberService.addSubscriber(request.getTopic(), request.getUrl());

        return new GeneralResponse(true, "Success");
    }
}
