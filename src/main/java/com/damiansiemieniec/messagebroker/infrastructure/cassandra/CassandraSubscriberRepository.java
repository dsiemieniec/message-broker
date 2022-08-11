package com.damiansiemieniec.messagebroker.infrastructure.cassandra;

import com.damiansiemieniec.messagebroker.domain.entity.Subscriber;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface CassandraSubscriberRepository extends CassandraRepository<Subscriber, UUID> {
    @AllowFiltering
    List<Subscriber> findByTopic(String topic);
}
