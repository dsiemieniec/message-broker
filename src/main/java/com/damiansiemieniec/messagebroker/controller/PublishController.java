package com.damiansiemieniec.messagebroker.controller;

import com.damiansiemieniec.messagebroker.dto.PublishRequest;
import com.damiansiemieniec.messagebroker.dto.PublishResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    @PostMapping("/publish")
    public PublishResponse publish(@RequestBody PublishRequest request)  {
        return new PublishResponse(true, request.getMessage());
    }
}
