package com.wasltec.backpack.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static SharedPreferencesManager ourInstance = null;
    Context context;
    SharedPreferences sharedPrefrence;

    private SharedPreferencesManager(Context context) {
        this.context = context;
        sharedPrefrence = context.getSharedPreferences("App", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SharedPreferencesManager(context);

        }
        return ourInstance;
    }

    public String getToken() {
        return sharedPrefrence.getString("token", null);
    }

    public void setToken(String token) {
        sharedPrefrence.edit().putString("token", token).apply();
    }

    public boolean isFirstTime() {
        return sharedPrefrence.getBoolean("first", true);
    }

    public void setisFirstTime(boolean token) {
        sharedPrefrence.edit().putBoolean("first", token).apply();
    }

}
