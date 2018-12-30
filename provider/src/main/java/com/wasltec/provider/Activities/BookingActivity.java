package com.wasltec.provider.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.Adopters.Activity_list_overview;
import com.wasltec.provider.Adopters.Booking_dateAdapter;
import com.wasltec.provider.MapsActivity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.BookingTicketDetail;
import com.wasltec.provider.model.OverviewReturnOpj;
import com.wasltec.provider.model.Reservation;

import org.json.JSONArray;
import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.wasltec.provider.Activities.CustomisedCalender.getdate;
import static com.wasltec.provider.Activities.Home.toolbar;

public class BookingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Booking_dateAdapter bookingAdapter;
    private Toolbar mToolbar;
    private Spinner mDates;
    private SearchView mSearchBar;
    private ArrayList<String> dates;
    private ArrayList<String> dates2;
    public static List<Reservation> reservations2, reservations;
    private ArrayList<OverviewReturnOpj> alldata;
    private String Activtity_id;
    private String date;
    private ImageButton mScanner;
    private Gson gson;
    private Map<String,List<OverviewReturnOpj>> dataSet;
    String SelectedDate;
    RotateLoading loader;

    OverviewReturnOpj curr_activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_booking);


//        Activtity_id = getIntent().getStringExtra("id");

        try {
            Activtity_id=getIntent().getExtras().getString("id");
        }catch (Exception e){
            Log.e(TAG, "onCreate: "+e.getMessage());
        }
        SelectedDate = getIntent().getStringExtra("position");


        fulldata();


//        dates =getIntent().getStringArrayListExtra("dates");

//        dates2 =getIntent().getStringArrayListExtra("dates2");

//        alldata = getIntent().getParcelableArrayListExtra("data");





        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.bookingRecycler);
        mDates = findViewById(R.id.dates);
        mSearchBar = findViewById(R.id.search_bar);
        mScanner = findViewById(R.id.scanner);
        FloatingActionButton fab = findViewById(R.id.fab);

        Setrecyclerview();




        fab.setOnClickListener(view ->{
//            startActivity(new Intent(view.getContext(), Add_new_activity.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


            Long date=(new Date()).getTime();


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //to convert Date to String, use format method of SimpleDateFormat class.
            String strDate = dateFormat.format(date);


            Intent intent = new Intent(BookingActivity.this, Book_new.class);
            intent.putExtra("date1", strDate);
            intent.putExtra("date2",strDate);
            for (Reservation reservation : reservations) {
                if (reservation.getActivityStart().replace("T", " ").equalsIgnoreCase(dates2.get(mDates.getSelectedItemPosition()))) {
                    intent.putExtra("end", reservation.getActivityEnd().replace("T", " "));
                    break;
                }
            }
            intent.putExtra("id", Activtity_id);
//            OverviewReturnOpj k = getIntent().getParcelableExtra("data");

//            OverviewReturnOpj tmp = Parcels.unwrap(getIntent().getParcelableExtra("curractivity"));
//            intent.putExtra("data", alldata);
            intent.putExtra("curr_activity",(new Gson()).toJson(curr_activity));
            try {
                intent.putExtra("Tickets_left",reservations.get(0).getRemainingTickets());
            }catch (Exception e){
                intent.putExtra("Tickets_left",0);
            }


            startActivity(intent);
        });
        mScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Scaner.class);
                startActivity(i);
            }
        });
    }

    private void Setrecyclerview() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String date = dates.get(position);
                loader.start();
                AndroidNetworking.get(URLManger.getInstance().getGetReservations(Activtity_id,dates2.get(position)))
//                AndroidNetworking.get(URLManger.getInstance().getGetReservations(Activtity_id ))
                        .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(BookingActivity.this).getToken())
                        .build()
                        .getAsObjectList(Reservation.class, new ParsedRequestListener<List<Reservation>>() {
                            @Override
                            public void onResponse(List<Reservation> response) {
                                reservations = response;
                                reservations2=new ArrayList<>();
                                bookingAdapter = new Booking_dateAdapter(reservations, BookingActivity.this);
                                recyclerView.setAdapter(bookingAdapter);
                                if (loader.isStart())
                                    loader.stop();       }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                if (loader.isStart())
                                    loader.stop();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSearchBar.setOnCloseListener(() -> {
            Log.i("Search", "onCreate: close");
            bookingAdapter = new Booking_dateAdapter(reservations, BookingActivity.this);
            recyclerView.setAdapter(bookingAdapter);

            return true;
        });


    }

    private void fulldata() {

        loader = (RotateLoading) findViewById(R.id.rotateloading);
        loader.start();

        AndroidNetworking.get(URLManger.getInstance().getUpcomingActivity())
                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(BookingActivity.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Type listType = new TypeToken<List<OverviewReturnOpj>>() {
                        }.getType();
                        gson=new Gson();

                        ArrayList<OverviewReturnOpj> data = gson.fromJson(response.toString(), listType);

                        set_data (data);

                        reservations2 = new ArrayList<>();
                        mSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {

                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String query) {
                                reservations2.clear();
                                if (query.length() > 0) {
                                    if (reservations != null) {
                                        Log.v("search","inside"+" "+reservations.size());
                                        for (Reservation reservation : reservations) {
                                            Log.v("search",reservation.getname()+" "+query);

                                            if (reservation.getname().contains(query))
                                                reservations2.add(reservation);
                                            else {
                                                for (BookingTicketDetail bookingTicketDetail : reservation.getBookingTicketDetails()) {
                                                    if (bookingTicketDetail.getName().contains(query)){
                                                        reservations2.add(reservation);
                                                        break;
                                                    }    if (bookingTicketDetail.getNameOfGroup().contains(query)){
                                                        reservations2.add(reservation);
                                                        break;
                                                    }  if (bookingTicketDetail.getNameOfGroup().contains(query)){
                                                        reservations2.add(reservation);
                                                        break;
                                                    } if (bookingTicketDetail.getMail().contains(query)){
                                                        reservations2.add(reservation);
                                                        break;
                                                    }
                                                    else if (bookingTicketDetail.getMobile().contains(query)) {
                                                        reservations2.add(reservation);
                                                        break;
                                                    } else if (String.valueOf(bookingTicketDetail.getTicketNumber()).contains(query)) {
                                                        reservations2.add(reservation);
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        bookingAdapter = new Booking_dateAdapter(reservations2, BookingActivity.this);
                                        recyclerView.setAdapter(bookingAdapter);

                                    }
                                    return true;
                                }
                                if (query.length()==0){

                                    bookingAdapter = new Booking_dateAdapter(reservations, BookingActivity.this);
                                    recyclerView.setAdapter(bookingAdapter);
                                    return true;
                                }
                                return false;
                            }
                        });

                        mDates.setAdapter(new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_list_item_1, dates));

                        mDates.setSelection(dates.indexOf(SelectedDate));
                        if (loader.isStart())
                            loader.stop();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getMessage());
                        if (loader.isStart())
                            loader.stop();
                    }
                });

    }

    private void set_data( ArrayList<OverviewReturnOpj> data ) {

        dataSet= new HashMap<>();

        dates= new ArrayList<>();
        dates2= new ArrayList<>();
            alldata=data;

            for (int i = 0; i < data.size(); i++) {
                if (dataSet.containsKey(data.get(i).getActivity_Start_trim()))
                    dataSet.get(data.get(i).getActivity_Start_trim()).add(data.get(i));
                else {

                    dates.add(data.get(i).getActivity_Start());
                    dates2.add(data.get(i).getActivity_Start2());
                    List<OverviewReturnOpj> tmp = new ArrayList<>();
                    tmp .add(data.get(i));
                    dataSet.put(data.get(i).getActivity_Start_trim(),tmp);
                }
                if (data.get(i).getId()==Integer.parseInt(Activtity_id)){
                    curr_activity=data.get(i);
                }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==5){
            if (resultCode==RESULT_OK){
                Long date=data.getLongExtra("daydate",0);


                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //to convert Date to String, use format method of SimpleDateFormat class.
                String strDate = dateFormat.format(date);


                Intent intent = new Intent(BookingActivity.this, Book_new.class);
                intent.putExtra("date1", strDate);
                intent.putExtra("date2",strDate);
                for (Reservation reservation : reservations) {
                    if (reservation.getActivityStart().replace("T", " ").equalsIgnoreCase(dates2.get(mDates.getSelectedItemPosition()))) {
                        intent.putExtra("end", reservation.getActivityEnd().replace("T", " "));
                        break;
                    }
                }
                intent.putExtra("id", Activtity_id);
//            OverviewReturnOpj k = getIntent().getParcelableExtra("data");

//            OverviewReturnOpj tmp = Parcels.unwrap(getIntent().getParcelableExtra("curractivity"));
//            intent.putExtra("data", alldata);
                intent.putExtra("curr_activity",(new Gson()).toJson(curr_activity));


                startActivity(intent);



            }
        }

    }

    // the action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onResume() {
        toolbar.setTitle(R.string.overview);
        fulldata();
        Setrecyclerview();
        super.onResume();
    }


}
