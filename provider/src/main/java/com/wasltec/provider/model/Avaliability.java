package com.wasltec.provider.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Avaliability
{
      int id;
      String activitystart;
      String actvityEnd;
      int isForGroup;



    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }


    public String getActivitystart() { return this.activitystart; }

    public void setActivitystart(String activitystart) { this.activitystart = activitystart; }

    public String getActvityEnd() { return this.actvityEnd; }

    public void setActvityEnd(String actvityEnd) { this.actvityEnd = actvityEnd; }

    public int getIsprovider() { return this.isForGroup; }

    public void setIsprovider(int isprovider) { this.isForGroup = isprovider; }

    @Override
    public String toString() {

        Date Startdate ,enddate ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        try {
            Startdate = format.parse(activitystart.replace( "T" , " " ));
            enddate = format.parse(actvityEnd.replace( "T" , " " ));
        } catch (ParseException e) {
            Startdate=new Date();
            enddate=new Date();
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MMM hh:mm aaa");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(Startdate);
        String endDate = dateFormat.format(enddate);


        return strDate+" - "+endDate;
    }
}