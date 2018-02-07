package com.mergimrama.instaapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.UIActivity.ListAdapter;

/**
 * Created by Mergim on 07-Feb-18.
 */

public class HomeFragment extends Fragment {
    private ListView listView;
    private ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view);
        listAdapter = new ListAdapter(getLayoutInflater());
        listView.setAdapter(listAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
