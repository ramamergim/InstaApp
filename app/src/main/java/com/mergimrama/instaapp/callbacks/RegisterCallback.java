package com.mergimrama.instaapp.callbacks;

import com.mergimrama.instaapp.model.User;

/**
 * Created by Mergim on 17-Dec-17.
 */

public interface RegisterCallback {
    public void onRegisterResponse(User user, boolean success, String message);
}
