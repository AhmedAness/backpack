package com.wasltec.provider.model;

import com.wasltec.provider.Utils.Post_type;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Post_item {
    String userPhoto_Url, user_name, review,title;
    int rate,activity_id,reviewid;
    String  date;





    public Post_item(String image_url, String user_name, String review, int rate, String date) {
        userPhoto_Url = image_url;
        this.user_name = user_name;
        this.review = review;
        this.rate = rate;
        this.date = date;

    }

    public String getUserPhoto_Url() {
        return userPhoto_Url;
    }

    public void setUserPhoto_Url(String userPhoto_Url) {
        this.userPhoto_Url = userPhoto_Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public String getImage_url() {
        return userPhoto_Url;
    }

    public void setImage_url(String image_url) {
        userPhoto_Url = image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate() {

        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);

        try {
            Startdate = format.parse(date.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy hh:mm:ss");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(Startdate);


        return strDate;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
