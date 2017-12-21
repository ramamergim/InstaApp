package com.mergimrama.instaapp;

import android.os.AsyncTask;

import com.mergimrama.instaapp.callbacks.AddPostCallback;
import com.mergimrama.instaapp.model.AddPostResponse;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Mergim on 19-Dec-17.
 */

public class AddPostAsyncTask extends AsyncTask <String, String, String> {
    AddPostCallback addPostCallback;
    String imageUrl;
    String pershkrimi;

    public AddPostAsyncTask(AddPostCallback addPostCallback, String imageUrl, String pershkrimi) {
        this.addPostCallback = addPostCallback;
        this.imageUrl = imageUrl;
        this.pershkrimi = pershkrimi;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            return ApiService.postImage(strings[0], imageUrl, pershkrimi);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            AddPostResponse addPostResponse = new AddPostResponse(s);
            addPostCallback.onAddPostResponse(addPostResponse.getAddPost());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }
}
