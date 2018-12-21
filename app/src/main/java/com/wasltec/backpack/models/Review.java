
package com.wasltec.backpack.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("reviewid")
    @Expose
    private int reviewid;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("userPhoto_Url")
    @Expose
    private String userPhotoUrl;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     *
     */
    public Review() {
    }

    /**
     *
     * @param userPhotoUrl
     * @param rate
     * @param userName
     * @param reviewid
     * @param date
     * @param review
     */
    public Review(int reviewid, String userName, String userPhotoUrl, int rate, String review, String date) {
        super();
        this.reviewid = reviewid;
        this.userName = userName;
        this.userPhotoUrl = userPhotoUrl;
        this.rate = rate;
        this.review = review;
        this.date = date;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
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
    public String getDate_trim (String x) {

        return date.substring(0,x.indexOf("T"));
    }

    public void setDate(String date) {
        this.date = date;
    }

}