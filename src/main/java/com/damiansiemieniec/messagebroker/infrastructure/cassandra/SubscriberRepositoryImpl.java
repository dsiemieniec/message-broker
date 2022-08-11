package com.damiansiemieniec.messagebroker.infrastructure.cassandra;

import com.damiansiemieniec.messagebroker.domain.entity.Subscriber;
import com.damiansiemieniec.messagebroker.domain.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SubscriberRepositoryImpl implements SubscriberRepository {
    private final CassandraSubscriberRepository repository;

    @Autowired
    public SubscriberRepositoryImpl(CassandraSubscriberRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Subscriber subscriber) {
        if (subscriber.getId() == null) {
            subscriber.setId(UUID.randomUUID());
        }

        this.repository.save(subscriber);
    }

    @Override
    public List<Subscriber> findByTopic(String topic) {
        return this.repository.findByTopic(topic);
    }
}
