package com.wasltec.provider.model.Statistic;

public class Tickets {
    int activityid;
    String title;
    int numberOfTickets;

    public Tickets(int activityid, String title, int numberOfTickets) {
        this.activityid = activityid;
        this.title = title;
        this.numberOfTickets = numberOfTickets;
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

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
