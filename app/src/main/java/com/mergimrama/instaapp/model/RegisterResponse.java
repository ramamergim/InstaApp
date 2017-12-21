package com.mergimrama.instaapp.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mergim on 17-Dec-17.
 */

public class RegisterResponse {
    String status;
    String message;
    User user;

    public RegisterResponse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        System.out.println(jsonObject.toString());
        status = jsonObject.optString("status");
        message = jsonObject.optString("message");
        if (message.equals("")) {
            user = new User(jsonObject.optJSONObject("user"));
        } else {
        }
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
