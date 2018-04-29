package com.mergimrama.instaapp.service;

/**
 * Created by Mergim on 17-Dec-17.
 */

import com.mergimrama.instaapp.AppPreferences;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {
    public static OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    public static String uploadImage(File image, String url) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", image.getName(), RequestBody.create(MEDIA_TYPE_PNG, image))
                .build();

        Request request = new Request.Builder().url(url)
                .post(requestBody).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postImage(String BASE_URLandRoute, String imagePath, String pershkrimi) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", AppPreferences.getUser().getUserId())
                .addFormDataPart("image_path", imagePath)
                .addFormDataPart("pershkrimi", pershkrimi)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URLandRoute)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
