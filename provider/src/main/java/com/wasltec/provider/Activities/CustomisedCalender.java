package com.wasltec.provider.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.provider.CalenderLib.CalendarListener;
import com.wasltec.provider.CalenderLib.CustomCalendarView;
import com.wasltec.provider.MapsActivity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityCalenderModel;
import com.wasltec.provider.model.User_res;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomisedCalender extends AppCompatActivity {




    public static String activity_id ;
    public static boolean getdate =false;

    List<ActivityCalenderModel> activityCalenderModelList;

    CustomCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customised_calender);



        //Initialize CustomCalendarView from layout
        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);

        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);
        
        //call refreshCalendar to update calendar the view
        try {
            activity_id= getIntent().getStringExtra("Activity_id");
        }catch (Exception e){
            Toast.makeText(this,"invalide activity id ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("daydate", currentCalendar.getTime());
            setResult(Activity.RESULT_OK, intent);
            finish();
            activity_id="0";
        }

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {


            @Override
            public void onDateSelected(Date date, boolean inaval,ActivityCalenderModel activityCalenderModel) {

                Calenderdaydetails.day= date ;
                Calenderdaydetails.is_Available= inaval ;

////////////////////

                 if(getdate){
                     Intent intent = new Intent();
                     intent.putExtra("daydate", date.getTime());
                     intent.putExtra("Avail_data", new Gson().toJson(activityCalenderModel).toString());


                     setResult(Activity.RESULT_OK, intent);
                     getdate=false;
                     finish();
                 }

                /////////////
                else {

                     if (inaval)

                     {

                         Intent intent = new Intent(CustomisedCalender.this, BookingActivity.class);
                         intent.putExtra("id", activity_id);
                         startActivity(intent);


                     } else

                     {
                         Intent intent = new Intent(CustomisedCalender.this, Calenderdaydetails.class);
                         startActivity(intent);
                     }

                 }

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Toast.makeText(CustomisedCalender.this, df.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
                Toast.makeText(CustomisedCalender.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        //Setting custom font


        AndroidNetworking.get(URLManger.getInstance().getCalenderReservation(activity_id))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(CustomisedCalender.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();

                        activityCalenderModelList = gson.fromJson(response.toString(),
                                new TypeToken<List<ActivityCalenderModel>>(){}.getType());


                        calendarView.setcalendardata(activityCalenderModelList);
                        calendarView.refreshCalendar(currentCalendar);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
