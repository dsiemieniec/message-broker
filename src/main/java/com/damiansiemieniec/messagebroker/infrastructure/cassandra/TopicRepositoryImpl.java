package com.damiansiemieniec.messagebroker.infrastructure.cassandra;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;
import com.damiansiemieniec.messagebroker.domain.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
    private final CassandraTopicRepository repository;

    @Autowired
    public TopicRepositoryImpl(CassandraTopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Topic topic) {
        if (topic.getId() == null) {
            topic.setId(UUID.randomUUID());
        }
        this.repository.save(topic);
    }

    @Override
    public Optional<Topic> findOneByName(String name) {
        return this.repository.findOptionalByName(name);
    }

    @Override
    public List<Topic> findAll() {
        return this.repository.findAll();
    }
}
