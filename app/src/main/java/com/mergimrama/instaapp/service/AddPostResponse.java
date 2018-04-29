package com.mergimrama.instaapp.service;

import com.mergimrama.instaapp.model.AddPost;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class AddPostResponse {
    boolean success;
    AddPost addPost;

    public AddPostResponse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        System.out.println(s);
        success = jsonObject.optBoolean("success");

        if (success) {
            addPost = new AddPost(jsonObject);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public AddPost getAddPost() {
        return addPost;
    }
}
