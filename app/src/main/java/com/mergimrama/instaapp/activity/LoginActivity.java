package com.mergimrama.instaapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mergimrama.instaapp.AppPreferences;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;
import com.mergimrama.instaapp.retrofit.model.UserSerializer;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.service.PublicData;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mergim on 15-Dec-17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Call<UserSerializer> mUserSerializerCall;

    private View mProgressView;
    private View mLoginView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUsername = findViewById(R.id.login_username);
        editTextPassword = findViewById(R.id.login_password);
        mProgressView = findViewById(R.id.login_progress);
        mLoginView = findViewById(R.id.login_form);
        Button buttonLogin = findViewById(R.id.button_login);
        TextView textViewSignUp = findViewById(R.id.sign_up_login);

        AppPreferences.init(getApplicationContext());

        PublicData.ReusableMethods.loadOrSaveSharedPreferences(getApplicationContext(), null, false);
        if (PublicData.USER != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            this.finish();
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTextUsername.getText().toString();
                String password = getMD5EncryptedString(editTextPassword.getText().toString());

                attemptLogin(username, password);
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelCall(mUserSerializerCall);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelCall(mUserSerializerCall);
    }

    public static String getMD5EncryptedString(String encTarget) {
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }

    private void cancelCall(Call<?> call) {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }

    private void attemptLogin(String username, String password) {
        showProgress(true);
        mUserSerializerCall = RetrofitCaller.call(APIEndpoints.class).login(username, password);
        mUserSerializerCall.enqueue(new Callback<UserSerializer>() {
            @Override
            public void onResponse(@NonNull Call<UserSerializer> call, @NonNull Response<UserSerializer> response) {
                if (response.isSuccessful()) {
                    UserSerializer userSerializer = response.body();
                    if (userSerializer != null) {
                        UserSerializer.User user = userSerializer.getUserList().get(0);
                        if (user.getStatus().equals("success")) {
                            AppPreferences.saveUserDetails(user.getUserId(), user.getName(), user.getUsername(), user.getStatus());
                            PublicData.ReusableMethods.loadOrSaveSharedPreferences(getApplicationContext(), user, true);
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            showProgress(false);
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    }
                } else {
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSerializer> call, @NonNull Throwable t) {
                Log.d("LoginActivity", "Request failed: " + t.getMessage());
                showProgress(false);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
