package com.mergimrama.instaapp.retrofit;

import com.mergimrama.instaapp.retrofit.model.PostSerializer;
import com.mergimrama.instaapp.retrofit.model.RegisterSerializer;
import com.mergimrama.instaapp.retrofit.model.UploadImage;
import com.mergimrama.instaapp.retrofit.model.UserSerializer;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIEndpoints {
    @GET("?")
    Call<UserSerializer> login(@Query("User") String user, @Query("Password") String password);
    @GET("?RegisterUser=")
    Call<RegisterSerializer> register(@Query("User") String user,
                                      @Query("password") String password,
                                      @Query("Emri") String name,
                                      @Query("Mbiemri") String surname);
    @GET("?ForgotPassword=")
    Call<ResponseBody> forgotPassword(@Query("User") String user);
    @GET("?GetPostet=")
    Call<PostSerializer> getPosts(@Query("UserID") Integer userId);

    @FormUrlEncoded
    @POST("?new_post")
    Call<ResponseBody> createPost(@Field("user_id") Integer userID,
                                  @Field("image_path") String imagePath,
                                  @Field("pershkrimi") String description);
    @Multipart
    @POST("./")
    Call<UploadImage> uploadImage(@Part MultipartBody.Part file);
}
