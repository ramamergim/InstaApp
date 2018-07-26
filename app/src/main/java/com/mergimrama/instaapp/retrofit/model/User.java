package com.mergimrama.instaapp.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("status")
    private String mStatus;
    @SerializedName("UserID")
    private String mUserId;
    @SerializedName("Emri")
    private String mName;
    @SerializedName("Username")
    private String mUsername;

    public User(String userId, String name, String username, String status) {
        mUserId = userId;
        mName = name;
        mUsername = username;
        mStatus = status;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }
}