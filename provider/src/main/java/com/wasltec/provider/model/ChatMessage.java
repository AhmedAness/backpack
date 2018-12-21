package com.wasltec.provider.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage {

     int messageReplayId,userId,regardingId;
     String message,date,user_name,userPhoto_Url,typeId;


    public ChatMessage(int messageReplayId, int userId, int regardingId, String message, String date, String user_name, String userPhoto_Url, String typeId) {
        this.messageReplayId = messageReplayId;
        this.userId = userId;
        this.regardingId = regardingId;
        this.message = message;
        this.date = date;
        this.user_name = user_name;
        this.userPhoto_Url = userPhoto_Url;
        this.typeId = typeId;
    }


    public int getMessageReplayId() {
        return messageReplayId;
    }

    public void setMessageReplayId(int messageReplayId) {
        this.messageReplayId = messageReplayId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRegardingId() {
        return regardingId;
    }

    public void setRegardingId(int regardingId) {
        this.regardingId = regardingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUserPhoto_Url() {
        return userPhoto_Url;
    }

    public void setUserPhoto_Url(String userPhoto_Url) {
        this.userPhoto_Url = userPhoto_Url;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public long getcurrTime() {


        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        try {
            Startdate = format.parse(date.replace( "T" , " " ));

        } catch (ParseException e) {
            Startdate=new Date();

            e.printStackTrace();
        }

        return Startdate.getTime();
    }
}
