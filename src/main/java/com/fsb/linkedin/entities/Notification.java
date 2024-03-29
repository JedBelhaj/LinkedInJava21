package com.fsb.linkedin.entities;

public class Notification {
    private int post_id;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    private String message;
    private String type;
    private int source_id;
    private int destination_id;

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public Notification() {
    }

    public Notification(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public Notification(String message, String type, int source_id, int destination_id) {
        this.type = type;
        this.source_id = source_id;
        this.message = message;
        this.destination_id = destination_id;

    }
    public Notification(String message, String type, int source_id, int post_id, int destination_id) {
        this.type = type;
        this.source_id = source_id;
        this.message = message;
        this.post_id = post_id;
        this.destination_id = destination_id;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }
}
