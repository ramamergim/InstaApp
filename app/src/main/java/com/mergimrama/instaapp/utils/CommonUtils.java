package com.mergimrama.instaapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mergimrama.instaapp.user.model.User;


public class CommonUtils {

    private static final String TAG = CommonUtils.class.getSimpleName();
    public static String SHARED_PREFERENCES_KEY = "INSTAAPP1551";

    public static User USER;

    public static class ReusableMethods {
        public static void loadOrSaveSharedPreferences(Context context, User obj, boolean save) {
            String userObj = new Gson().toJson(obj);
            SharedPreferences sharedPreferences = context
                    .getSharedPreferences(CommonUtils.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            try {
                if (save) {
                    SharedPreferences.Editor editPref = sharedPreferences.edit();
                    editPref.putString(SHARED_PREFERENCES_KEY, userObj);
                    editPref.apply();
                } else {
                    String userSession = sharedPreferences.getString(SHARED_PREFERENCES_KEY, "");
                    if (!userSession.equals(""))
                        USER = new Gson().fromJson(userSession, User.class);
                }

            } catch (Exception e) {
                Log.e(TAG, "loadOrSaveSharedPreferences: ", e);
            }
        }
    }
}
