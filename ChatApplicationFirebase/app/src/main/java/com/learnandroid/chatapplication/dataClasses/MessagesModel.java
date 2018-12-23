package com.learnandroid.chatapplication.dataClasses;

public class MessagesModel {
    String value;
    String from;

    public MessagesModel(String value, String from) {
        this.value = value;
        this.from = from;
    }

    public String getValue() {
        return value;
    }

    public String getFrom() {
        return from;
    }

}
