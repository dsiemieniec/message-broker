package com.damiansiemieniec.messagebroker.controller;

import com.damiansiemieniec.messagebroker.dto.PublishRequest;
import com.damiansiemieniec.messagebroker.dto.PublishResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    private JmsTemplate jmsTemplate;

    @Autowired
    public PublishController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/publish")
    public PublishResponse publish(@RequestBody PublishRequest request) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.jmsTemplate.convertAndSend("message_broker", ow.writeValueAsString(request));

        return new PublishResponse(true, request.getMessage());
    }
}
