package com.wasltec.provider.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wasltec.provider.Adopters.AddonesDialogAdapter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityAddOn;
import com.wasltec.provider.model.ActivityCalenderModel;
import com.wasltec.provider.model.ActivityDetailsReturnObj;
import com.wasltec.provider.model.IndividualCategory;
import com.wasltec.provider.model.OverviewReturnOpj;
import com.wasltec.provider.model.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class Book_new extends AppCompatActivity {

    private static final String TAG = "book_now";
    private TextView mDate;
    private TextView mTime;
    private TextView book_btn;
    private TextView mTicketLeft;
    private TextView mPriceCount;
    private TextView mTicketCount;
    private TextView dtest;
    private Date Startdate;
    private Date Startdate2;
    private OverviewReturnOpj data;
    int count = 0;
    private LinearLayout mContainer;
    private LinearLayout mPriceCounter;
    private LinearLayout mindividual_groups;
    private ImageView mClose;
    private ACProgressPie dialog;
    private CheckBox mGroupCheckbox;
    private String Activtity_id;
    private AddonesDialogAdapter addonesAdapter;
    List<ActivityAddOn> addOnList ;
    List<View> views;
    int booking_type=1;
    private Gson gson;
    private ActivityDetailsReturnObj activityDetails;
    private int paymentmethod=1;
    private int ticket_number=100;
    LinearLayout group_ticket;
    public static float total_price=0;
    EditText mGname,mGPhone,mGEmail,nameOfGroup,numOfGroup;
    LinearLayout mgroup_contamer;
    LinearLayout book_for_group;
    Typeface myfont;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_now_layout);


        String date1 = getIntent().getStringExtra("date1");
        String date2 = getIntent().getStringExtra("end");
        Activtity_id = getIntent().getStringExtra("id");


        Type listType = new TypeToken<OverviewReturnOpj>() {
        }.getType();
        data =(new Gson()).fromJson(getIntent().getStringExtra("curr_activity"),listType);
//        myfont.createFromAsset(this.getAssets(),"fonts/Lucida-Grande_28952.ttf");
//        dtest=findViewById(R.id.dtest);
//        dtest.setTypeface(myfont);
        initgroup_views();
        mClose.setOnClickListener(v -> finish());



        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomisedCalender.getdate=true;
                Intent intent=new Intent(Book_new.this, CustomisedCalender.class);
                intent.putExtra("Activity_id",Activtity_id);
                startActivityForResult(intent,5);
            }
        });


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        dialog = new ACProgressPie.Builder(this)
                .ringColor(Color.WHITE)
                .pieColor(Color.MAGENTA)
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();

        try {
            Startdate = format.parse(date1.replace("T", " "));
            Startdate2 = format.parse(date2.replace("T", " "));
        } catch (ParseException e) {
            Startdate = new Date();
//            e.printStackTrace();
        }catch (Exception e){
            Startdate = new Date();
            Startdate2 = new Date();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyy");
        DateFormat dateFormat2 = new SimpleDateFormat("hh:mm a");

        String strDate = dateFormat.format(Startdate);


        mDate.setText(dateFormat.format(  Startdate ));

        mTime.setText(dateFormat2.format(Startdate) + "-" + dateFormat2.format(Startdate2));
        mTicketLeft = findViewById(R.id.ticket_left);
        book_for_group = findViewById(R.id.book_for_group);

        try {
            mTicketLeft.setText(getIntent().getExtras().getInt("Tickets_left") + " tickets left");
        }catch (Exception e){
            mTicketLeft.setVisibility(View.GONE);
        }



        dialog.show();
        mGroupCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    mContainer.setVisibility(View.GONE);
                    group_ticket.setVisibility(View.VISIBLE);

                    booking_type=0;
                }else
                {
                    mContainer.setVisibility(View.VISIBLE);
                    group_ticket.setVisibility(View.GONE);
                    booking_type=1;
                }
            }
        });

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (data.getTotal_tickets()>0){

                if (booking_type!=0){
                    for (int i = 0; i < mContainer.getChildCount(); i++) {
                        View view = mContainer.getChildAt(i);
                        EditText mname = view.findViewById(R.id.user_name);
                        EditText mPhone = view.findViewById(R.id.phone);
                        EditText mEmail = view.findViewById(R.id.email);
                        if (mname.getText().length()<=0){
                            mname.setError("This field is required");
                            break;
                        }else if (mPhone.getText().length()<=0){
                            mPhone.setError("This field is required");
                            break;
                        }else if (mEmail.getText().length()<=0){
                            mEmail.setError("This field is required");
                            break;
                        }
                        else if (i==mContainer.getChildCount()-1){
                            create_book ();
                        }
                    }
                    }else {
                    if (mGname.getText().length()<=0){
                        mGname.setError("This field is required");
                    }else if (mGPhone.getText().length()<=0){
                        mGPhone.setError("This field is required");
                    }else if (mGEmail.getText().length()<=0){
                        mGEmail.setError("This field is required");
                    }else if (nameOfGroup.getText().length()<=0){
                        nameOfGroup.setError("This field is required");
                    }else if (numOfGroup.getText().length()<=0){
                        numOfGroup.setError("This field is required");
                    } else {
                        create_book ();
                    }
                }
//                Toast.makeText(Book_new.this,"booking click ",Toast.LENGTH_LONG).show();
//            }else {
//                    Toast.makeText(Book_new.this, "There's no tickets left", Toast.LENGTH_SHORT).show();
//                }
            }

        });
//        AndroidNetworking.get(URLManger.getInstance().getIndividualCategory(Activtity_id))
//                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(this).getToken())
//                .build().getAsObjectList(Price_per_individual.class, new ParsedRequestListener<List<Price_per_individual>>() {
//            @Override
//            public void onResponse(List<Price_per_individual> response) {
////                response.add(new Price_per_individual("Adults",750,1,750,0));
////                response.add(new Price_per_individual("child",150,1,750,0));
//
//                for (Price_per_individual price_per_individual : response) {
//                    addView2(price_per_individual);
//                }
//                dialog.hide();
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
//
//                dialog.hide();
//
//            }
//        });



        AndroidNetworking.get(URLManger.getInstance().getGetActivityDetails(""+Activtity_id))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Book_new.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        gson = new Gson();
                        Type listType = new TypeToken<ActivityDetailsReturnObj>() {
                        }.getType();
                        try {
                            activityDetails = gson.fromJson(response.get(0).toString(), listType);
                            addOnList= activityDetails.getActivityAddOns();

                            for (ActivityAddOn price_per_individual : addOnList) {
                                addView2group(price_per_individual);
                            }
                            for (IndividualCategory price_per_individual : activityDetails.getIndividualCategories()) {
                                addView2(price_per_individual);
                            }
                        } catch (JSONException e) {
                            activityDetails = new ActivityDetailsReturnObj();
                            e.printStackTrace();
                        } catch (Exception e) {
                            activityDetails = new ActivityDetailsReturnObj();
                            e.printStackTrace();
                        }
                        dialog.hide();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError.getMessage());
                        dialog.hide();
                    }
                });



//        AndroidNetworking.get(URLManger.getInstance().getGetAllAdd_Ons())//"http://backpackapis.wasltec.org/api/add_ons/GetAllAdd_Ons"
//                .build().getAsObjectList(ActivityAddOn.class, new ParsedRequestListener<List<ActivityAddOn>>() {
//            @Override
//            public void onResponse(List<ActivityAddOn> response) {
//
//                addOnList=response;
//
//            }
//
//
//            @Override
//            public void onError(ANError anError) {
//
//                Toast.makeText(Book_new.this,""+anError.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });

        CustomisedCalender.getdate=true;
        Intent intent=new Intent(Book_new.this, CustomisedCalender.class);
        intent.putExtra("Activity_id",Activtity_id);
        startActivityForResult(intent,5);


    }


    private void initgroup_views() {

        mGroupCheckbox = findViewById(R.id.group_checkbox);
        book_btn = findViewById(R.id.book_btn);
        mDate = findViewById(R.id.date);
        group_ticket = findViewById(R.id.group_ticket);
        mContainer = findViewById(R.id.container);
        mPriceCounter = findViewById(R.id.price_counter);
        mindividual_groups = findViewById(R.id.individual_groups);
        mClose = findViewById(R.id.close);
        mTime = findViewById(R.id.time);
        mPriceCount = findViewById(R.id.price_count);
        mTicketCount = findViewById(R.id.ticket_count);


          mGname =  findViewById(R.id.user_name);
          mGPhone = findViewById(R.id.phone);
          mGEmail =  findViewById(R.id.email);
          nameOfGroup =  findViewById(R.id.group_name);
          numOfGroup =  findViewById(R.id.group_number);
        mgroup_contamer = findViewById(R.id.group_contamer);

    }

    private void create_book() {
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("activity_id",Activtity_id);
            jsonObject.put("avaliability_id",getavailbolty_id());
            jsonObject.put("booking_type","1");
            jsonObject.put("full_group", booking_type == 0);

            jsonObject.put("booking_amount", Get_booking_amount());
            jsonObject.put("is_paid", false);


            jsonObject.put("bookingIndividualCategoryCapacity", Get_bookingIndividualCategoryCapacity());
            if (booking_type!=0)
            jsonObject.put("bookingTicket", Get_bookingTicket());
            else
            jsonObject.put("bookingTicket", Get_bookingGroupTicket());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(URLManger.getInstance().getCreateBooking())
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Book_new.this).getToken())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(Book_new.this,"Done " +response.toString(),Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(Book_new.this,anError.getMessage(),Toast.LENGTH_LONG).show();
//                        finish();
                    }
                });


    }

    private JSONArray Get_bookingTicket() {

        JSONArray jsonElements = new JSONArray();
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            View view = mContainer.getChildAt(i);


            EditText mname = view.findViewById(R.id.user_name);
            EditText mPhone = view.findViewById(R.id.phone);
            EditText mEmail = view.findViewById(R.id.email);
            if (mname.getText().length()<=0){
                mname.setError("Enter Name");
                break;
            }
            RecyclerView recyclerView = view.findViewById(R.id.addons_list);

            try {
                JSONObject jsonObject1 = new JSONObject();

                jsonObject1.put("ticket_number",""+ticket_number++);
                jsonObject1.put("name", mname.getText().toString());
                jsonObject1.put("mail", mEmail.getText().toString());
                jsonObject1.put("mobile", mPhone.getText().toString());
                jsonObject1.put("primaryTicket",i==0);
                jsonObject1.put("ticket_reviewd",false);
                jsonObject1.put("ticket_checked_in",false);
                jsonObject1.put("ticket_cancelled",false);

                AddonesDialogAdapter tmp= (AddonesDialogAdapter) recyclerView.getAdapter();
                tmp.getPrices();
                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < tmp.getPrices().size(); j++) {
                    JSONObject jsonObject = new JSONObject();
                    if (tmp.getPrices().get(j).isSelected())
                    {
                        jsonObject.put("Addons_id",tmp.getPrices().get(j).getActivityAddons_id());
                        jsonArray.put(jsonObject);
                    }

                }
                jsonObject1.put("Booking_Ticket_AddonsModel",jsonArray);


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

                jsonObject1.put("ticket_number",""+ticket_number++);
                jsonObject1.put("name", mGname.getText().toString());
                jsonObject1.put("mail", mGEmail.getText().toString());
                jsonObject1.put("mobile", mGPhone.getText().toString());

                jsonObject1.put("isGroupTicket",  1);
                jsonObject1.put("nameOfGroup",  nameOfGroup.getText().toString());
                jsonObject1.put("numOfGroup",  numOfGroup.getText().toString());


                jsonObject1.put("primaryTicket",true);
                jsonObject1.put("ticket_reviewd",false);
                jsonObject1.put("ticket_checked_in",false);
                jsonObject1.put("ticket_cancelled",false);



                jsonObject1.put("Booking_Ticket_AddonsModel",Get_bookingaddons_groupCapacity());




                jsonElements.put(jsonObject1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return jsonElements;
    }

    private JSONArray Get_bookingIndividualCategoryCapacity() {

        JSONArray jsonElements = new JSONArray();
        for (int i = 0; i < mindividual_groups.getChildCount(); i++) {
            View view = mindividual_groups.getChildAt(i);
            TextView mCount = view.findViewById(R.id.count);
            TextView mcategory_id = view.findViewById(R.id.category_id);

            if (Integer.parseInt(mCount.getText().toString())>0)
            {

                try {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("category_id", mcategory_id.getText().toString());
                    jsonObject1.put("count", mCount.getText().toString());
                    jsonElements.put(jsonObject1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonElements;
    }
    private JSONArray Get_bookingaddons_groupCapacity() {

        JSONArray jsonElements = new JSONArray();
        for (int i = 0; i < mgroup_contamer.getChildCount(); i++) {
            View view = mgroup_contamer.getChildAt(i);
            TextView mCount = view.findViewById(R.id.count);
            TextView mcategory_id = view.findViewById(R.id.category_id);

            if (Integer.parseInt(mCount.getText().toString())>0)
            {

                try {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("Addons_id", mcategory_id.getText().toString());
                    jsonObject1.put("addonCount", mCount.getText().toString());
                    jsonElements.put(jsonObject1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonElements;
    }

    private String Get_booking_amount() {

        return ""+total_price;
    }

    private int getavailbolty_id() {
        for (int i = 0; i < activityDetails.getAvaliabilities().size(); i++) {
            //TODO date formate
            if(data.getActivity_StartwithT().equals(activityDetails.getAvaliabilities().get(i).getActivitystart()))
            {
                return activityDetails.getAvaliabilities().get(i).getId();
            }
        }
        return activityDetails.getAvaliabilities().get(activityDetails.getAvaliabilities().size()-1).getId();
    }

    public void addView(int count) {
        View view = LayoutInflater.from(this).inflate(R.layout.backpacker_layout, null, false);
        LinearLayout.LayoutParams layoutInflater = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setMargins(0, 10, 0, 0);
        view.setLayoutParams(layoutInflater);
        EditText mname = view.findViewById(R.id.user_name);
        EditText mPhone = view.findViewById(R.id.phone);
        EditText mEmail = view.findViewById(R.id.email);


        RecyclerView recyclerView = view.findViewById(R.id.addons_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(Book_new.this));
//        addonesAdapter = new AddonesDialogAdapter(addOnList,true,Book_new.this );
        recyclerView.setAdapter(new AddonesDialogAdapter(addOnList,true,false,Book_new.this ));

        TextView headder = view.findViewById(R.id.header);
        headder.setText(getString(R.string.backpackernumber) + count);
        mContainer.addView(view);

    }

    public void addView2(IndividualCategory item) {
        View view = LayoutInflater.from(this).inflate(R.layout.price_book_now_layout, null, false);
        LinearLayout.LayoutParams layoutInflater = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setMargins(0, 20, 0, 0);
        view.setLayoutParams(layoutInflater);
        TextView mText = view.findViewById(R.id.text);
        ImageView mDec = view.findViewById(R.id.dec);
        ImageView mInc = view.findViewById(R.id.inc);
        TextView mCount = view.findViewById(R.id.count);
        TextView mcategory_id = view.findViewById(R.id.category_id);
        mcategory_id.setText(""+item.getId());
        mText.setText(String.format("%s(%s)", item.getName(), item.getPrice()));
        view.setTag(item.getPrice());
        mCount.setTag(0);
        mDec.setOnClickListener(v -> {
            int c = (int) mCount.getTag();

            if (count > 0 && c > 0) {
                count--;

                mContainer.removeAllViews();
                for (int i = 0; i < count; i++) {
                    addView(i);
                }
//                mPriceCount.setText(item.getPrice() * count + " ");
                total_price-=item.getPrice();
                mPriceCount.setText("SR "+total_price);
                c--;
                mCount.setTag(c);
                mCount.setText(String.valueOf(c));
                mTicketCount.setText(count + " Tickets");

            }

        });
        mInc.setOnClickListener(v -> {
            int c = (int) mCount.getTag();

            count++;

            addView(count);
            total_price+=item.getPrice();
//            mPriceCount.setText(item.getPrice() * count + " ");
            mPriceCount.setText("SR "+total_price);
            c++;
            mCount.setTag(c);
            mCount.setText(String.valueOf(c));
            mTicketCount.setText(String.format("%d Tickets", count));


        });
//        mPriceCounter.addView(view);
        mindividual_groups.addView(view);

    }
    public void addView2group(ActivityAddOn item) {
        View view = LayoutInflater.from(this).inflate(R.layout.price_book_now_layout, null, false);
        LinearLayout.LayoutParams layoutInflater = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setMargins(0, 20, 0, 0);
        view.setLayoutParams(layoutInflater);
        TextView mText = view.findViewById(R.id.text);
        ImageView mDec = view.findViewById(R.id.dec);
        ImageView mInc = view.findViewById(R.id.inc);
        TextView mCount = view.findViewById(R.id.count);
        TextView mcategory_id = view.findViewById(R.id.category_id);
        mcategory_id.setText(""+item.getId());
        mText.setText(String.format("%s(%s)", item.getName(), item.getPrice()));
        view.setTag(item.getPrice());
        mCount.setTag(0);
        mDec.setOnClickListener(v -> {
            int c = (int) mCount.getTag();

            if (count > 0 && c > 0) {
                count--;


                c--;
                mCount.setTag(c);
                mCount.setText(String.valueOf(c));


            }

        });
        mInc.setOnClickListener(v -> {
            int c = (int) mCount.getTag();

            count++;


            c++;
            mCount.setTag(c);
            mCount.setText(String.valueOf(c));


        });

        mgroup_contamer.addView(view);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==5){
            if (resultCode==RESULT_OK){
                Long date=data.getLongExtra("daydate",0);

                ActivityCalenderModel activityCalenderModel=new Gson().fromJson( data.getStringExtra("Avail_data"),new TypeToken<ActivityCalenderModel>(){}.getType());

                if(activityCalenderModel!=null)
                {
                    if(activityCalenderModel.getIsForGroup()==0)
                    book_for_group.setVisibility(View.GONE);
                    else if(activityCalenderModel.getIsForGroup()==1)
                    mindividual_groups.setVisibility(View.GONE);

                    mTicketLeft.setText(String.format(getResources().getString(R.string.tickets_left),activityCalenderModel.getTotalTickets(),activityCalenderModel.getTotalCapacity()));
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");

                //to convert Date to String, use format method of SimpleDateFormat class.
                String strDate = dateFormat.format(date);

                mDate.setText(strDate);
            }
        }

    }


}
