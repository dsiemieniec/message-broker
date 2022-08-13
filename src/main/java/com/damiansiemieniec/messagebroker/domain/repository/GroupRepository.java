package com.damiansiemieniec.messagebroker.domain.repository;

import com.damiansiemieniec.messagebroker.domain.entity.Group;

import java.util.Optional;

public interface GroupRepository {
    void save(Group group);
    Optional<Group> findOneByName(String name);
}
