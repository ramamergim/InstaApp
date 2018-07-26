package com.mergimrama.instaapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.mergimrama.instaapp.model.User;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class AppPreferences {
    public static final String SHARED_PREFS = "mySharedPref";

    public static final String USER_ID = "com.mergimrama.instaapp.user_id";
    public static final String NAME = "com.mergimrama.instaapp.name";
    public static final String USERNAME = "com.mergimrama.instaapp.username";
    public static final String STATUS = "com.mergimrama.instaapp.status";

    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    /*public static void saveUserId (String userId) {
        prefs.edit().putString(USER_ID, userId).commit();
    }*/

    public static void saveUserDetails (String userId, String name, String username, String status) {
        prefs.edit().putString(USER_ID, userId).commit();
        prefs.edit().putString(NAME, name).commit();
        prefs.edit().putString(USERNAME, username).commit();
        prefs.edit().putString(STATUS, status).commit();
    }

    /*public static String getUserId() {
        return prefs.getString(USER_ID, "");
    }*/

    public static User getUser() {
        String userId = prefs.getString(USER_ID, "");
        String name = prefs.getString(NAME, "");
        String username = prefs.getString(USERNAME, "");
        String status = prefs.getString(STATUS, "");

        return new User(userId, name, username, status);
    }
}
