package com.mergimrama.instaapp.model;

import org.json.JSONObject;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class UploadImage {
    boolean success;
    String name;

    public UploadImage(JSONObject jsonObject) {
        success = jsonObject.optBoolean("success");
        name = jsonObject.optString("name");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UploadImage{" +
                "success=" + success +
                ", name='" + name + '\'' +
                '}';
    }
}
