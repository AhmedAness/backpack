
package com.wasltec.backpack.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerFavoriteActivitiesModel implements Serializable
{



    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("activity_Location")
    @Expose
    private String activityLocation;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("firstIndividualPrice")
    @Expose
    private int price;
    @SerializedName("additiondate")
    @Expose
    private String additiondate;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("activity_Photos")
    @Expose
    private String activityPhotos = null;

    private final static long serialVersionUID = 7654197151755006983L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getAdditiondate() {
        return additiondate;
    }

    public void setAdditiondate(String additiondate) {
        this.additiondate = additiondate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getActivityPhotos() {
        return activityPhotos;
    }

    public void setActivityPhotos(String activityPhotos) {
        this.activityPhotos = activityPhotos;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
