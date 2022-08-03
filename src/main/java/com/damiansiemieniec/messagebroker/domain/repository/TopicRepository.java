package com.damiansiemieniec.messagebroker.domain.repository;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;

import java.util.Optional;

public interface TopicRepository {
    void save(Topic topic);
    Optional<Topic> findOneByName(String name);
}
