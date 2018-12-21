package com.wasltec.provider.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ActivityCalenderModel implements Serializable, Parcelable
{

    @SerializedName("avaliabilityid")
    @Expose
    private int avaliabilityid;
    @SerializedName("activity_Start")
    @Expose
    private String activityStart;
    @SerializedName("activity_End")
    @Expose
    private String activityEnd;
    @SerializedName("group_price")
    @Expose
    private int groupPrice;
    @SerializedName("isForGroup")
    @Expose
    private int isForGroup;
    @SerializedName("total_tickets")
    @Expose
    private int totalTickets;
    @SerializedName("totalCapacity")
    @Expose
    private int totalCapacity;
    @SerializedName("avaliabilityPricing")
    @Expose
    private List<AvaliabilityPricing> avaliabilityPricing = null;
    public final static Parcelable.Creator<ActivityCalenderModel> CREATOR = new Creator<ActivityCalenderModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ActivityCalenderModel createFromParcel(Parcel in) {
            return new ActivityCalenderModel(in);
        }

        public ActivityCalenderModel[] newArray(int size) {
            return (new ActivityCalenderModel[size]);
        }

    }
            ;
    private final static long serialVersionUID = -7470510172560031700L;


    protected ActivityCalenderModel(Parcel in) {
        this.avaliabilityid = ((int) in.readValue((int.class.getClassLoader())));
        this.activityStart = ((String) in.readValue((String.class.getClassLoader())));
        this.activityEnd = ((String) in.readValue((String.class.getClassLoader())));
        this.groupPrice = ((int) in.readValue((int.class.getClassLoader())));
        this.isForGroup = ((int) in.readValue((int.class.getClassLoader())));
        this.totalTickets = ((int) in.readValue((int.class.getClassLoader())));
        this.totalCapacity = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.avaliabilityPricing, (com.wasltec.provider.model.AvaliabilityPricing.class.getClassLoader()));
    }

    public ActivityCalenderModel() {
    }

    public int getAvaliabilityid() {
        return avaliabilityid;
    }

    public void setAvaliabilityid(int avaliabilityid) {
        this.avaliabilityid = avaliabilityid;
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

    public int getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(int groupPrice) {
        this.groupPrice = groupPrice;
    }

    public int getIsForGroup() {
        return isForGroup;
    }

    public void setIsForGroup(int isForGroup) {
        this.isForGroup = isForGroup;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public List<AvaliabilityPricing> getAvaliabilityPricing() {
        return avaliabilityPricing;
    }

    public void setAvaliabilityPricing(List<AvaliabilityPricing> avaliabilityPricing) {
        this.avaliabilityPricing = avaliabilityPricing;
    }




    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(avaliabilityid);
        dest.writeValue(activityStart);
        dest.writeValue(activityEnd);
        dest.writeValue(groupPrice);
        dest.writeValue(isForGroup);
        dest.writeValue(totalTickets);
        dest.writeValue(totalCapacity);
        dest.writeList(avaliabilityPricing);
    }

    public int describeContents() {
        return 0;
    }

    public Date getActivityStartDate() {

        Date Startdate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        try {
            Startdate = format.parse(activityStart.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            e.printStackTrace();
        }

        return Startdate;
    }
    public Date getActivityEndDate() {

        Date Enddate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        try {
            Enddate = format.parse(activityEnd.replace( "T" , " " ));
        } catch (ParseException e) {
            Enddate=new Date();
            e.printStackTrace();
        }

        return Enddate;
    }
}