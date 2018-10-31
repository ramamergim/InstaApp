package com.mergimrama.instaapp.user.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterSerializer {
    @SerializedName("User")
    private List<User> mUserList;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("message")
    private String mMessage;

    public List<User> getUserList() {
        return mUserList;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }
}
