package com.damiansiemieniec.messagebroker.application.dto;

public class PublishRequest {
    private String message;
    private int copies;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
