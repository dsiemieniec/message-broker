package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Group;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void create(String name, String description) throws IllegalArgumentException, DuplicateException {
        if (name.isBlank() || !name.matches("^[a-zA-Z0-9_-]*$") || name.length() > 100) {
            throw new IllegalArgumentException("Invalid format of a group name");
        }

        if (description.length() > 200) {
            throw new IllegalArgumentException("Description is too long");
        }

        var group = this.groupRepository.findOneByName(name);
        if (group.isPresent()) {
            throw new DuplicateException("Group with this name already exists");
        }

        this.groupRepository.save(new Group(name, description));
    }
}
