package com.mergimrama.instaapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mergimrama.instaapp.retrofit.model.UserSerializer;

public class PublicData {

    private static final String TAG = PublicData.class.getSimpleName();
    public static String SHARED_PREFERENCES_KEY = "INSTAAPP1551";

    public static UserSerializer.User USER;

    public static class ReusableMethods {
        public static void loadOrSaveSharedPreferences(Context context, UserSerializer.User obj, boolean save) {
            String userObj = new Gson().toJson(obj);
            SharedPreferences sharedPreferences = context
                    .getSharedPreferences(PublicData.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            try {
                if (save) {
                    SharedPreferences.Editor editPref = sharedPreferences.edit();
                    editPref.putString(SHARED_PREFERENCES_KEY, userObj);
                    editPref.apply();
                } else {
                    String userSession = sharedPreferences.getString(SHARED_PREFERENCES_KEY, "");
                    if (!userSession.equals(""))
                        USER = new Gson().fromJson(userSession, UserSerializer.User.class);
                }

            } catch (Exception e) {
                Log.e(TAG, "loadOrSaveSharedPreferences: ", e);
            }
        }
    }
}
