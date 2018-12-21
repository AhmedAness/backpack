package com.wasltec.provider.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AllActivityReturnOpj {

    private int id;
    private String title;
    private String activity_Location;
    private Boolean status,isCompleted;
    private float rate;
    private String name;
    private int stepNumber;
    private String url;
    List<ActivityPhoto> activity_Photos = null;
    Category category;


    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivity_Location() {
        return activity_Location;
    }

    public void setActivity_Location(String activity_Location) {
        this.activity_Location = activity_Location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ActivityPhoto> getActivityPhotos() {
        return activity_Photos;
    }

    public void setActivityPhotos(List<ActivityPhoto> activityPhotos) {
        this.activity_Photos = activityPhotos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}