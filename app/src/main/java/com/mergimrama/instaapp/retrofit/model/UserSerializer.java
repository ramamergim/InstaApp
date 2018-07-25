package com.mergimrama.instaapp.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserSerializer {
    @SerializedName("User")
    private List<User> mUserList;

    public List<User> getUserList() {
        return mUserList;
    }

    public class User {
        @SerializedName("status")
        private String mStatus;
        @SerializedName("UserID")
        private String mUserId;
        @SerializedName("Emri")
        private String mName;
        @SerializedName("Username")
        private String mUsername;

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
}
