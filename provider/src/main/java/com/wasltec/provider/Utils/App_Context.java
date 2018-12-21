package com.wasltec.provider.Utils;

import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.Booking_item;

import java.util.ArrayList;
import java.util.List;

public  class App_Context {


    static public String TAG="loger";
//    public static ArrayList<Activity_model> Activities= new ArrayList<>();
    public static Activity_model current_Activity = new Activity_model();
//    public static List<Booking_item> booking_data =new ArrayList<>();
    private static final App_Context ourInstance = new App_Context();

    public static App_Context getInstance() {
        return ourInstance;
    }

    private App_Context() {
        Activity_model myData;
        for (int i = 0; i <10 ; i++) {
            myData= new Activity_model(i,"land",10,10,3,20,15,"title"+i,"pster"+i,"","date "+i,"",""+i,true);
//            Activities.add(myData);
        }
    }


//    public ArrayList<Activity_model> getActivities() {
//        return Activities;
//    }
//
//
//    public void setActivities(ArrayList<Activity_model> activities) {
//        Activities = activities;
//    }
//
//    public static String getTAG() {
//        return TAG;
//    }
//
//    public static void setTAG(String TAG) {
//        App_Context.TAG = TAG;
//    }
//
//    public static Activity_model getCurrent_Activity() {
//        return current_Activity;
//    }
//
//    public static void setCurrent_Activity(Activity_model current_Activity) {
//        App_Context.current_Activity = current_Activity;
//    }
//
//    public static List<Booking_item> getBooking_data() {
//        return booking_data;
//    }
//
//    public static void setBooking_data(List<Booking_item> booking_data) {
//        App_Context.booking_data = booking_data;
//    }
}
