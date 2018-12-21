package com.wasltec.backpack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.wasltec.backpack.Adapters.Price_Adapter;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.QRActivity;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.models.ActivityDetails.ActivityAddOn;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.ActivityDetails.ActivityPhoto;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.BookingTicket;
import com.wasltec.backpack.models.Items;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    private Button mConfirmBtn;
    private CardView mCard;
    private ImageView mActivityImg;
    private TextView mItemName;
    private TextView mLocation;
    private TextView mLocationCountry;
    private TextView mOrganizationName;
    private RecyclerView mPricesList;
    private Button mPromoCodeBtn;
    private TextView mTotal;
    private TextView mTimeTxt;
    private Button mViewTicket;
    private ActivityDetail object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        object =DetailsActivity.object;


        mConfirmBtn = findViewById(R.id.Confirm_btn);
        mCard = findViewById(R.id.card);
        mConfirmBtn.setOnClickListener(v -> {
            toolbar.setTitle("Home");
            mCard.setVisibility(View.GONE);
            mViewTicket.setVisibility(View.VISIBLE);

        });

        mActivityImg = findViewById(R.id.activity_img);
        mItemName = findViewById(R.id.item_name);
        mLocation = findViewById(R.id.location);
        mLocationCountry = findViewById(R.id.location_country);
        mOrganizationName = findViewById(R.id.organization_name);
        mPricesList = findViewById(R.id.prices_list);
        mPromoCodeBtn = findViewById(R.id.Promo_code_btn);
        mTotal = findViewById(R.id.total);

        for (com.wasltec.backpack.models.ActivityPhoto activityPhoto :object.getActivityPhotos())
            if (activityPhoto.getCoverPhoto())
                Glide.with(this).load(activityPhoto.getUrl()).into(mActivityImg);
        mItemName.setText(object.getTitle());
        mLocation.setText(object.getActivityLocation());
//        mLocationCountry.setText(object.getLocationCountry());
        mOrganizationName.setText(object.getProvider().getUserName());
        mPricesList.setLayoutManager(new LinearLayoutManager(this));
        List<Items> items = new ArrayList<>();

        if(!Cart.getInstance().isIsgroub()) {
            for (ActivityAddOn addone : object.getActivityAddOns()) {
                int count = 0;
                for (int i = 0; i < Cart.getInstance().getTickets().size(); i++) {
                    for (int j = 0; j < Cart.getInstance().getTickets().get(i).getBookingTicketAddonsModel().size(); j++)
                        if (i == 0) {
                            if (Objects.equals(Cart.getInstance().getTickets().get(i).getBookingTicketAddonsModel().get(j).getAddonsId(), addone.getActivityAddonsId().toString()))
                                count++;
                        } else {
                            if (Objects.equals(Cart.getInstance().getTickets().get(i).getBookingTicketAddonsModel().get(j).getAddonsId(), addone.getAddonsId().toString()))
                                count++;
                        }
                }
                items.add(new Items(count + " x " + addone.getName(), count * addone.getPrice()));
            }


            items.add(0, new Items(Cart.getInstance().getTickets().size() + " individual x " + object.getFirstIndividualPrice(),
                    Cart.getInstance().getTickets().size() * object.getFirstIndividualPrice()));
        }
        else {
            items.add(0, new Items("Ticket", object.getGroupPrice()));
            items.add(1, new Items("Addons", Cart.getInstance().getTotal_price() - object.getGroupPrice()));
        }

        mPricesList.setAdapter(new Price_Adapter(this, items));
        mTotal.setText(String.valueOf(Cart.getInstance().getTotal_price()));

        mViewTicket = findViewById(R.id.view_ticket);
        mViewTicket.setOnClickListener(v -> {
            Intent i = new Intent(this, QRActivity.class);
            i.putExtra("object", getIntent().getIntExtra("object", 0));
            startActivity(i);
        });
    }

}
