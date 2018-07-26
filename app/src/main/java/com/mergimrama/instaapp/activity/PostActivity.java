package com.mergimrama.instaapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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

import com.mergimrama.instaapp.AppPreferences;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.model.UploadImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mergim on 17-Dec-17.
 */

public class PostActivity extends AppCompatActivity {
    private static final String TAG = PostActivity.class.getSimpleName();
    private ImageView mImageView;
    private EditText descriptionEditText;
    private View mProgressView;
    private View mMainConstraintLayout;
    private String imageUrlPath = "";
    private Call<UploadImage> mUploadImageCall;
    private Call<ResponseBody> mPostImageToFeedCall;
    private boolean isImageAdded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImageView = findViewById(R.id.post_image_to_feed);
        Button buttonPostToFeed = findViewById(R.id.post_button_to_feed);
        descriptionEditText = findViewById(R.id.description_edit_text);
        mProgressView = findViewById(R.id.progress_bar);
        mMainConstraintLayout = findViewById(R.id.main_constraint_l);

        mImageView.setOnClickListener(new View.OnClickListener() {
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
                if (isImageAdded) {
                    String description = descriptionEditText.getText().toString();
                    postImageToFeed(Integer.parseInt(AppPreferences.getUser().getUserId()),
                            imageUrlPath, description);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.add_image_first, Toast.LENGTH_SHORT).show();
                }
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
                    mImageView.setImageURI(selectedImage);
                }
                break;
            }
            case 1: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    if (selectedImage != null) {
                        mImageView.setImageURI(selectedImage);
                        System.out.println("path " + selectedImage.getPath());
                        File file = new File(getImagePathFromInputStreamUri(selectedImage));
                        uploadImageToServer(file);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.couldnt_get_image, Toast.LENGTH_SHORT).show();
                    }
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
                    if (inputStream != null) {
                        inputStream.close();
                    }
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

    private void uploadImageToServer(File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
/*        // add another part within the multipart request
        RequestBody fullName =
                RequestBody.create(MediaType.parse("multipart/form-data"), "image");*/
        mUploadImageCall = RetrofitCaller.call(APIEndpoints.class).uploadImage(body);
        mUploadImageCall.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(@NonNull Call<UploadImage> call, @NonNull Response<UploadImage> response) {
                if (response.isSuccessful()) {
                    UploadImage uploadImage = response.body();
                    if (uploadImage != null) {
                        if (uploadImage.isSuccess()) {
                            imageUrlPath = uploadImage.getName();
                            isImageAdded = true;
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.couldnt_upload,
                                    Toast.LENGTH_SHORT).show();
                            isImageAdded = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadImage> call, @NonNull Throwable t) {
                Log.d(TAG, t.getMessage());
                isImageAdded = false;
            }
        });
    }

    private void postImageToFeed(Integer userId, String imagePath, String description) {
        showProgress(true);
        mPostImageToFeedCall = RetrofitCaller.call(APIEndpoints.class).createPost(userId,
                imagePath, description);
        mPostImageToFeedCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostActivity.this, R.string.image_added_succes, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostActivity.this, MainActivity.class);
                    startActivity(intent);
                    showProgress(false);
                } else {
                    Toast.makeText(PostActivity.this, R.string.image_added_failure, Toast.LENGTH_SHORT).show();
                    showProgress(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d(TAG, t.getMessage());
                showProgress(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelCall(mUploadImageCall);
        cancelCall(mPostImageToFeedCall);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelCall(mUploadImageCall);
        cancelCall(mPostImageToFeedCall);
    }

    private void cancelCall(Call<?> call) {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mMainConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        mMainConstraintLayout.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mMainConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
