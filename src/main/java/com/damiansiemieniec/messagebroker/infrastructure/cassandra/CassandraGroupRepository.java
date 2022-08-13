package com.damiansiemieniec.messagebroker.infrastructure.cassandra;

import com.damiansiemieniec.messagebroker.domain.entity.Group;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface CassandraGroupRepository extends CassandraRepository<Group, UUID> {
    @AllowFiltering
    Optional<Group> findOptionalByName(String name);
}
