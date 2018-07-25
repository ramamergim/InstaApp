package com.mergimrama.instaapp.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostSerializer {
    @SerializedName("postet")
    private List<Post> mPostList;

    public List<Post> getPostList() {
        return mPostList;
    }

    public class Post {
        @SerializedName("id")
        String id;
        @SerializedName("user_id")
        String userId;
        @SerializedName("username")
        String username;
        @SerializedName("photo_url")
        String photoUrl;
        @SerializedName("pershkrimi")
        String pershkrimi;
        @SerializedName("created_date")
        String createdDate;

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
    }
}
