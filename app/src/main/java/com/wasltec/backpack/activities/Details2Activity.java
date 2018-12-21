package com.wasltec.backpack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.fragment.DateFragment;
import com.wasltec.backpack.fragment.GroupTripFragment;
import com.wasltec.backpack.fragment.IndividualFragment;
import com.wasltec.backpack.fragment.TicketsCounterFragment;
import com.wasltec.backpack.fragment.TimesFragment;
import com.wasltec.backpack.models.ActivityDetails.Avaliability;
import com.wasltec.backpack.models.BookingTicket;
import com.wasltec.backpack.models.KeyValue;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Details2Activity extends AppCompatActivity implements TicketsCounterFragment.Listener, DateFragment.Listener, TimesFragment.Listener {
    IndividualFragment fragment;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    GroupTripFragment fragment2;
    private ViewPager mViewPager;
    private TextView mCheckOut;
    private TextView mItemPrice2;
    private TextView mCounter;
    private Button mConfirmBtn;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        mItemPrice2 = findViewById(R.id.item_price2);
        mCounter = findViewById(R.id.counter);
        mConfirmBtn = findViewById(R.id.Confirm_btn);
        mConfirmBtn.setOnClickListener(v -> {

            if(Session.getInstance(this) != null) {
                int tmp = mViewPager.getCurrentItem();
                if (tmp == 1) {
                    if (GroupTripFragment.valid()) {
                        Cart.getInstance().setIsgroub(true);
                        Intent i = new Intent(v.getContext(), ReviewActivity.class);
                        i.putExtra("object", getIntent().getIntExtra("object", 0));
                        v.getContext().startActivity(i);
                    }


                } else {
                    if (IndividualFragment.valid()) {
                        Cart.getInstance().setIsgroub(false);
                        Intent i = new Intent(v.getContext(), ReviewActivity.class);
                        i.putExtra("object", getIntent().getIntExtra("object", 0));
                        v.getContext().startActivity(i);
                    }
                }
            }
            else
                Toast.makeText(this, "please log in or sign up first", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(v.getContext(), ReviewActivity.class);
//            i.putExtra("object", getIntent().getIntExtra("object", 0));
//            v.getContext().startActivity(i);

        });
        mCounter.setText(String.format(getString(R.string.counter_holder), sum));
        mItemPrice2.setText(String.format(getString(R.string.price_holder),  (0f)));
        Cart.getInstance().setTotal_price(0f);
    }

    private void create_book() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("activity_id", DetailsActivity.object.getId());
            jsonObject.put("avaliability_id", Cart.getInstance().getAvaliablityID().getId());
            jsonObject.put("booking_type", "1");
            jsonObject.put("full_group", mViewPager.getCurrentItem() != 0);
            jsonObject.put("booking_amount", /*cart.getAmount()*/ "1000");


            if ((mViewPager.getCurrentItem() == 0)) {
                JSONArray individualCategory = new JSONArray();


                for (KeyValue c : Cart.getInstance().getCategories()) {


                    JSONObject category = new JSONObject();
                    category.put("category_id", (int) c.getKey());
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

        AndroidNetworking.post(URLManager.getInstance().getCreateBooking())
                .addHeaders("Authorization", "bearer " + Session.getInstance(Details2Activity.this).getToken())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Cart.getInstance().setBookingid(response.has("bookingid") ? response.getInt("bookingid") : 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(Details2Activity.this, ReviewActivity.class);

                        i.putExtra("object", getIntent().getIntExtra("object", 0));

                        startActivity(i);

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Details2Activity.this, "No booking ", Toast.LENGTH_LONG).show();
                    }
                });


    }
    @Override
    protected void onResume() {
        super.onResume();

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


    @Override
    public void onClose(int price, int sum) {
        this.sum=sum;
        mCounter.setText(String.format(getString(R.string.counter_holder), sum));
        mItemPrice2.setText(String.format("%s SR",  price));
        Cart.getInstance().setTotal_price( price);
        fragment.onClose(sum, price);
        fragment2.onClose(sum, price);
    }

    @Override
    public void onClose(List<Avaliability> dates, int count) {
        try {
            fragment.onClose(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(dates.get(0).getActivityStart()));
            fragment2.onClose(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(dates.get(0).getActivityStart()));

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClose() {
        fragment.onClose();
        fragment2.onClose();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragment = new IndividualFragment();
            fragment2 = new GroupTripFragment();

        }


        @Override
        public Fragment getItem(int position) {
            return position == 0 ? fragment : fragment2;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
