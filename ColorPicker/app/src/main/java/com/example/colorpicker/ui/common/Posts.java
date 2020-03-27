package com.example.colorpicker.ui.common;

/**
 * @author Alex Valente
 */
public class Posts {
    public String date;
    public String imageUrl;
    public String time;
    public String uid;
    public String username;

    public Posts(){ }

    /**
     * Constructor for the Posts class
     * @param date Date value corresponding to the date when the post was made
     * @param imageUrl String corresponding to a Url with a reference to the FireBase Storage
     * @param time String corresponding to the time when the post was made
     * @param uid String with and ID corresponding to the respective user
     * @param username String with the username of the user
     * @see com.google.firebase.storage.FirebaseStorage
     */
    public Posts(String date, String imageUrl, String time, String uid, String username) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.time = time;
        this.uid = uid;
        this.username = username;
    }

    /**
     * Getter method for the Date variable
     * @return String representing a date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter method for the Date variable
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter method for the imageUrl variable
     * @return String representing a FireBase Url.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter method for the imageUrl variable
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Getter method for the time variable
     * @return String representing a time.
     */
    public String getTime() {
        return time;
    }

    /**
     * Setter method for the time variable
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Getter method for the userId variable
     * @return String representing a userId.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Setter method for the userId variable
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Getter method for the username variable
     * @return String representing the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for the username variable
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
