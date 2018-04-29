package com.mergimrama.instaapp.service;

import com.mergimrama.instaapp.model.Posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mergim on 18-Dec-17.
 */

public class PostsResponse {
    ArrayList<Posts> postsArrayList = new ArrayList<Posts>();

    public PostsResponse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        JSONArray params = jsonObject.getJSONArray("postet");
        JSONObject param1 = params.getJSONObject(0);

        for (int i = 0; i < params.length(); i++)
            postsArrayList.add(new Posts(params.getJSONObject(i)));
    }

    public ArrayList<Posts> getPostsArrayList() {
        return postsArrayList;
    }
}
