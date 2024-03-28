package com.fsb.linkedin.entities;

public class Account {
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String name;
    private byte[] profileImg;
    private boolean isVerified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}
