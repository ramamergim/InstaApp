package com.mergimrama.instaapp.login;

import android.arch.lifecycle.LiveData;

import com.mergimrama.instaapp.main.model.UserSerializer;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.DataWrapper;
import com.mergimrama.instaapp.retrofit.GenericRequestHandler;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;

import retrofit2.Call;

public class LoginInteract extends GenericRequestHandler<UserSerializer> {
    private APIEndpoints mAPIEndpoints = RetrofitCaller.call(APIEndpoints.class);
    private String mUsername, mPassword;

    public static LoginInteract createInstance(String username, String password) {
        LoginInteract loginInteract = new LoginInteract();
        loginInteract.mUsername = username;
        loginInteract.mPassword = password;
        return loginInteract;
    }

    public LiveData<DataWrapper<UserSerializer>> onAuthRequest() {
        return doRequests();
    }

    @Override
    protected Call<UserSerializer> makeRequest() {
        return mAPIEndpoints.login("", "");
    }
}
