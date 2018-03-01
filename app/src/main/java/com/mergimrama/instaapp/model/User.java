package com.mergimrama.instaapp.model;

/**
 * Created by Mergim on 17-Dec-17.
 */
import org.json.JSONObject;

import java.io.Serializable;

public class User {
    private String userId;
    private String name;
    private String username;
    private String status;

    public User(JSONObject jsonObject) {
        userId = jsonObject.optString("UserID");
        name = jsonObject.optString("Emri");
        username = jsonObject.optString("Username");
        status = jsonObject.optString("status");
    }

    public User(String userId, String name, String username, String status) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
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
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

