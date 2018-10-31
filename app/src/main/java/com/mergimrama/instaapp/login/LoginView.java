package com.mergimrama.instaapp.login;

public interface LoginView {
    void onUsernameError();

    void onPasswordError();

    void onPasswordError(int messageResourceID);

    void onFailure();

    void onFailure(String failureText);

    void onFailure(int failureResourceTextId);

    void onSuccess();

    void showProgress();

    void hideProgress();
}
