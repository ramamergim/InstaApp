package com.mergimrama.instaapp.retrofit;

public class DataWrapper<T> {
    private Throwable mThrowable;
    private T data;

    public Throwable getApiThrowable() {
        return mThrowable;
    }

    public void setThrowable(Throwable throwable) {
        mThrowable = throwable;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
