package com.mergimrama.instaapp.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mergimrama.instaapp.AppPreferences;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.main.activities.MainActivity;
import com.mergimrama.instaapp.main.model.UserSerializer;
import com.mergimrama.instaapp.user.model.User;

import java.util.List;

import javax.inject.Inject;

public class LoginActivityVM extends AppCompatActivity {
    private static final String TAG = "LoginActivityVM";

    private EditText editTextUsername;
    private EditText editTextPassword;

    private View mProgressView;
    private View mLoginView;

    private LoginViewModel mLoginViewModel;
    @Inject
    ViewModelProvider.Factory mFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppPreferences.init(getApplicationContext());

        editTextUsername = findViewById(R.id.login_username);
        editTextPassword = findViewById(R.id.login_password);
        mProgressView = findViewById(R.id.login_progress);
        mLoginView = findViewById(R.id.login_form);
        Button buttonLogin = findViewById(R.id.button_login);
        TextView textViewSignUp = findViewById(R.id.sign_up_login);

        mLoginViewModel = ViewModelProviders.of(this,
                mFactory).get(LoginViewModel.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                showProgress(true);
                mLoginViewModel.login(username, password)
                        .observe(LoginActivityVM.this, new Observer<UserSerializer>() {
                            @Override
                            public void onChanged(@Nullable UserSerializer userSerializer) {
                                if (userSerializer == null) {
                                    Log.d(TAG, "onChanged: userSerializer came null");
                                    showProgress(false);
                                    Toast.makeText(LoginActivityVM.this, R.string.request_not_successful, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                List<User> users = userSerializer.getUserList();
                                if (users.isEmpty()) {
                                    Toast.makeText(LoginActivityVM.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: users came empty. Terminating login.");
                                    return;
                                }
                                User user = users.get(0);
                                if (user.getStatus().equals("success")) {
                                    AppPreferences.saveUserDetails(user.getUserId(), user.getName(), user.getUsername(), user.getStatus());
                                    finish();
                                    startActivity(MainActivity.newIntent(getApplicationContext()));
                                } else {
                                    Toast.makeText(LoginActivityVM.this, user.getStatus(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: login not successful.");
                                }
                            }
                        });
            }
        });
    }

    private void showProgress(boolean showProgress) {
        mLoginView.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        mProgressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    private void loginFlow() {

    }
}
