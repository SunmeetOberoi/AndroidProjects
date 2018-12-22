package com.learnandroid.chatapplication.dataClasses;

public class ContactsModel {
    String name;
    String status;
    String lastMsg;

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
