package com.wasltec.backpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.Adapters.Slider_Adopter;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.activities.MainActivity;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.BookingDetails.BookingDetailsModel;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class QRActivity extends AppCompatActivity {

    private TextView mTicketNumber;
    private TextView mTripName;
    private TextView mTripProvider;
    private TextView mDate;
    private TextView mTime;
    private TextView mTextView11;
    private TextView confirm_btn;

    private ActivityDetail object;


    private static final Integer[] XMEN = {R.drawable.backpack_logo_white_52x78,
            R.drawable.camera_icon_profile,
            R.drawable.backpack_logo_white_52x78,
            R.drawable.activity_period_icon,
            R.drawable.backpack_logo_white_52x78};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        init();
        object = DetailsActivity.object;


        toolbar.setTitle(Session.getInstance(QRActivity.this).getUser().getName());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        confirm_btn=findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(QRActivity.this, MainActivity.class);
// set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });


    }


    private void init() {


        AndroidNetworking.get(URLManager.getInstance().getGetBookingDetails("" + Cart.getInstance().getBookingid()))
                .addHeaders("Authorization", "bearer " + Session.getInstance(QRActivity.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<BookingDetailsModel>>() {
                        }.getType();

//                        items = gson.fromJson(response.toString(), listType);
                        Gson gson = new Gson();
                        List<BookingDetailsModel> bookingDetailsModels = gson.fromJson(response.toString(), listType);

                        mPager = (ViewPager) findViewById(R.id.pager);
                        mPager.setAdapter(new Slider_Adopter(QRActivity.this, bookingDetailsModels));
                        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                        indicator.setViewPager(mPager);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

}

