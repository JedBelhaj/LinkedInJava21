package com.fsb.linkedin.entities;

import java.util.Arrays;

public class Message {
    int senderID;
    private int convID = -1;

    public int getConvID() {
        return convID;
    }

    public void setConvID(int convID) {
        this.convID = convID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    String caption;
    byte[] attachment;

    public Message(String caption, byte[] attachment) {
        this.caption = caption;
        this.attachment = attachment;
    }

    public Message(String caption) {
        this.caption = caption;
    }

    public Message(){}
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderID=" + senderID +
                ", caption='" + caption + '\'' +
                '}';
    }
}
