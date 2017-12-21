package com.mergimrama.instaapp;

import android.os.AsyncTask;

import com.mergimrama.instaapp.callbacks.PostsCallback;
import com.mergimrama.instaapp.model.PostsResponse;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Mergim on 18-Dec-17.
 */

public class PostsAsyncTask extends AsyncTask<String, String, String> {
    PostsCallback postsCallback;
    public PostsAsyncTask(PostsCallback postsCallback) {
        this.postsCallback = postsCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            return ApiService.get(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            PostsResponse postsResponse = new PostsResponse(s);
            //boolean status = loginResponse.getUser().getStatus().equals("success");
            postsCallback.onPostsResponse(postsResponse.getPostsArrayList(), true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
