package com.mergimrama.instaapp.service;

import com.mergimrama.instaapp.model.UploadImage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class UploadImageResponse {
    boolean success;
    String name;
    UploadImage uploadImage;

    public UploadImageResponse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        System.out.println(s);
        System.out.println(jsonObject.toString());
        success = jsonObject.optBoolean("success");
        name = jsonObject.optString("name");
        if (success) {
            uploadImage = new UploadImage(jsonObject);
        } else {

        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public UploadImage getUploadImage() {
        return uploadImage;
    }
}
