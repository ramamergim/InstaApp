package com.mergimrama.instaapp.model;

import org.json.JSONObject;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class AddPost {
    boolean success;

    public AddPost(JSONObject jsonObject) {
        success = jsonObject.optBoolean("success");
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "AddPost{" +
                "success=" + success +
                '}';
    }
}
