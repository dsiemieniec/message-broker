package com.damiansiemieniec.messagebroker.domain.command;


public class CreateGroupCommand {
    private final String name;
    private final String description;

    public CreateGroupCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
