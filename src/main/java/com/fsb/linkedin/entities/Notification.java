package com.fsb.linkedin.entities;

import java.time.LocalDate;
import java.util.Arrays;

public class Notification {
    private byte[] profilePicture;
    private String firstName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    private int post_id;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
    private LocalDate date;
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

    public Notification(String type, int source_id, int destination_id) {
        this.type = type;
        this.source_id = source_id;
        this.destination_id = destination_id;
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

    public Notification(String type, int source_id, int destination_id, int post_id, String message) {
        this.post_id = post_id;
        this.type = type;
        this.source_id = source_id;
        this.destination_id = destination_id;
        this.message = message;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDate() {
        return this.date.toString();
    }

    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String toString() {
        return "Notification{" +
                ", firstName='" + firstName + '\'' +
                ", post_id=" + post_id +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", source_id=" + source_id +
                ", destination_id=" + destination_id +
                '}';
    }
}
