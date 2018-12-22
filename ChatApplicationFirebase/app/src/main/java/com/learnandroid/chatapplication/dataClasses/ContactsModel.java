package com.learnandroid.chatapplication.dataClasses;

public class ContactsModel {
    String name;
    String status;
    String lastMsg;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public ContactsModel(String name, String status, String lastMsg) {
        this.name = name;
        this.status = status;
        this.lastMsg = lastMsg;
    }
}
