package com.example.colorpicker.ui.common;

public class Posts {
    public String date;
    public String imageUrl;
    public String time;
    public String uid;
    public String username;

    public Posts(){ }

    public Posts(String date, String imageUrl, String time, String uid, String username) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.time = time;
        this.uid = uid;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
