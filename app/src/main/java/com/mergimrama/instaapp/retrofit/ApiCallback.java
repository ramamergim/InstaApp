package com.mergimrama.instaapp.retrofit;

import android.support.annotation.NonNull;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    private static final String TAG = "ApiCallback";

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (!response.isSuccessful()) {
            handleError(response);
            return;
        }
        handleResponseData(response.body());
    }

    protected abstract void handleResponseData(T data);

    protected abstract void handleError(Response<T> response);

    protected abstract void handleException(Throwable t);

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        handleException(t);
    }
}
