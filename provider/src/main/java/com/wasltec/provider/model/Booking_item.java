package com.wasltec.provider.model;


import java.util.Date;

public class Booking_item {
    int id;
    public String Name;
    public int NumOfTickets;
    public Date date;
    public String Time;
    String phone,Mail;

    boolean paid =false ;

    public Booking_item(int id ,String name, String mail, String phone , int numOfTickets, Date date, String time, boolean paid) {
        this.id= id;
        Name = name;
        Mail = mail;
        this.phone = phone;
        NumOfTickets = numOfTickets;
        this.date = date;
        Time = time;
        this.paid = paid;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumOfTickets() {
        return NumOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        NumOfTickets = numOfTickets;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
