package com.damiansiemieniec.messagebroker.domain.event;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;
import org.springframework.context.ApplicationEvent;

public class TopicCreated extends ApplicationEvent {
    private final Topic topic;

    public TopicCreated(Object source, Topic topic) {
        super(source);
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}
