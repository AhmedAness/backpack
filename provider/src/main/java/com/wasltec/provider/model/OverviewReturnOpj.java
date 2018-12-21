package com.wasltec.provider.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OverviewReturnOpj  {

    /*
 "activity_id": 18,
        "title": "test11-9-4",
        "activity_Start": "2018-10-12T17:44:12",
        "isCompleted": true,
        "isForGroup": 0,
        "status": true,
        "total_tickets": 7,
        "id": 2286
    * */


    int activity_id ,
            total_tickets ,
            isForGroup ,
            id,remainingTickets;
    String title ,
            activity_Start;
    boolean isCompleted ,
            status;

    /*
    * {"id":2,"title":"Alex",
    * "activity_Start":"2018-11-12T12:00:00",
    * "total_tickets":100,"isForGroup":1}
    *
    * */

    public OverviewReturnOpj(int id, int total_tickets, int isprovider, String title, String activity_Start,int remainingTickets) {
        this.activity_id = id;
        this.total_tickets = total_tickets;
        this.isForGroup = isprovider;
        this.title = title;
        this.activity_Start = activity_Start;
        this.remainingTickets = remainingTickets;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getId() {
        return activity_id;
    }

    public void setId(int id) {
        this.activity_id = id;
    }

    public int getTotal_tickets() {
        return total_tickets;
    }

    public void setTotal_tickets(int total_tickets) {
        this.total_tickets = total_tickets;
    }

    public int getIsForGroup() {
        return isForGroup;
    }

    public void setIsForGroup(int isForGroup) {
        this.isForGroup = isForGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Date getActivity_Start_date(){
        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Startdate = format.parse(activity_Start.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            e.printStackTrace();
        }
        return Startdate;
    }
    public String getActivity_Start() {

        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Startdate = format.parse(activity_Start.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("EEEE,dd MMMM yyyy");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(Startdate);

        return strDate;
    }
    public String getActivity_Start_trim() {

        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Startdate = format.parse(activity_Start.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("EEEE,dd MMMM yyyy");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(Startdate);

        return strDate;
    }


    public String getActivity_Start2() {


        return activity_Start.replace( "T" , " " );
    }
    public String getActivity_StartwithT() {


        return activity_Start;
    }

    public void setActivity_Start(String activity_Start) {
        this.activity_Start = activity_Start;
    }


    public int getAvaid() {
        return id;
    }

    public void setAvaid(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
