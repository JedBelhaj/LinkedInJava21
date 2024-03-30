package com.fsb.linkedin.entities;

public class Post {
    private int postID;
    private String postType;

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Post(Message m) {
        setCaption(m.getCaption());
    }
    public Post(){}
    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getPostID() {
        return postID;
    }

    private Account account;
    private PostAudience audience;
    private String date;
    private String caption;
    private byte[] image;
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private int totalReactions;

    @Override
    public String toString() {
        return "Post{" +
                "account=" + account +
                ", audience=" + audience +
                ", date='" + date + '\'' +
                ", caption='" + caption + '\'' +
                ", image='" + image + '\'' +
                ", totalReactions=" + totalReactions +
                ", nbComments=" + nbComments +
                ", nbShares=" + nbShares +
                '}';
    }

    private int nbComments;
    private int nbShares;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PostAudience getAudience() {
        return audience;
    }

    public void setAudience(PostAudience audience) {
        this.audience = audience;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }



    public int getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }

    public int getNbComments() {
        return nbComments;
    }

    public void setNbComments(int nbComments) {
        this.nbComments = nbComments;
    }

    public int getNbShares() {
        return nbShares;
    }

    public void setNbShares(int nbShares) {
        this.nbShares = nbShares;
    }
}
