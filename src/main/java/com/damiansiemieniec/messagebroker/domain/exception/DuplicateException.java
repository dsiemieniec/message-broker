package com.damiansiemieniec.messagebroker.domain.exception;

public class DuplicateException extends Exception {
    public DuplicateException(String message) {
        super(message);
    }
}
