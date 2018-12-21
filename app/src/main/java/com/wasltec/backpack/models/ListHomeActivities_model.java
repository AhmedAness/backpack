package com.wasltec.backpack.models;

import java.util.ArrayList;

public class ListHomeActivities_model {

    ArrayList<ActivityTrip> activities;
    int more;


    public ListHomeActivities_model(ArrayList<ActivityTrip> activities, int more) {
        this.activities = activities;
        this.more = more;
    }

    public ArrayList<ActivityTrip> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<ActivityTrip> activities) {
        this.activities = activities;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
