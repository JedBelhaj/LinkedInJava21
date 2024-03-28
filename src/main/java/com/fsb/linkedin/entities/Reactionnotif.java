package com.fsb.linkedin.entities;

public class Reactionnotif {
    private Account account;
    private String date;

    public Reactionnotif() {
    }

    public Reactionnotif(Account account, String date) {
        this.account = account;
        this.date = date;
    }

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
}
