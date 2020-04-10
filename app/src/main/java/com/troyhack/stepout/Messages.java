package com.troyhack.stepout;

import android.os.Messenger;

public class Messages {
    private String from;
    private String message;
    private String type;
    private String date;
    private String name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public Messages(){}

    public Messages(String from, String message, String type, String date, String name, String time) {
        this.from = from;
        this.message = message;
        this.type = type;
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
