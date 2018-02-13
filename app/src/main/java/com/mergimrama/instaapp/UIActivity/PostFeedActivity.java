package com.mergimrama.instaapp.UIActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mergimrama.instaapp.AddPostAsyncTask;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.UploadImageTask;
import com.mergimrama.instaapp.callbacks.AddPostCallback;
import com.mergimrama.instaapp.callbacks.UploadImageCallback;
import com.mergimrama.instaapp.model.AddPost;
import com.mergimrama.instaapp.model.UploadImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mergim on 17-Dec-17.
 */

public class PostFeedActivity extends AppCompatActivity implements UploadImageCallback, AddPostCallback {
    ImageView postImageToFeed;
    Button buttonPostToFeed;
    EditText descriptionEditText;
    String imageUri = "";
    String url = "http://appsix.net/paintbook/index.php";
    String imageUrlPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);

        postImageToFeed = (ImageView) findViewById(R.id.post_image_to_feed);
        buttonPostToFeed = (Button) findViewById(R.id.post_button_to_feed);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);

        postImageToFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent choosePic = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(choosePic, 1);
            }
        });

        buttonPostToFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://appsix.net/paintbook/index.php?new_post";
                String pershkrimi = descriptionEditText.getText().toString();
                new AddPostAsyncTask(PostFeedActivity.this, imageUrlPath, pershkrimi).execute(url);
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
                    postImageToFeed.setImageURI(selectedImage);

                }
                break;
            }
            case 1: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageUri += selectedImage;
                    postImageToFeed.setImageURI(selectedImage);
                    System.out.println("path " + selectedImage.getPath());

                    File file = new File(getImagePathFromInputStreamUri(selectedImage));
                    new UploadImageTask(url, this).execute(file);
                }
            }
        }
    }

    public String getImagePathFromInputStreamUri(Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

    private File createTemporalFileFrom(InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile();
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private File createTemporalFile() {
        return new File(getExternalCacheDir(), "tempFile.jpg"); // context needed
    }

    @Override
    public void onUploadResponse(UploadImage uploadImage) {
        if (uploadImage.isSuccess()) {
            imageUrlPath = uploadImage.getName();
        }
    }

    @Override
    public void onAddPostResponse(AddPost addPost) {
        if (addPost.isSuccess()) {
            Toast.makeText(PostFeedActivity.this, R.string.image_added_succes, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostFeedActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(PostFeedActivity.this, R.string.image_added_failure, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
