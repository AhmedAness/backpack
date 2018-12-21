package com.wasltec.provider.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private Context context;
    private String phoneNumber, password, accessToken;
    SharedPreferences.Editor editor;
    SharedPreferences settings;
    //notification config
    public static boolean From_following = false;
    public static boolean From_news = false;
    public static boolean From_admin = false;
    public static boolean message_alarm = false;

    public   boolean isFrom_following() {
        settings = context.getSharedPreferences("login", 0);
        return settings.getBoolean("From_following",false);

    }

    public   boolean isFrom_news() {

        settings = context.getSharedPreferences("login", 0);
        return settings.getBoolean("From_news",false);
    }

    public   boolean isFrom_admin() {

        settings = context.getSharedPreferences("login", 0);
        return settings.getBoolean("From_admin",false);

    }

    public   boolean isMessage_alarm() {
        settings = context.getSharedPreferences("login", 0);
        return settings.getBoolean("message_alarm",false);

    }

    public   void setFrom_following(boolean from_following) {
        From_following = from_following;
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putBoolean("From_following", From_following);
        editor.commit();
    }

    public   void setFrom_news(boolean from_news) {

        From_news = from_news;
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putBoolean("From_news", From_news);
        editor.commit();
    }

    public   void setFrom_admin(boolean from_admin) {
        From_admin = from_admin;


        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putBoolean("From_admin", From_admin);
        editor.commit();

    }

    public   void setMessage_alarm(boolean message_alarm) {
        UserPreferences.message_alarm = message_alarm;

        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putBoolean("message_alarm", UserPreferences.message_alarm);
        editor.commit();
    }

    public UserPreferences(Context context) {
        this.context = context;
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
    }



    public String getPhoneNumber() {
        settings = context.getSharedPreferences("login", 0);
        return settings.getString("phoneNumber","");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putString("phoneNumber", this.phoneNumber);
        editor.commit();
    }

    public String getPassword() {
        settings = context.getSharedPreferences("login", 0);
        return settings.getString("password","");
    }

    public void setPassword(String password) {
        this.password = password;
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putString("password", this.password);
        editor.commit();
    }

    public String getAccessToken() {
        settings = context.getSharedPreferences("login", 0);
        return settings.getString("accessToken", "");
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.putString("accessToken", accessToken);
        editor.commit();

    }


    public void removeLogin(Context context) {
        settings = context.getSharedPreferences("login", 0);
        editor = settings.edit();
        editor.remove("accessToken");
        editor.remove("phoneNumber");
        editor.remove("password");
        editor.commit();
    }
}
