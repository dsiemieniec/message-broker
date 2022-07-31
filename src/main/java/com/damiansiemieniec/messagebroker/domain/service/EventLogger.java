package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Event;

public interface EventLogger {
    void indexMessage(Event event, String message);
}
