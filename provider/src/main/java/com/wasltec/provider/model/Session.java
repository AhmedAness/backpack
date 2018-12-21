package com.wasltec.provider.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Session {
    private static Session ourInstance;



    int id;
    String userName = "userName";
    String email = "Insert It";
    String first_name = "first_name";
    String last_name = "last_name";


    String userPhoto_Url, phone;
    boolean isVerifyDes = false;
    boolean isProvider = false;
    boolean isVerifyID = false;
    boolean isVerifyLicense = false;

    private Session() {
    }

    public static Session getInstance() {
        if (ourInstance == null) {
            ourInstance = new Session();
        }
        return ourInstance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getUserPhoto_Url() {
        return userPhoto_Url;
    }

    public void setUserPhoto_Url(String userPhoto_Url) {
        this.userPhoto_Url = userPhoto_Url;
    }

    public boolean isProvider() {
        return isProvider;
    }

    public void setProvider(boolean provider) {
        isProvider = provider;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (userPhoto_Url != null && !userPhoto_Url.isEmpty())
            n++;
        return n;
    }

    public void start(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mol", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("file")) {
            Session session = new Gson().fromJson(sharedPreferences.getString("file", null), Session.class);




            String first_name = "first_name";
            String last_name = "last_name";

            String userPhoto_Url, phone;

            this.id = session.id;
            this.userName = session.userName;
            this.isVerifyID = session.isVerifyID;
            this.isVerifyLicense = session.isVerifyLicense;
            this.isProvider = session.isProvider;
            this.isVerifyDes = session.isVerifyDes;
            this.email = session.email;
            this.first_name = session.first_name;
            this.last_name = session.last_name;
            this.phone = session.phone;
        }
    }

    public void saveToFile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mol", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("file", new Gson().toJson(this)).apply();
    }

    public void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mol", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("file","").apply();
    }

}
