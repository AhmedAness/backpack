package com.wasltec.backpack.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerActivitiesModel implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("activity_Start")
    @Expose
    private String activityStart;
    @SerializedName("activityPhoto_Url")
    @Expose
    private String activityPhotoUrl;
    @SerializedName("avaliability_id")
    @Expose
    private Integer avaliabilityId;
    @SerializedName("ticket_id")
    @Expose
    private Integer ticketId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("favActivity")
    @Expose
    private Boolean favActivity;
    private final static long serialVersionUID = 5631548896005409037L;

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

    public String getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(String activityStart) {
        this.activityStart = activityStart;
    }

    public String getActivityPhotoUrl() {
        return activityPhotoUrl;
    }

    public void setActivityPhotoUrl(String activityPhotoUrl) {
        this.activityPhotoUrl = activityPhotoUrl;
    }

    public Integer getAvaliabilityId() {
        return avaliabilityId;
    }

    public void setAvaliabilityId(Integer avaliabilityId) {
        this.avaliabilityId = avaliabilityId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getFavActivity() {
        return favActivity;
    }

    public void setFavActivity(Boolean favActivity) {
        this.favActivity = favActivity;
    }

}
