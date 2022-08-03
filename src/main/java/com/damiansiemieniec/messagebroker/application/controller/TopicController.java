package com.damiansiemieniec.messagebroker.application.controller;

import com.damiansiemieniec.messagebroker.application.dto.CreateTopicRequest;
import com.damiansiemieniec.messagebroker.application.dto.GeneralResponse;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topic")
    public GeneralResponse createTopic(@RequestBody CreateTopicRequest request) throws DuplicateException {
        this.topicService.createTopic(request.getName());

        return new GeneralResponse(true, request.getName());
    }
}
