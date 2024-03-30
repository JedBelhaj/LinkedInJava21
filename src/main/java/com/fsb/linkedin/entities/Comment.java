package com.fsb.linkedin.entities;

import java.util.Arrays;

public class Comment {

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    private String caption;
    private int parent_ID;

    public int getParent_ID() {
        return parent_ID;
    }

    public void setParent_ID(int parent_ID) {
        this.parent_ID = parent_ID;
    }
    private boolean isReply;

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    private int commentID;
    public Comment(){};
    private Account account;
    private String date;
    private byte[] image;

    public Comment(Account account, String date, byte[] image, String caption) {
        this.account = account;
        this.date = date;
        this.image = image;
    }
    public Comment(Account account, String date, byte[] image, int parent_ID, String caption) {
        this.account = account;
        this.date = date;
        this.image = image;
        this.parent_ID = parent_ID;
    }
    @Override
    public String toString() {
        return "Comment_With_Image{" +
                "commentID=" + commentID +
                ", account=" + account +
                ", date='" + date + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
