package com.fsb.linkedin.entities;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


import java.util.Objects;

public class Offer {
    private Account account;
    private PostAudience audience;
    private String date;
    private String caption;
    private String image;
    private int totalReactions;

    @Override
    public String toString() {
        return "Offer{" +
                "account=" + account +
                ", audience=" + audience +
                ", date='" + date + '\'' +
                ", caption='" + caption + '\'' +
                ", image='" + image + '\'' +
                '}';
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
