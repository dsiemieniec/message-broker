package com.damiansiemieniec.messagebroker.domain.handler;

import com.damiansiemieniec.messagebroker.domain.command.CreateGroupCommand;
import com.damiansiemieniec.messagebroker.domain.entity.Group;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateGroupHandler implements CommandHandler<CreateGroupCommand> {
    private final GroupRepository groupRepository;

    @Autowired
    public CreateGroupHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void handle(CreateGroupCommand command) throws DuplicateException, IllegalArgumentException {
        var name = command.getName();
        if (name.isBlank() || !name.matches("^[a-zA-Z0-9_-]*$") || name.length() > 100) {
            throw new IllegalArgumentException("Invalid format of a group name");
        }

        if (command.getDescription().length() > 200) {
            throw new IllegalArgumentException("Description is too long");
        }

        var group = this.groupRepository.findOneByName(name);
        if (group.isPresent()) {
            throw new DuplicateException("Group with this name already exists");
        }

        this.groupRepository.save(new Group(name, command.getDescription()));
    }
}
