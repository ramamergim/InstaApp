package com.mergimrama.instaapp;

import android.os.AsyncTask;

import com.mergimrama.instaapp.callbacks.LoginCallback;
import com.mergimrama.instaapp.callbacks.RegisterCallback;
import com.mergimrama.instaapp.model.LoginResponse;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Mergim on 18-Dec-17.
 */

public class LoginAsyncTask extends AsyncTask<String, String, String> {
    LoginCallback loginCallback;
    public LoginAsyncTask(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
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
            LoginResponse loginResponse = new LoginResponse(s);
            boolean status = loginResponse.getUser().getStatus().equals("success");
            loginCallback.onLoginResponse(loginResponse.getUser(), status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
