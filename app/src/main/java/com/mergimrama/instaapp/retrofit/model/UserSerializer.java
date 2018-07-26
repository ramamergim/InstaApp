package com.mergimrama.instaapp.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserSerializer {
    @SerializedName("User")
    private List<User> mUserList;

    public List<User> getUserList() {
        return mUserList;
    }
}
