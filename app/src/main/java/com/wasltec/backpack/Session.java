package com.wasltec.backpack;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.wasltec.backpack.models.User;

public class Session {
    private static Session ourInstance;
    Context context;

    User user;
    boolean isVerifyDes = false;
    boolean isVerifyID = false;
    boolean isVerifyLicense = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        saveToFile();
    }

    private Session(Context context) {
        this.context=context;
        start();
    }

    public static Session getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Session(context);

        }
        return ourInstance;
    }


    public boolean isVerifyDes() {
        return isVerifyDes;
    }

    public void setVerifyDes(boolean verifyDes) {
        isVerifyDes = verifyDes;
    }

    public boolean isVerifyID() {
        return isVerifyID;
    }

    public void setVerifyID(boolean verifyID) {
        isVerifyID = verifyID;
    }

    public boolean isVerifyLicense() {
        return isVerifyLicense;
    }

    public void setVerifyLicense(boolean verifyLicense) {
        isVerifyLicense = verifyLicense;
    }

    public int getCompleteInt() {
        int n = 1;
        if (isVerifyDes) {
            n++;
        }
        if (isVerifyID) {
            n++;
        }
        if (isVerifyLicense) {
            n++;
        }
        return n;
    }

    public void start() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mol", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("file")) {
            user = new Gson().fromJson(sharedPreferences.getString("file", null), User.class); }
            else
                user=new User();
    }

    public void saveToFile() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mol", Context.MODE_PRIVATE);
        if (user!=null)
            sharedPreferences.edit().putString("file", new Gson().toJson(user)).apply();
        else
            sharedPreferences.edit().putString("file", new Gson().toJson(new User())).apply();

    }


    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mol", Context.MODE_PRIVATE);
        if (user!=null)
            sharedPreferences.edit().putString("file", "").apply();
        else
            sharedPreferences.edit().putString("file", new Gson().toJson(new User())).apply();

    }

    public String getToken() {
        return context.getSharedPreferences("mol", Context.MODE_PRIVATE).getString("token", null);
    }

    public void setToken(String token) {
       context.getSharedPreferences("mol", Context.MODE_PRIVATE).edit().putString("token", token).apply();
    }

    public boolean isFirstTime() {
        return context.getSharedPreferences("mol", Context.MODE_PRIVATE).getBoolean("first", true);
    }

    public void setisFirstTime(boolean token) {
        context.getSharedPreferences("mol", Context.MODE_PRIVATE).edit().putBoolean("first", token).apply();
    }

}
