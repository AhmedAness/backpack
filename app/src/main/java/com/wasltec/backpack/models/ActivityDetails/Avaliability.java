
package com.wasltec.backpack.models.ActivityDetails;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avaliability implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("activity_Start")
    @Expose
    private String activityStart;
    @SerializedName("activity_End")
    @Expose
    private String activityEnd;
    @SerializedName("group_Price")
    @Expose
    private Integer groupPrice;
    @SerializedName("total_tickets")
    @Expose
    private Integer totalTickets;
    @SerializedName("isForGroup")
    @Expose
    private Integer isForGroup;
    @SerializedName("avaliability_Pricings")
    @Expose
    private List<Object> avaliabilityPricings = null;

    public Avaliability() {
    }

    private final static long serialVersionUID = -2749684018610289971L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(String activityStart) {
        this.activityStart = activityStart;
    }

    public String getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(String activityEnd) {
        this.activityEnd = activityEnd;
    }

    public Integer getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Integer groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getIsForGroup() {
        return isForGroup;
    }

    public void setIsForGroup(Integer isForGroup) {
        this.isForGroup = isForGroup;
    }

    public List<Object> getAvaliabilityPricings() {
        return avaliabilityPricings;
    }

    public void setAvaliabilityPricings(List<Object> avaliabilityPricings) {
        this.avaliabilityPricings = avaliabilityPricings;
    }

}
