package com.mergimrama.instaapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mergimrama.instaapp.AppPreferences;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.UIActivity.MainActivity;
import com.mergimrama.instaapp.callbacks.LoginCallback;
import com.mergimrama.instaapp.model.User;

/**
 * Created by Mergim on 07-Feb-18.
 */

public class ProfileFragment extends Fragment {

    TextView fullNameTextview;
    TextView usernameTextview;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        fullNameTextview = (TextView) view.findViewById(R.id.profile_name_surname);
        usernameTextview = (TextView) view.findViewById(R.id.profile_username);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = MainActivity.user;

        fullNameTextview.setText(user.getEmri());
        usernameTextview.setText(user.getUsername());
    }
}
