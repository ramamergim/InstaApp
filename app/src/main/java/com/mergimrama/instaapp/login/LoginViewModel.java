package com.mergimrama.instaapp.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.main.model.UserSerializer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "LoginViewModel";
    private UserRepository mUserRepository;

    @Inject
    public LoginViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository();
    }

    boolean isEmailAndPasswordValid(String email, String password, LoginView listener) {
        if (TextUtils.isEmpty(email)) {
            listener.onUsernameError();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordError();
            return false;
        }
        if (!isPasswordValid(password)) {
            listener.onPasswordError(R.string.error_invalid_password);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public LiveData<UserSerializer> login(String username, String password) {
        return mUserRepository.login(username, getMD5EncryptedString(password));
    }

    private String getMD5EncryptedString(String encTarget) {
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
            String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm

        return "";
    }
}
