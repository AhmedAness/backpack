package com.wasltec.backpack.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class SimilarActivitiesResponse {
    @Expose
    @SerializedName("favActivity")
    private boolean favActivity;
    @Expose
    @SerializedName("group_price")
    private int group_price;
    @Expose
    @SerializedName("price")
    private int price;
    @Expose
    @SerializedName("reviewsCount")
    private int reviewsCount;

    private String rate;
    @Expose
    @SerializedName("activityPhoto_Url")
    private String activityPhoto_Url;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id")
    private int id;

    public boolean getFavActivity() {
        return favActivity;
    }

    public void setFavActivity(boolean favActivity) {
        this.favActivity = favActivity;
    }

    public int getGroup_price() {
        return group_price;
    }

    public void setGroup_price(int group_price) {
        this.group_price = group_price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getActivityPhoto_Url() {
        return activityPhoto_Url;
    }

    public void setActivityPhoto_Url(String activityPhoto_Url) {
        this.activityPhoto_Url = activityPhoto_Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String  rate) {
        this.rate = rate;
    }
}
