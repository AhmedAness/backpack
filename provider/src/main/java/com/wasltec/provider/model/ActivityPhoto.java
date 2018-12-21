package com.wasltec.provider.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class ActivityPhoto {

    Integer id;
    Integer activityId;
    String url;
    Uri uri;
    Object activity;
    Bitmap bitmap;

    private boolean cover_photo;




    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public Object getActivity() {
        return activity;
    }

    public void setActivity(Object activity) {
        this.activity = activity;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


    public boolean getCoverPhoto() { return this.cover_photo; }

    public void setCoverPhoto(boolean cover_photo) { this.cover_photo = cover_photo; }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}




