package com.mergimrama.instaapp.callbacks;

import com.mergimrama.instaapp.model.User;

/**
 * Created by Mergim on 18-Dec-17.
 */

public interface LoginCallback {
    public void onLoginResponse(User user, boolean success);
}
