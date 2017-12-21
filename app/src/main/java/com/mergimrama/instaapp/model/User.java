package com.mergimrama.instaapp.model;

/**
 * Created by Mergim on 17-Dec-17.
 */
import org.json.JSONObject;

public class User {
    String userId;
    String emri;
    String username;
    String status;

    public User(JSONObject jsonObject) {
        userId = jsonObject.optString("UserID");
        emri = jsonObject.optString("Emri");
        username = jsonObject.optString("Username");
        status = jsonObject.optString("status");
    }

    public String getUserId() {
        return userId;
    }

    public String getEmri() {
        return emri;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", emri='" + emri + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

