package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;
import com.damiansiemieniec.messagebroker.domain.event.DomainEventPublisher;
import com.damiansiemieniec.messagebroker.domain.event.TopicCreated;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final DomainEventPublisher publisher;

    @Autowired
    public TopicService(TopicRepository topicRepository, DomainEventPublisher publisher) {
        this.topicRepository = topicRepository;
        this.publisher = publisher;
    }

    public void createTopic(String name) throws DuplicateException {
        var topic = this.topicRepository.findOneByName(name);
        if (topic.isPresent()) {
            throw new DuplicateException("Topic already exists");
        }

        var newTopic = new Topic();
        newTopic.setName(name);
        this.topicRepository.save(newTopic);

        this.publisher.publish(new TopicCreated(this, newTopic));
    }
}
