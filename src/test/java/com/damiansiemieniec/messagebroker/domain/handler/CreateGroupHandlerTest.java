package com.damiansiemieniec.messagebroker.domain.handler;

import com.damiansiemieniec.messagebroker.domain.command.CreateGroupCommand;
import com.damiansiemieniec.messagebroker.domain.entity.Group;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.repository.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class CreateGroupHandlerTest  {
    @Test
    void shouldCreateGroup() throws DuplicateException {
        String name = "validTestName";
        String description = "Some funny description";
        var command = new CreateGroupCommand(name, description);
        var repositoryMock =  Mockito.mock(GroupRepository.class);
        Mockito.when(repositoryMock.findOneByName(name)).thenReturn(Optional.empty());

        new CreateGroupHandler(repositoryMock).handle(command);
        Mockito.verify(repositoryMock, Mockito.atLeastOnce()).findOneByName(Mockito.eq(name));
        Mockito.verify(repositoryMock, Mockito.atLeastOnce()).save(Mockito.eq(new Group(name, description)));
    }

    @Test
    void shouldThrowExceptionWhenNameContainsIllegalCharacters() {
        var repositoryMock =  Mockito.mock(GroupRepository.class);
        var commandHandler = new CreateGroupHandler(repositoryMock);
        List<String> invalidNames = List.of("", "   ", "&", "%", ".", ",", "test 123", " test", "a".repeat(101));
        for (var name : invalidNames) {
            String description = "Some funny description";
            var command = new CreateGroupCommand(name, description);
            Exception exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> commandHandler.handle(command)
            );
            Assertions.assertEquals("Invalid format of a group name", exception.getMessage());
        }
    }

    @Test
    void shouldThrowDuplicateExceptionIfThereIsAGroupAlready() {
        String name = "validTestName";
        String description = "Some funny description";
        var command = new CreateGroupCommand(name, description);

        var repositoryMock =  Mockito.mock(GroupRepository.class);
        Mockito.when(repositoryMock.findOneByName(name)).thenReturn(Optional.of(new Group(name, description)));

        Exception exception = Assertions.assertThrows(
                DuplicateException.class,
                () -> new CreateGroupHandler(repositoryMock).handle(command)
        );

        Assertions.assertEquals("Group with this name already exists", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsToLong() {
        String name = "validTestName";
        String description = "a".repeat(201);
        var command = new CreateGroupCommand(name, description);

        var repositoryMock =  Mockito.mock(GroupRepository.class);
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CreateGroupHandler(repositoryMock).handle(command)
        );

        Assertions.assertEquals("Description is too long", exception.getMessage());
    }
}
