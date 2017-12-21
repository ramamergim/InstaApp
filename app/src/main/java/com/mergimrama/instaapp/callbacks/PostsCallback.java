package com.mergimrama.instaapp.callbacks;

import com.mergimrama.instaapp.model.Posts;
import com.mergimrama.instaapp.model.User;

import java.util.ArrayList;

/**
 * Created by Mergim on 18-Dec-17.
 */

public interface PostsCallback {
    public void onPostsResponse(ArrayList<Posts> posts, boolean success);
}
