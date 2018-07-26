package com.mergimrama.instaapp.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mergimrama.instaapp.retrofit.model.PostSerializer;
import com.mergimrama.instaapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mergim on 16-Dec-17.
 */

public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PostSerializer.Post> mPosts = new ArrayList<>();

    public ListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_view, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String username = mPosts.get(position).getUsername();
        String createdDate = mPosts.get(position).getCreatedDate();
        String photoUrl = mPosts.get(position).getPhotoUrl();
        String description = mPosts.get(position).getPershkrimi();
        try {
            Glide.with(view).load(photoUrl).into(viewHolder.postImageView);
        } catch (Exception e) {
            Log.d("ListAdapter", "couldn't load image");
        }
        viewHolder.nameTextView.setText(username);
        viewHolder.postTimeTextView.setText(createdDate);
        viewHolder.descriptionTextView.setText(description);

        viewHolder.postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked on post");
            }
        });

        viewHolder.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked on profile pic");
            }
        });

        viewHolder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Name was clicked");
            }
        });

        viewHolder.surnameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Surname was clicked");
            }
        });
        return view;
    }

    public void setPosts(List<PostSerializer.Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView profileImageView;
        TextView nameTextView;
        TextView surnameTextView;
        TextView postTimeTextView;
        ImageView postImageView;
        TextView descriptionTextView;

        ViewHolder(View v) {
            profileImageView = v.findViewById(R.id.profile_image_feed);
            nameTextView = v.findViewById(R.id.name_feed_text_view);
            surnameTextView = v.findViewById(R.id.surname_feed_text_view);
            postTimeTextView = v.findViewById(R.id.time_feed_text_view);
            postImageView = v.findViewById(R.id.post_image_view_feed);
            descriptionTextView = v.findViewById(R.id.description_text_view);
        }
    }
}
