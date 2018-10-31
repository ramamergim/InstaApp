package com.mergimrama.instaapp.login;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.mergimrama.instaapp.retrofit.DataWrapper;

public class ApiObserver<T> implements Observer<DataWrapper<T>> {
    private ChangeListener<T> mChangeListener;

    public ApiObserver(ChangeListener<T> changeListener) {
        mChangeListener = changeListener;
    }

    @Override
    public void onChanged(@Nullable DataWrapper<T> tDataWrapper) {
        if (tDataWrapper == null) {
//            mChangeListener.onException();
            return;
        }
        if (tDataWrapper.getApiThrowable() == null) {
            mChangeListener.onSuccess(tDataWrapper.getData());
        } else {
            mChangeListener.onException(tDataWrapper.getApiThrowable());
        }
    }

    public interface ChangeListener<T> {
        void onSuccess(T dataWrapper);
        void onException(Throwable throwable);
    }
}
