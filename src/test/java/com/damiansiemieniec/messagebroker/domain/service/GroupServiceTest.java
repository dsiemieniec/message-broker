package com.damiansiemieniec.messagebroker.domain.service;

import com.damiansiemieniec.messagebroker.domain.entity.Group;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.repository.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class GroupServiceTest {
    @Test
    void shouldCreateGroup() throws DuplicateException {
        String name = "validTestName";
        String description = "Some funny description";

        var repositoryMock =  Mockito.mock(GroupRepository.class);
        Mockito.when(repositoryMock.findOneByName(name)).thenReturn(Optional.empty());

        new GroupService(repositoryMock).create(name, description);
        Mockito.verify(repositoryMock, Mockito.atLeastOnce()).findOneByName(Mockito.eq(name));
        Mockito.verify(repositoryMock, Mockito.atLeastOnce()).save(Mockito.eq(new Group(name, description)));
    }

    @Test
    void shouldThrowExceptionWhenNameContainsIllegalCharacters() {
        var repositoryMock =  Mockito.mock(GroupRepository.class);
        var groupService = new GroupService(repositoryMock);
        List<String> invalidNames = List.of("", "   ", "&", "%", ".", ",", "test 123", " test", "a".repeat(101));
        for (var name : invalidNames) {
            String description = "Some funny description";
            Exception exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> groupService.create(name, description)
            );
            Assertions.assertEquals("Invalid format of a group name", exception.getMessage());
        }
    }

    @Test
    void shouldThrowDuplicateExceptionIfThereIsAGroupAlready() {
        String name = "validTestName";
        String description = "Some funny description";

        var repositoryMock =  Mockito.mock(GroupRepository.class);
        Mockito.when(repositoryMock.findOneByName(name)).thenReturn(Optional.of(new Group(name, description)));

        Exception exception = Assertions.assertThrows(
                DuplicateException.class,
                () -> new GroupService(repositoryMock).create(name, description)
        );

        Assertions.assertEquals("Group with this name already exists", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsToLong() {
        String name = "validTestName";
        String description = "a".repeat(201);

        var repositoryMock =  Mockito.mock(GroupRepository.class);
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new GroupService(repositoryMock).create(name, description)
        );

        Assertions.assertEquals("Description is too long", exception.getMessage());
    }
}
