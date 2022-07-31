package com.damiansiemieniec.messagebroker.domain.entity;

import org.json.JSONObject;

import java.util.UUID;

public class Event {
    private final UUID id;
    private final String content;

    public Event(String content) {
        this.id =  UUID.randomUUID();
        this.content = content;
    }

    public Event(UUID id, String content) {
        this.id = id;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public JSONObject toJson() {
        var json = new JSONObject();
        json.put("event_id", this.getId().toString());
        json.put("content", this.getContent());

        return json;
    }

    public static Event fromJson(JSONObject json) {
        return new Event(UUID.fromString(json.getString("event_id")), json.getString("content"));
    }
}
