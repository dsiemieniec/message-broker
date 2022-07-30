package com.damiansiemieniec.messagebroker.dto;

public class GeneralResponse {
    private final boolean success;
    private final String message;

    public GeneralResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
