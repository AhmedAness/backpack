package com.wasltec.provider.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.wasltec.provider.Adopters.Customes_Adapter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Key_value;
import com.wasltec.provider.model.Reservation;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {



    private Pay_list_Adapter adapter;
    private int customer_id;
    private ImageView mClose;
    private RecyclerView mPayList;
    private TextView mConfirm;
    private int booking_id;
    private Reservation booking_item;
    private TextView total;
    private TextView paid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        mClose = findViewById(R.id.close);
        mClose.setOnClickListener(v-> finish());
        mPayList = findViewById(R.id.pay_list);
        mConfirm = findViewById(R.id.confirm);
        total= findViewById(R.id.total);
        paid= findViewById(R.id.paid);
        booking_item = Parcels.unwrap(getIntent().getParcelableExtra("t"));
        customer_id= getIntent().getIntExtra("customer_id",-1);
        booking_id= getIntent().getIntExtra("booking_id",-1);
        if(customer_id==-1||booking_id==-1)
            Toast.makeText(Payment.this,"fail to get customer id or booking_id ",Toast.LENGTH_LONG).show();

        mPayList= findViewById(R.id.pay_list);
        pay_list_fn();
        total.setText("SAR "+booking_item.getBookingAmount());
        paid.setText("SAR "+"0");

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("Booking_id",booking_item.getId());
                    jsonObject.put("payment_method","1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URLManger.getInstance().getBookingPaid())
                        .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Payment.this).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build();
                finish();
            }
        });

    }

    private void pay_list_fn() {
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Payment.this, LinearLayoutManager.VERTICAL, false);
        mPayList.setLayoutManager(horizontalLayoutManagaer);

        ArrayList<Key_value> arr= new ArrayList<>();

      try{
          arr.add(new Key_value("SAR "+booking_item.getBookingAmount()/booking_item.getBookingTicketDetails().size()+" X "+booking_item.getBookingTicketDetails().size()+" individule","SAR "+booking_item.getBookingAmount()));
      }catch (Exception e)
      {
          Log.d("TAG", "pay_list_fn: "+e.getMessage());
      }
//       arr.add(new Key_value("Transportation","SAR 0"));
//       arr.add(new Key_value("Food & Bevirgase","SAR 0"));

        adapter = new Pay_list_Adapter(Payment.this,customer_id,arr);

        mPayList.setAdapter(adapter);
    }
}
