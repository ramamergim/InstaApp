package com.mergimrama.instaapp.UIActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.RegisterAsyncTask;
import com.mergimrama.instaapp.callbacks.RegisterCallback;
import com.mergimrama.instaapp.model.User;

/**
 * Created by Mergim on 16-Dec-17.
 */

public class RegisterAccountActivity extends AppCompatActivity implements RegisterCallback {

    ImageView addPicture;
    EditText editTextFullName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account);

        addPicture = (ImageView) findViewById(R.id.add_picture_register);
        editTextFullName = (EditText) findViewById(R.id.register_fullname);
        editTextEmail = (EditText) findViewById(R.id.register_email);
        editTextPassword = (EditText) findViewById(R.id.register_password);
        buttonSignUp = (Button) findViewById(R.id.register_button_signup);

        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);*/
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

                String url = "http://appsix.net/paintbook/index.php?RegisterUser=&User=" + username + "&password=" + password +
                        "&Emri=" + name + "&Mbiemri=" + surname;
                new RegisterAsyncTask(RegisterAccountActivity.this).execute(url);
            }
        });
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

    @Override
    public void onRegisterResponse(User user, boolean success, String message) {
        if(success) {
            Toast.makeText(RegisterAccountActivity.this, "User account created successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterAccountActivity.this, LoginActivity.class);
            startActivity(intent);
            System.out.println("user: " + user.toString());
        } else {
            Toast.makeText(RegisterAccountActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
