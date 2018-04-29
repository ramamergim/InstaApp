package com.mergimrama.instaapp.service;

import android.os.AsyncTask;

import com.mergimrama.instaapp.callbacks.UploadImageCallback;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class UploadImageTask  extends AsyncTask<File, String, String> {

    String url;
    UploadImageCallback uploadImageCallback;

    public UploadImageTask(String url, UploadImageCallback uploadImageCallback){
        this.url = url;
        this.uploadImageCallback = uploadImageCallback;
    }

    @Override
    protected String doInBackground(File... files) {
        try {
            return ApiService.uploadImage(files[0], url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(s);
        try {
            UploadImageResponse uploadImageResponse = new UploadImageResponse(s);
            uploadImageCallback.onUploadResponse(uploadImageResponse.getUploadImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
