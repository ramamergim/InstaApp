package com.mergimrama.instaapp.service;

import android.os.AsyncTask;

import com.mergimrama.instaapp.callbacks.RegisterCallback;

import org.json.JSONException;

import java.io.IOException;
//[ - array
//{ - object
/**
 * Created by Mergim on 17-Dec-17.
 */

public class RegisterAsyncTask extends AsyncTask<String, String, String> {
    RegisterCallback registerCallback;

    public RegisterAsyncTask(RegisterCallback registerCallback) {
        this.registerCallback = registerCallback;
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
            RegisterResponse registerResponse = new RegisterResponse(s);
            registerCallback.onRegisterResponse(registerResponse.getUser(), registerResponse.getMessage().equals(""),
                    registerResponse.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
