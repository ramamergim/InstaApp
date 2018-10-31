package com.mergimrama.instaapp.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mergimrama.instaapp.main.model.UserSerializer;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserRepository {
    private static final String TAG = "UserRepository";

    @Inject
    public UserRepository() {
    }

    public LiveData<UserSerializer> login(final String username, String password) {
        final MutableLiveData<UserSerializer> user = new MutableLiveData<>();
        Call<UserSerializer> userSerializerCall = RetrofitCaller.call(APIEndpoints.class).login(username, password);
        userSerializerCall.enqueue(new Callback<UserSerializer>() {
            @Override
            public void onResponse(@NonNull Call<UserSerializer> call, @NonNull Response<UserSerializer> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Response not successful. terminating login");
                    return;
                }
                user.setValue(response.body());

                        /*if (!response.isSuccessful()) {
                            Log.d(TAG, "onResponse: response not successful. Terminating login");
                            loginView.hideProgress();
                            loginView.onFailure(R.string.request_not_successful);
                            return;
                        }
                        UserSerializer userSerializer = response.body();
                        if (userSerializer == null) {
                            Log.d(TAG, "onResponse: userSerializer came null. Terminating login");
                            loginView.hideProgress();
                            loginView.onFailure();
                            return;
                        }
                        List<User> users = userSerializer.getUserList();
                        if (users.isEmpty()) {
                            Log.d(TAG, "onResponse: users came empty. Terminating login.");
                            loginView.hideProgress();
                            loginView.onFailure();
                            return;
                        }
                        User user = users.get(0);
                        if (user.getStatus().equals("success")) {
                            AppPreferences.saveUserDetails(user.getUserId(), user.getName(), user.getUsername(), user.getStatus());
                            loginView.onSuccess();
                        } else {
                            Log.d(TAG, "onResponse: login not successful.");
                            loginView.hideProgress();
                            loginView.onFailure(user.getStatus());
                        }*/
            }

            @Override
            public void onFailure(@NonNull Call<UserSerializer> call, @NonNull Throwable t) {
//                        loginView.hideProgress();
                user.setValue(null);
                Log.d("LoginActivity", "Request failed: " + t.getMessage());
            }
        });
        return user;
    }
}