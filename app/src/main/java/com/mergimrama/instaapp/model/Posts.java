package com.mergimrama.instaapp.model;

import org.json.JSONObject;

/**
 * Created by Mergim on 19-Dec-17.
 */

public class Posts {
    String id;
    String userId;
    String username;
    String photoUrl;
    String pershkrimi;
    String createdDate;

    public Posts(JSONObject jsonObject) {
        id = jsonObject.optString("id");
        userId = jsonObject.optString("user_id");
        username = jsonObject.optString("username");
        photoUrl = jsonObject.optString("photo_url");
        pershkrimi = jsonObject.optString("pershkrimi");
        createdDate = jsonObject.optString("created_date");
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", pershkrimi='" + pershkrimi + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
