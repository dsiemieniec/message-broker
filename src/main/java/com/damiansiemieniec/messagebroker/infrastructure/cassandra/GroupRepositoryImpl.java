package com.damiansiemieniec.messagebroker.infrastructure.cassandra;

import com.damiansiemieniec.messagebroker.domain.entity.Group;
import com.damiansiemieniec.messagebroker.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class GroupRepositoryImpl implements GroupRepository {
    private final CassandraGroupRepository repository;

    @Autowired
    public GroupRepositoryImpl(CassandraGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Group group) {
        if (group.getId() == null) {
            group.setId(UUID.randomUUID());
        }

        this.repository.save(group);
    }

    @Override
    public Optional<Group> findOneByName(String name) {
        return this.repository.findOptionalByName(name);
    }
}
