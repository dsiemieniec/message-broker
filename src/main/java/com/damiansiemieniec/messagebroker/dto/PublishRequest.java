package com.damiansiemieniec.messagebroker.dto;

public class PublishRequest {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PublishRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}
