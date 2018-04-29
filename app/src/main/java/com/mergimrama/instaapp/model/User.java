package com.mergimrama.instaapp.model;

/**
 * Created by Mergim on 17-Dec-17.
 */
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

public class User implements Parcelable {
    private String userId;
    private String name;
    private String username;
    private String status;

    public User(JSONObject jsonObject) {
        userId = jsonObject.optString("UserID");
        name = jsonObject.optString("Emri");
        username = jsonObject.optString("Username");
        status = jsonObject.optString("status");
    }

    public User(String userId, String name, String username, String status) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.status = status;
    }

    protected User(Parcel in) {
        userId = in.readString();
        name = in.readString();
        username = in.readString();
        status = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(status);
    }
}

