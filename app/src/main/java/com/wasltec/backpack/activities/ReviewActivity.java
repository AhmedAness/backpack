package com.wasltec.backpack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.ActivityDetails.IndividualCategory;
import com.wasltec.backpack.models.KeyValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ReviewActivity extends AppCompatActivity {

    private ImageView mActivityImg;
    private TextView mItemName;
    private TextView mItemPrice;

    private Button mConfirmBtn;
    private TextView mLocation;
    private TextView mLocationCountry;
    private TextView mOrganizationName;
    private TextView mDateTxt;
    private TextView mTicketsCount;
    private TextView mTimeTxt;
    private TextView mItemPrice2;
    private TextView mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        object = DataManager.getInstance().getItem(getIntent().getIntExtra("object", 0));

        mActivityImg = findViewById(R.id.activity_img);
        mItemName = findViewById(R.id.item_name);
        mLocation = findViewById(R.id.location);
        mLocationCountry = findViewById(R.id.location_country);
        mOrganizationName = findViewById(R.id.organization_name);
        mDateTxt = findViewById(R.id.date_txt);
        mTicketsCount = findViewById(R.id.tickets_count);
        mTimeTxt = findViewById(R.id.time_txt);
        mItemPrice2 = findViewById(R.id.item_price2);
        mCounter = findViewById(R.id.counter);
        mConfirmBtn = findViewById(R.id.Confirm_btn);

//        int sum = Cart.getInstance().getAdultCount() + Cart.getInstance().getChildCount();
        int sum = 0;

        sum = Cart.getInstance().getTickets().size();

        mItemPrice2.setText(String.format("%s SR", Cart.getInstance().getTotal_price()));
        if(Cart.getInstance().isIsgroub())
            mCounter.setText("");
        else
            mCounter.setText(String.format(getString(R.string.counter_holder), sum));

        for (com.wasltec.backpack.models.ActivityPhoto activityPhoto: DetailsActivity.object.getActivityPhotos())
        {
            if (activityPhoto.getCoverPhoto())
                Glide.with(this).load(activityPhoto.getUrl()).into(mActivityImg);

        }
//        Glide.with(this).load(DetailsActivity.object.getCover()).into(mActivityImg);
        mItemName.setText(DetailsActivity.object.getTitle());
        if (Cart.getInstance().getStart() != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");

            mDateTxt.setText(dateFormat.format(Cart.getInstance().getStart()));

        }

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");


        DateFormat format = new SimpleDateFormat("yyyy-dd-MM'T'hh:mm:ss", Locale.ENGLISH);
//        Date date1,date2;
//
//        try {
//              date1 = format.parse(Cart.getInstance().getAvaliablityID().getActivityStart().replace("T", " "));
//              date2 = format.parse(Cart.getInstance().getAvaliablityID().getActivityEnd().replace("T", " "));
//        } catch (ParseException e) {
//            e.printStackTrace();
//            date1= new Date();
//            date2= new Date();
//        }


        String Time_Txt = "";
        try {
            Time_Txt = Cart.getInstance().getAvaliablityID() != null ? new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(Cart.getInstance().getAvaliablityID().getActivityStart())) +
                    " - " + new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(Cart.getInstance().getAvaliablityID().getActivityEnd())) :
                    "No Date Selected";
        } catch (ParseException e) {
            e.printStackTrace();
        }


        mTimeTxt.setText(String.format("%s", Time_Txt));

        if (!Cart.getInstance().isIsgroub()) {
            int count = 0;
            for (KeyValue entry : Cart.getInstance().getCategories()) {
                List<IndividualCategory> list = DetailsActivity.object.getIndividualCategories();
                for (int i = 0; i < list.size(); i++) {
                    if (Objects.equals(list.get(i).getId(), entry.getKey())) {
                        if (count != 0)
                            mTicketsCount.append("\n");
                        mTicketsCount.append(list.get(i).getName() + " : ");
                        mTicketsCount.append(String.valueOf(entry.getValue()));
                        count++;
                    }
                }
            }
        } else {
            mTicketsCount.setText("Group");
        }


        mConfirmBtn.setOnClickListener(v ->

        {
            Intent i = new Intent(v.getContext(), ReviewRulesActivity.class);
            i.putExtra("object", getIntent().getIntExtra("object", 0));
            v.getContext().startActivity(i);


        });

    }

}
