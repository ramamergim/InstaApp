package com.mergimrama.instaapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.mergimrama.instaapp.model.User;

/**
 * Created by Mergim on 20-Dec-17.
 */

public class AppPreferences {
    public static final String SHARED_PREFS = "mySharedPref";
    public static final String USER_ID = "userId";
    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static void saveUserId (String userId) {
        prefs.edit().putString(USER_ID, userId).commit();
    }

    public static String getUserId() {
        return prefs.getString(USER_ID, "");
    }
}
