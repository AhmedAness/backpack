package com.wasltec.backpack.models;

import java.io.Serializable;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class InboxModel implements Serializable
{

    @SerializedName("chatId")
    @Expose
    private int chatId;
    @SerializedName("activityPhoto_Url")
    @Expose
    private String activityPhotoUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("unReadableMessageNo")
    @Expose
    private int unReadableMessageNo;
    @SerializedName("ticketId")
    @Expose
    private long ticketId;
    @SerializedName("lastMessage")
    @Expose
    private String lastMessage;
    private final static long serialVersionUID = 9075895214290717440L;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getActivityPhotoUrl() {
        return activityPhotoUrl;
    }

    public void setActivityPhotoUrl(String activityPhotoUrl) {
        this.activityPhotoUrl = activityPhotoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnReadableMessageNo() {
        return unReadableMessageNo;
    }

    public void setUnReadableMessageNo(int unReadableMessageNo) {
        this.unReadableMessageNo = unReadableMessageNo;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}