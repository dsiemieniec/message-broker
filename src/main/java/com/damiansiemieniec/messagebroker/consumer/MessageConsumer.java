package com.damiansiemieniec.messagebroker.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class MessageConsumer {
    @JmsListener(destination = "message_broker")
    public void processMessage(String content) {
        System.out.println("Received message: " + content);

        var client = HttpClient.newHttpClient();
        var uri = URI.create("http://localhost:8000/castlemock/mock/rest/project/szSWBv/application/GwHaVy/some-funny-endpoint");
        var request = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Subscriber response: " +  response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
