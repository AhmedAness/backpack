package com.wasltec.provider.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Inbox_model {


    int chatId;
    String lastUpdateDate;
    String firstUser;
    String userName;
    String userPhoto_Url;
    int unReadableMessageNo;


    public Inbox_model(int chatId, String lastUpdateDate, String firstUser, String userName, String userPhoto_Url, int unReadableMessageNo) {
        this.chatId = chatId;
        this.lastUpdateDate = lastUpdateDate;
        this.firstUser = firstUser;
        this.userName = userName;
        this.userPhoto_Url = userPhoto_Url;
        this.unReadableMessageNo = unReadableMessageNo;
    }


    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getLastUpdateDate() {

        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        try {
            Startdate = format.parse(lastUpdateDate.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            e.printStackTrace();
        }

        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto_Url() {
        return userPhoto_Url;
    }

    public void setUserPhoto_Url(String userPhoto_Url) {
        this.userPhoto_Url = userPhoto_Url;
    }

    public int getUnReadableMessageNo() {
        return unReadableMessageNo;
    }

    public void setUnReadableMessageNo(int unReadableMessageNo) {
        this.unReadableMessageNo = unReadableMessageNo;
    }
}
