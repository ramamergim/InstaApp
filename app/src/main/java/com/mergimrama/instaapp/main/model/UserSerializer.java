package com.mergimrama.instaapp.main.model;

import com.google.gson.annotations.SerializedName;
import com.mergimrama.instaapp.user.model.User;

import java.util.Collections;
import java.util.List;

public class UserSerializer {
    @SerializedName("User")
    private List<User> mUserList;

    public List<User> getUserList() {
        return mUserList != null ? mUserList : Collections.<User>emptyList();
    }
}
