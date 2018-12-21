
package com.wasltec.backpack.models.ActivityDetails;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerActivitiesDetailsModel implements Serializable
{

    @SerializedName("activityDetails")
    @Expose
    private List<ActivityDetail> activityDetails = null;
    private final static long serialVersionUID = -4694223205357253633L;

    public List<ActivityDetail> getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(List<ActivityDetail> activityDetails) {
        this.activityDetails = activityDetails;
    }

}
