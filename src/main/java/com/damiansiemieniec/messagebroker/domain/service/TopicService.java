package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void createTopic(String name) throws DuplicateException {
        var topic = this.topicRepository.findOneByName(name);
        if (topic.isPresent()) {
            throw new DuplicateException("Topic already exists");
        }

        var newTopic = new Topic();
        newTopic.setName(name);
        this.topicRepository.save(newTopic);
    }
}
