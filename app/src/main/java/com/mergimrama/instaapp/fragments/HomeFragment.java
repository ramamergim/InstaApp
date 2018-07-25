package com.mergimrama.instaapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mergimrama.instaapp.AppPreferences;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.activity.ListAdapter;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;
import com.mergimrama.instaapp.retrofit.model.PostSerializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mergim on 07-Feb-18.
 */

public class HomeFragment extends Fragment {

    private Call<PostSerializer> mPostSerializerCall;
    private ListAdapter mListAdapter;
    private String mUserId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        ListView listView = rootView.findViewById(R.id.list_view);
        mListAdapter = new ListAdapter(getLayoutInflater());
        listView.setAdapter(mListAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserId = AppPreferences.getUser().getUserId();
        getPosts(Integer.parseInt(mUserId));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelCall(mPostSerializerCall);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelCall(mPostSerializerCall);
    }

    private void getPosts(Integer userId) {
        mPostSerializerCall = RetrofitCaller.call(APIEndpoints.class).getPosts(userId);
        mPostSerializerCall.enqueue(new Callback<PostSerializer>() {
            @Override
            public void onResponse(@NonNull Call<PostSerializer> call, @NonNull Response<PostSerializer> response) {
                if (response.isSuccessful()) {
                    PostSerializer postSerializer = response.body();
                    if (postSerializer != null) {
                        List<PostSerializer.Post> posts = postSerializer.getPostList();
                        mListAdapter.setPosts(posts);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostSerializer> call, @NonNull Throwable t) {

            }
        });
    }

    private void cancelCall(Call<?> call) {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }
}
