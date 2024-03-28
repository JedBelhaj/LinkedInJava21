package com.fsb.linkedin.entities;

public class Friendrequest {
    private Account account;
    private String date;
    @Override
    public String toString() {
        return "Reactionnotif{" +
                "account=" + account +
                ", date='" + date + '\'' +
                '}';
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

    public Friendrequest() {
    }

    public Friendrequest(Account account, String date) {
        this.account = account;
        this.date = date;
    }
}
