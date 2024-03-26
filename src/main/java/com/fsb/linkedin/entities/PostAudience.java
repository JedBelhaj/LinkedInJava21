package com.fsb.linkedin.entities;

public enum PostAudience {
    PUBLIC(0,"Public","/imgs/ic_public.png"),
    FRIENDS(1,"Friends","/imgs/ic_friend.png");

    @Override
    public String toString() {
        return "PostAudience{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    private int id;
    private String name;
    private String imgSrc;

    PostAudience(int id, String name, String imgSrc) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgSrc() {
        return imgSrc;
    }
    public String getAudienceAsString() {
        return this == PUBLIC ? "public" : "friends";
    }
}

