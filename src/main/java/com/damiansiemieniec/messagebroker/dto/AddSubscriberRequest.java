package com.damiansiemieniec.messagebroker.dto;

public class AddSubscriberRequest {
    private String topic;
    private String url;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
