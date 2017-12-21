package com.mergimrama.instaapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mergim on 18-Dec-17.
 */

public class LoginResponse {
    User user;

    public LoginResponse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        JSONArray params = jsonObject.getJSONArray("User");
        JSONObject param1 = params.getJSONObject(0);

        user = new User(param1);
    }

    public User getUser() {
        return user;
    }
}
