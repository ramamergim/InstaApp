package com.mergimrama.instaapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;
import com.mergimrama.instaapp.retrofit.model.RegisterSerializer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mergim on 16-Dec-17.
 */

public class RegisterAccountActivity extends AppCompatActivity {
    private static final String TAG = RegisterAccountActivity.class.getSimpleName();
    private ImageView addPicture;
    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Call<RegisterSerializer> mUserSerializerCall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account);

        addPicture = findViewById(R.id.add_picture_register);
        editTextFullName = findViewById(R.id.register_fullname);
        editTextEmail = findViewById(R.id.register_username);
        editTextPassword = findViewById(R.id.register_password);
        Button buttonSignUp = findViewById(R.id.register_button_signup);

        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent choosePic = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(choosePic, 1);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] nameAndSurname = editTextFullName.getText().toString().split(" ");

                String username = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String name = nameAndSurname[0];
                String surname = "";
                if (nameAndSurname.length > 1) {
                    surname = nameAndSurname[1];
                }
                attemptRegistration(username, password, name, surname);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelCall(mUserSerializerCall);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelCall(mUserSerializerCall);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    addPicture.setImageURI(selectedImage);
                }
                break;
            }
            case 1: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    addPicture.setImageURI(selectedImage);
                }
            }
        }
    }

    private void attemptRegistration(String username, String password, String name, String surname) {
        mUserSerializerCall = RetrofitCaller.call(APIEndpoints.class).register(username,
                password, name, surname);
        mUserSerializerCall.enqueue(new Callback<RegisterSerializer>() {
            @Override
            public void onResponse(@NonNull Call<RegisterSerializer> call, @NonNull Response<RegisterSerializer> response) {
                if (response.isSuccessful()) {
                    RegisterSerializer registerSerializer = response.body();
                    if (registerSerializer != null) {
                        if (!registerSerializer.getStatus().equals("fail")) {
                            Toast.makeText(RegisterAccountActivity.this,
                                    getResources().getString(R.string.account_created), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterAccountActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterAccountActivity.this, registerSerializer.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterSerializer> call, @NonNull Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void cancelCall(Call<?> call) {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }
}
