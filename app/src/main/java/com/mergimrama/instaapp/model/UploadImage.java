package com.mergimrama.instaapp.model;

import com.google.gson.annotations.SerializedName;

public class UploadImage {
    @SerializedName("success")
    private boolean mSuccess;
    @SerializedName("name")
    private String mName;

    public boolean isSuccess() {
        return mSuccess;
    }

    public String getName() {
        return mName;
    }
}
