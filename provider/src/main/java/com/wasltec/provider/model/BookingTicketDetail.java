
package com.wasltec.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class BookingTicketDetail
{



    @SerializedName("ticket_Id")
    @Expose
    public int id;
    @SerializedName("ticket_number")
    @Expose
    public String ticketNumber;
    @SerializedName("name")
    @Expose
    public String name="";
    @SerializedName("mail")
    @Expose
    public String mail="";
    @SerializedName("mobile")
    @Expose
    public String mobile="";
    @SerializedName("primaryTicket")
    @Expose
    public boolean primaryTicket;
    @SerializedName("ticket_reviewd")
    @Expose
    public boolean ticketReviewd;
    @SerializedName("ticket_checked_in")
    @Expose
    public boolean ticketCheckedIn;
    @SerializedName("ticket_cancelled")
    @Expose
    public boolean ticketCancelled;
    @SerializedName("user_verified")
    @Expose
    public boolean userverified;

    @SerializedName("messageIcon")
    @Expose
    public boolean messageIcon;

    @SerializedName("isGroupTicket")
    @Expose
    public boolean isGroupTicket;

    @SerializedName("numOfGroup")
    @Expose
    public double numOfGroup;

    @SerializedName("chatId")
    @Expose
    public double chat_id;


    @SerializedName("nameOfGroup")
    @Expose
    public String nameOfGroup;
    @SerializedName("userPhoto_Url")
    @Expose
    public String userPhoto_Url;

    public BookingTicketDetail() {
    }


    public BookingTicketDetail(int id, String ticketNumber, String name, String mail, String mobile, boolean primaryTicket, boolean ticketReviewd, boolean ticketCheckedIn, boolean ticketCancelled, boolean userverified, boolean messageIcon, boolean isGroupTicket, double numOfGroup,int chatid, String nameOfGroup) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.name = name;
        this.mail = mail;
        this.mobile = mobile;
        this.primaryTicket = primaryTicket;
        this.ticketReviewd = ticketReviewd;
        this.ticketCheckedIn = ticketCheckedIn;
        this.ticketCancelled = ticketCancelled;
        this.userverified = userverified;
        this.messageIcon = messageIcon;
        this.isGroupTicket = isGroupTicket;
        this.numOfGroup = numOfGroup;
        this.nameOfGroup = nameOfGroup;
        this.chat_id=chatid;
    }

    public String getUserPhoto_Url() {
        return userPhoto_Url;
    }

    public void setUserPhoto_Url(String userPhoto_Url) {
        this.userPhoto_Url = userPhoto_Url;
    }

    public double getChat_id() {
        return chat_id;
    }

    public void setChat_id(double chat_id) {
        this.chat_id = chat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isPrimaryTicket() {
        return primaryTicket;
    }

    public void setPrimaryTicket(boolean primaryTicket) {
        this.primaryTicket = primaryTicket;
    }

    public boolean isTicketReviewd() {
        return ticketReviewd;
    }

    public void setTicketReviewd(boolean ticketReviewd) {
        this.ticketReviewd = ticketReviewd;
    }

    public boolean isTicketCheckedIn() {
        return ticketCheckedIn;
    }

    public void setTicketCheckedIn(boolean ticketCheckedIn) {
        this.ticketCheckedIn = ticketCheckedIn;
    }

    public boolean isTicketCancelled() {
        return ticketCancelled;
    }

    public void setTicketCancelled(boolean ticketCancelled) {
        this.ticketCancelled = ticketCancelled;
    }

    public boolean isUserverified() {
        return userverified;
    }

    public void setUserverified(boolean userverified) {
        this.userverified = userverified;
    }


    public boolean isMessageIcon() {
        return messageIcon;
    }

    public void setMessageIcon(boolean messageIcon) {
        this.messageIcon = messageIcon;
    }

    public boolean isGroupTicket() {
        return isGroupTicket;
    }

    public void setGroupTicket(boolean groupTicket) {
        isGroupTicket = groupTicket;
    }

    public double getNumOfGroup() {
        return numOfGroup;
    }

    public void setNumOfGroup(double numOfGroup) {
        this.numOfGroup = numOfGroup;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }
}
