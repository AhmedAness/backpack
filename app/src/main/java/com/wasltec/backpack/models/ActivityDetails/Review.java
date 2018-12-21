
package com.wasltec.backpack.models.ActivityDetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review implements Serializable
{

    @SerializedName("reviewid")
    @Expose
    private Integer reviewid;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("userPhoto_Url")
    @Expose
    private String userPhotoUrl;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("date")
    @Expose
    private String date;
    private final static long serialVersionUID = -7434847016699516025L;

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
