package com.fsb.linkedin.entities;

public class Contact {
    private Account account;

    private String msg;

    private String date;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "account=" + account +
                ", msg='" + msg + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getID() {
        return id;
    }
}
