package com.wasltec.backpack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.backpack.Adapters.RulesAdapter;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.BookingTicket;
import com.wasltec.backpack.models.KeyValue;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewRulesActivity extends AppCompatActivity {


    private RecyclerView mRulesList;
    private TextView mItemPrice2;
    private TextView mCounter;
    private Button mConfirmBtn;
    JSONObject obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rules);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        object = DataManager.getInstance().getItem(getIntent().getIntExtra("object", 0));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRulesList = findViewById(R.id.rules_list);
        mItemPrice2 = findViewById(R.id.item_price2);
        mCounter = findViewById(R.id.counter);
        mConfirmBtn = findViewById(R.id.Confirm_btn);
        mConfirmBtn.setOnClickListener(v ->

        {
            create_book();

        });
        try {


        int sum = Cart.getInstance().getTickets().size();

        if(Cart.getInstance().isIsgroub())
            mCounter.setText("");
        else
            mCounter.setText(String.format("For %d individual", sum));
        }catch (Exception e){
            mCounter.setText(String.format("For %d individual", 0));
        }
        mItemPrice2.setText(String.format("%s SR", Cart.getInstance().getTotal_price()));
        mRulesList.setLayoutManager(new LinearLayoutManager(this));
        mRulesList.setAdapter(new RulesAdapter(DetailsActivity.object.getActivityRules()));

    }


    private void create_book() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("activity_id", DetailsActivity.object.getId());
            jsonObject.put("avaliability_id", Cart.getInstance().getAvaliablityID().getId());
            jsonObject.put("booking_type", "1");
            jsonObject.put("full_group", Cart.getInstance().isIsgroub());
            jsonObject.put("booking_amount", Cart.getInstance().getTotal_price());


            if (!(Cart.getInstance().isIsgroub())) {
                JSONArray individualCategory = new JSONArray();


                ArrayList<KeyValue> list = Cart.getInstance().getCategories();
                for (KeyValue c : list) {
                    JSONObject category = new JSONObject();
                    category.put("category_id", c.getKey());
                    category.put("count", c.getValue());
                    individualCategory.put(category);

                }
                jsonObject.put("bookingIndividualCategoryCapacity", individualCategory);
                jsonObject.put("bookingTicket", Get_bookingTicket());
            } else
                jsonObject.put("bookingTicket", Get_bookingGroupTicket());

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String jsonn = "{\n" + "\t\"activity_id\":\"277\",\n" + "    \"avaliability_id\":\"2363\",\n" + "    \"booking_type\":\"1\",\n" + "    \"full_group\":false,\n" + "    \"booking_amount\":\"1000.00\",\n" + "    \"bookingIndividualCategoryCapacity\":[{\n" + "    \t\"category_id\":\"3\",\n" + "    \t\"count\":\"1\"\n" + "    },{\n" + "    \t\"category_id\":\"4\",\n" + "    \t\"count\":\"1\"\n" + "    }],\n" + "    \"bookingTicket\":[{\n" + "\t\t\n" + "\t\t\t\"name\":\"Eman\",\n" + "\t\t\t\"mail\":\"amanyShaker@gmail.com\",\n" + "\t\t\t\"mobile\":\"012548\",\n" + "\t\t\t\"primaryTicket\":true,\n" + "\t\t\t\"ticket_reviewd\":true,\n" + "\t\t\t\"ticket_checked_in\":true,\n" + "\t\t\t\"ticket_cancelled\":false,\n" + "\t\t\t\"category_id\":3,\n" + "\t\t\t\"Booking_Ticket_AddonsModel\":[\n" + "\t\t\t\t{\n" + "\t\t\t\t\t\"Addons_id\":\"1\"\n" + "\t\t\t\t}]\n" + "\t\t},{\n" + "\t\t\t\n" + "\t\t\t\"name\":\"Ahmed\",\n" + "\t\t\t\"mail\":\"ahmed@gmail.com\",\n" + "\t\t\t\"mobile\":\"0125489\",\n" + "\t\t\t\"primaryTicket\":true,\n" + "\t\t\t\"ticket_reviewd\":true,\n" + "\t\t\t\"ticket_checked_in\":true,\n" + "\t\t\t\"ticket_cancelled\":false,\n" + "\t\t\t\"category_id\":4,\n" + "\t\t\t\"Booking_Ticket_AddonsModel\":[\n" + "\t\t\t    {\n" + "\t\t\t\t\t\"Addons_id\":\"2\"\n" + "\t\t\t\t},\n" + "\t\t\t\t{\n" + "\t\t\t\t\t\"Addons_id\":\"1\"\n" + "\t\t\t\t}]\n" + "\t\t}\n" + "\t\t]\n" + "    \n" + "}";

//        try {
//
//            obj = new JSONObject(jsonn);
//
//            Log.d("My App", obj.toString());
//
//        } catch (Throwable t) {
//            Log.e("My App", "Could not parse malformed JSON: \"" + jsonn + "\"");
//        }
//        Log.d(PaymentActivity.class.getSimpleName(), jsonObject.toString());

        AndroidNetworking.post(URLManager.getInstance().getCreateBooking())
                .addHeaders("Authorization", "bearer " + Session.getInstance(ReviewRulesActivity.this).getToken())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                        if(response.has("status") && response.getInt("status") == 0)
                            Toast.makeText(ReviewRulesActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            Cart.getInstance().setBookingid(response.has("bookingid") ? response.getInt("bookingid") : 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(ReviewRulesActivity.this, PaymentActivity.class);
                        i.putExtra("object", getIntent().getIntExtra("object", 0));
                        startActivity(i);

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ReviewRulesActivity.this, "No booking ", Toast.LENGTH_LONG).show();
                    }
                });


    }

    private JSONArray Get_bookingTicket() {


        JSONArray jsonElements = new JSONArray();
        for (int i = 0; i < Cart.getInstance().getTickets().size(); i++) {


            BookingTicket tmp = Cart.getInstance().getTickets().get(i);

            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("name", tmp.getName());
                jsonObject1.put("mail", tmp.getMail());
                jsonObject1.put("mobile", tmp.getMobile());
                jsonObject1.put("category_id", tmp.getCategoryId());
                jsonObject1.put("primaryTicket", i == 0);
                jsonObject1.put("ticket_reviewd", false);
                jsonObject1.put("ticket_checked_in", false);
                jsonObject1.put("ticket_cancelled", false);


                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < tmp.getBookingTicketAddonsModel().size(); j++) {
                    JSONObject jsonObject = new JSONObject();


                    jsonObject.put("Addons_id", tmp.getBookingTicketAddonsModel().get(j).getAddonsId());
                    jsonArray.put(jsonObject);


                }
                jsonObject1.put("Booking_Ticket_AddonsModel", jsonArray);


                jsonElements.put(jsonObject1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return jsonElements;
    }

    private JSONArray Get_bookingGroupTicket() {
        JSONArray jsonElements = new JSONArray();

        try {
            JSONObject jsonObject1 = new JSONObject();

            BookingTicket tmp = Cart.getInstance().getTickets().get(0);

            jsonObject1.put("name", tmp.getName());
            jsonObject1.put("mail", tmp.getMail());
            jsonObject1.put("mobile", tmp.getMobile());
            jsonObject1.put("primaryTicket", true);
            jsonObject1.put("ticket_reviewd", false);
            jsonObject1.put("ticket_checked_in", false);
            jsonObject1.put("ticket_cancelled", false);
            jsonObject1.put("numOfGroup", tmp.getNumOfGroup());
            jsonObject1.put("nameOfGroup", tmp.getNameOfGroup());
            jsonObject1.put("isGroupTicket", 1);


            jsonObject1.put("Booking_Ticket_AddonsModel", Get_bookingaddons_groupCapacity(tmp));


            jsonElements.put(jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonElements;
    }

    private JSONArray Get_bookingaddons_groupCapacity(BookingTicket tmp) {
        JSONArray jsonArray = new JSONArray();


        for (int i = 0; i < tmp.getBookingTicketAddonsModel().size(); i++) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("Addons_id", "" + tmp.getBookingTicketAddonsModel().get(i).getAddonsId());
                jsonObject.put("addonCount", tmp.getBookingTicketAddonsModel().get(i).getCount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
