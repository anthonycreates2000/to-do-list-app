package com.example.keepoapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.keepoapp.data.User;

public class SharedPreferenceManager {
    public static String KEY_USER_DATA = "com.example.keepoapp.KEY_USER_DATA";
    public static String KEY_USERNAME = "com.example.keepoapp.KEY_USERNAME";
    public static String KEY_USER_ID = "com.example.keepoapp.KEY_USER_ID";
    public static String KEY_NAME = "com.example.keepoapp.KEY_NAME";
    private SharedPreferences sharedPreferences;
    public SharedPreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences(KEY_USER_DATA, 0);
    }
    public void saveUserData(User user){
        sharedPreferences.edit()
            .putString(KEY_USERNAME, user.getUsername())
            .putString(KEY_NAME, user.getName())
            .putString(KEY_USER_ID, user.getUser_id())
                .apply();
    }
    public boolean isInitialized(){
        if (!sharedPreferences.getString(KEY_NAME, "").equals("")){
            return true;
        }
        return false;
    }
    public User getUserData(){
        String name = sharedPreferences.getString(KEY_NAME, "");
        Log.d("Name", name);
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        String userID = sharedPreferences.getString(KEY_USER_ID, "");
        return new User(userID, name, username);
    }
    public void deleteUserData(){
        sharedPreferences.edit()
            .clear()
            .apply();
    }
}
