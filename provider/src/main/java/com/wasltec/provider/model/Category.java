package com.wasltec.provider.model;

public class Category {
    int id ;
    String name,url;

    Object activities;

    public Category(int id, String name, String url, Object activities) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.activities = activities;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Object getActivities() {
        return activities;
    }

    public void setActivities(Object activities) {
        this.activities = activities;
    }
}


