package com.mergimrama.instaapp.main.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
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
import com.mergimrama.instaapp.main.adapters.ListAdapter;
import com.mergimrama.instaapp.retrofit.APIEndpoints;
import com.mergimrama.instaapp.retrofit.RetrofitCaller;
import com.mergimrama.instaapp.main.model.PostSerializer;

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
    private View mProgressView;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        mListView = rootView.findViewById(R.id.list_view);
        mProgressView = rootView.findViewById(R.id.progress_bar);
        mListAdapter = new ListAdapter(getLayoutInflater());
        mListView.setAdapter(mListAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String userId = AppPreferences.getUser().getUserId();
        getPosts(Integer.parseInt(userId));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelCall(mPostSerializerCall);
    }

    private void getPosts(Integer userId) {
        showProgress(true);
        mPostSerializerCall = RetrofitCaller.call(APIEndpoints.class).getPosts(userId);
        mPostSerializerCall.enqueue(new Callback<PostSerializer>() {
            @Override
            public void onResponse(@NonNull Call<PostSerializer> call, @NonNull Response<PostSerializer> response) {
                if (response.isSuccessful()) {
                    PostSerializer postSerializer = response.body();
                    if (postSerializer != null) {
                        List<PostSerializer.Post> posts = postSerializer.getPostList();
                        mListAdapter.setPosts(posts);
                        showProgress(false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostSerializer> call, @NonNull Throwable t) {
                showProgress(false);
            }
        });
    }

    private void cancelCall(Call<?> call) {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mListView.setVisibility(show ? View.GONE : View.VISIBLE);
        mListView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mListView.setVisibility(show ? View.GONE : View.VISIBLE);
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
