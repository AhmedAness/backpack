package com.wasltec.provider.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Availability {


    String activity_Start,activity_End;
    int isprovider,id ,isForGroup,group_Price;
    List<AvalibilityPricingModels> avalibilityPricingModels;






    public Availability(String activity_End, String activity_Start, int isForGroup,int group_Price) {
        this.id= 0;

        this.activity_End = activity_End;
        this.activity_Start = activity_Start;
        this.isForGroup = isForGroup;
        this.group_Price = group_Price;
        avalibilityPricingModels= new ArrayList<>();
    }
    public Availability(String activity_End, String activity_Start, int isForGroup) {
        this.activity_End = activity_End;
        this.activity_Start = activity_Start;
        this.isForGroup = isForGroup;
        avalibilityPricingModels= new ArrayList<>();
    }





    public String getActivity_Start() {
        return activity_Start;
    }

    public void setActivity_Start(String activity_Start) {
        this.activity_Start = activity_Start;
    }

    public String getActivity_End() {
        return activity_End;
    }

    public void setActivity_End(String activity_End) {
        this.activity_End = activity_End;
    }

    public int getIsprovider() {
        return isprovider;
    }

    public void setIsprovider(int isprovider) {
        this.isprovider = isprovider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsForGroup() {
        return isForGroup;
    }

    public void setIsForGroup(int isForGroup) {
        this.isForGroup = isForGroup;
    }

    @Override
    public String toString() {
        activity_Start=activity_Start.replace("T"," ");
        activity_End=activity_End.replace("T"," ");
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd H:m");
        Date date = null;
        Date date2 = null;

        try {
            date = inFormat.parse(activity_Start);
            date2=inFormat.parse(activity_End);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE h a");
        String goal = outFormat.format(date);

        String goal2 = outFormat.format(date2);
        return "" + goal+" "+ '-'  +" "+ goal2 + " "  ;

    }

    public int getGroup_Price() {
        return group_Price;
    }

    public void setGroup_Price(int group_Price) {
        this.group_Price = group_Price;
    }

    public List<AvalibilityPricingModels> getAvalibilityPricingModels() {
        return avalibilityPricingModels;
    }

    public void setAvalibilityPricingModels(List<AvalibilityPricingModels> avalibilityPricingModels) {
        this.avalibilityPricingModels = avalibilityPricingModels;
    }
}
