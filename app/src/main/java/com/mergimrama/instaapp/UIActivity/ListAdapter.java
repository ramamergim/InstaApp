package com.mergimrama.instaapp.UIActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mergimrama.instaapp.PostsAsyncTask;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.callbacks.PostsCallback;
import com.mergimrama.instaapp.model.Posts;

import java.util.ArrayList;

/**
 * Created by Mergim on 16-Dec-17.
 */

public class ListAdapter extends BaseAdapter implements PostsCallback {

    LayoutInflater inflater;
    ViewHolder viewHolder;
    ArrayList<Posts> posts = new ArrayList<Posts>();

    public ListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;

        String url = "http://appsix.net/paintbook/index.php?GetPostet=&UserID=" + MainActivity.userId;
        System.out.println(url);
        new PostsAsyncTask(this).execute(url);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_view, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String username = posts.get(position).getUsername();
        String createdDate = posts.get(position).getCreatedDate();
        String photoUrl = posts.get(position).getPhotoUrl();
        String description = posts.get(position).getPershkrimi();
        try {
            Glide.with(view).load(photoUrl).into(viewHolder.postImageView);
        } catch (Exception e) {

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

    @Override
    public void onPostsResponse(ArrayList<Posts> posts, boolean success) {
        this.posts = posts;
        notifyDataSetChanged();
        for (Posts post: posts) {
            System.out.println(post.toString());
        }
    }

    class ViewHolder {
        ImageView profileImageView;
        TextView nameTextView;
        TextView surnameTextView;
        TextView postTimeTextView;
        ImageView postImageView;
        TextView descriptionTextView;

        public ViewHolder(View v) {
            profileImageView = (ImageView) v.findViewById(R.id.profile_image_feed);
            nameTextView = (TextView) v.findViewById(R.id.name_feed_text_view);
            surnameTextView = (TextView) v.findViewById(R.id.surname_feed_text_view);
            postTimeTextView = (TextView) v.findViewById(R.id.time_feed_text_view);
            postImageView = (ImageView) v.findViewById(R.id.post_image_view_feed);
            descriptionTextView = (TextView) v.findViewById(R.id.description_text_view);
        }
    }
}
