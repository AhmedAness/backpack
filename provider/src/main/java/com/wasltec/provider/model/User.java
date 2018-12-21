package com.wasltec.provider.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wasltec.provider.Utils.App_Context.TAG;

public class User {
    int id ,age ,LoginType=1;
    String name , Email,Password,gender ,phone ;
    boolean isProvider;


    public User(int id, int age, String name, String Email, String Password, String gender, String phone, boolean isProvider) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.Email = Email;
        this.Password = Password;
        this.gender = gender;
        this.phone = phone;
        this.isProvider = isProvider;
    }
    public User(JSONObject jsonObject) {
        if (jsonObject.has("id")) {
            try {
                id = jsonObject.getInt("id");
            } catch (JSONException e) {
                id=-1;
                Log.d(TAG, "User: id  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("name")) {
            try {
                name = jsonObject.getString("name");
            } catch (JSONException e) {
                name="";
                Log.d(TAG, "User: user_name  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("Email")) {
            try {
                Email = jsonObject.getString("Email");
            } catch (JSONException e) {
                Email="";
                Log.d(TAG, "User: Email  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("Password")) {
            try {
                Password = jsonObject.getString("Password");
            } catch (JSONException e) {
                Password ="";
                Log.d(TAG, "User: Password  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("isProvider")) {
            try {
                isProvider = jsonObject.getBoolean("isProvider");
            } catch (JSONException e) {
                isProvider=false;
                Log.d(TAG, "User: isProvider  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("gender")) {
            try {
                gender = jsonObject.getString("gender");
            } catch (JSONException e) {
                gender="";
                Log.d(TAG, "User: gender  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("age")) {
            try {
                age = jsonObject.getInt("age");
            } catch (JSONException e) {
                age =10;
                Log.d(TAG, "User: age  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("phone")) {
            try {
                phone = jsonObject.getString("phone");
            } catch (JSONException e) {
                phone ="";
                Log.d(TAG, "User: phone  "+e.getMessage());
                e.printStackTrace();
            }
        } if (jsonObject.has("LoginType")) {
            try {
                LoginType = jsonObject.getInt("LoginType");
            } catch (JSONException e) {
                LoginType =1;
                Log.d(TAG, "User: phone  "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public User(String Email, String Password) {
        this.Email = Email;
        this.Password = Password;
    }

    JSONObject   TojsonObject(){
        JSONObject jsonObject= new JSONObject();
        try {

            jsonObject.put("id",id);
            jsonObject.put("age",age);
            jsonObject.put("user_name",name);
            jsonObject.put("Email",Email);
            jsonObject.put("Password",Password);
            jsonObject.put("gender",gender);
            jsonObject.put("phone",phone);
            jsonObject.put("isProvider",isProvider);
            jsonObject.put("LoginType",LoginType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject ;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", isProvider=" + isProvider +
                ", LoginType=" + LoginType +
                '}';
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isProvider() {
        return isProvider;
    }

    public void setProvider(boolean provider) {
        isProvider = provider;
    }
}
