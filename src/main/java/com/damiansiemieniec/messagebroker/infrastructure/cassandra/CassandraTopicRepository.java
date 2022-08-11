package com.damiansiemieniec.messagebroker.infrastructure.cassandra;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface CassandraTopicRepository extends CassandraRepository<Topic, UUID> {
    @AllowFiltering
    Optional<Topic> findOptionalByName(String name);
}
