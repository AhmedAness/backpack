package com.wasltec.provider.model.Statistic;

public class Revenue {

     int activityid;
     String title ;
     int  totalAmount;

    public Revenue(int activityid, String title, int totalAmount) {
        this.activityid = activityid;
        this.title = title;
        this.totalAmount = totalAmount;
    }

    public int getActivityid() {
        return activityid;
    }

    public void setActivityid(int activityid) {
        this.activityid = activityid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
