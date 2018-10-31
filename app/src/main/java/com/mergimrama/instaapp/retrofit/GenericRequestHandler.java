package com.mergimrama.instaapp.retrofit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Response;

public abstract class GenericRequestHandler<R> {
    protected abstract Call<R> makeRequest();

    public final LiveData<DataWrapper<R>> doRequests() {
        final MutableLiveData<DataWrapper<R>> liveData = new MutableLiveData<>();
        final DataWrapper<R> dataWrapper = new DataWrapper<R>();
        makeRequest().enqueue(new ApiCallback<R>() {
            @Override
            protected void handleResponseData(R data) {
                dataWrapper.setData(data);
                liveData.setValue(dataWrapper);
            }

            @Override
            protected void handleError(Response<R> response) {

            }

            @Override
            protected void handleException(Throwable t) {
                dataWrapper.setThrowable(t);
                liveData.setValue(dataWrapper);
            }
        });
        return liveData;
    }
}
