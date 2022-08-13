package com.damiansiemieniec.messagebroker.domain.handler;

import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;

public interface CommandHandler<T> {
    void handle(T command) throws DuplicateException, IllegalArgumentException;
}
