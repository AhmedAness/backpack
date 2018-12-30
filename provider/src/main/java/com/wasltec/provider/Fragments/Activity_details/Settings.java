package com.wasltec.provider.Fragments.Activity_details;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Activities.Home;
import com.wasltec.provider.Adopters.ActivityAvailability_adobter;
import com.wasltec.provider.Adopters.ActivityCapacity_adobter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;

import org.json.JSONObject;

import static com.wasltec.provider.Activities.Activity_details.activityDetails;

public class Settings extends Fragment {

    private View view;


    TextView ac_notice_in_advance,ac_booking_window,ac_individuals,ac_private_groups,ac_activity_length
            ,ac_total_capacity,ac_private_group,ac_private_Groups_price;

    GridLayout availability_list ,organizer_list ,admins_list ;
    Switch ac_status;
    GridLayout ac_booking_available_for;

    RecyclerView capacity_list,price_per_individual_list;
    private TextView mBookingPrefrencesEditBtn;
    private TextView mAvailabilityEditBtn;
    private TextView mLengthAndCapacityEditBtn;
    private TextView mPricingEditBtn;
    private TextView mOrganisersEditBtn;
    private ImageButton mBalanceSwitch;
    RotateLoading loader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.fragment_settings, container, false);


        init();
//        mPaymentMethodList = findViewById(R.id.payment_method_list);
        mBalanceSwitch.setOnClickListener(v -> {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            v.setSelected(!v.isSelected());

            String valid= String.valueOf("Are you sure you want to make your activity online ?");
            if (!v.isSelected()){
                valid= String.valueOf("Are you sure you want to take make activity offline ?");
            }

            loader = (RotateLoading) view.findViewById(R.id.rotateloading);
            loader.start();

            builder.setTitle("Change Status")
                    .setMessage(valid)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            AndroidNetworking.get(URLManger.getInstance().getChangeStatus(activityDetails.getId()+""))
                                    .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                                            if (loader.isStart())
                                                loader.stop();
                                        }

                                        @Override
                                        public void onError(ANError anError) {

                                            Toast.makeText(getActivity(),anError.getMessage(),Toast.LENGTH_LONG).show();
                                            if (loader.isStart())
                                                loader.stop();
                                        }
                                    });
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        });

        ac_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                String valid= String.valueOf("Are you sure you want to take your activity online ?");
                if (activityDetails.getStatus()){
                    valid= String.valueOf("Are you sure you want to take your activity offline ?");
                }
                loader.start();
                builder.setTitle("Change Status")
                        .setMessage(valid)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                AndroidNetworking.get(URLManger.getInstance().getChangeStatus(activityDetails.getId()+""))
                                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                                                if (loader.isStart())
                                                    loader.stop();
                                            }

                                            @Override
                                            public void onError(ANError anError) {

                                                Toast.makeText(getActivity(),anError.getMessage(),Toast.LENGTH_LONG).show();
                                                if (loader.isStart())
                                                    loader.stop();
                                            }
                                        });
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        return view;
    }

    private void init() {
        ac_status = view.findViewById(R.id.ac_status);

        ac_notice_in_advance = view.findViewById(R.id.ac_notice_in_advance);
        ac_booking_window = view.findViewById(R.id.ac_booking_window);
        ac_individuals = view.findViewById(R.id.ac_individuals);
        ac_private_groups = view.findViewById(R.id.ac_private_groups);
        ac_total_capacity = view.findViewById(R.id.ac_total_capacity);
        ac_activity_length = view.findViewById(R.id.ac_activity_length);
        ac_private_group = view.findViewById(R.id.ac_private_group);
        ac_private_Groups_price = view.findViewById(R.id.ac_private_Groups_price);


        ac_booking_available_for = view.findViewById(R.id.booking_available_for);
        availability_list = view.findViewById(R.id.availability_list);
        organizer_list = view.findViewById(R.id.organizer_list);
        admins_list = view.findViewById(R.id.admins_list);
        capacity_list = view.findViewById(R.id.capacity_list);
        price_per_individual_list = view.findViewById(R.id.price_per_individual_list);
        mBalanceSwitch = view.findViewById(R.id.balance_switch);


        ac_status.setChecked(activityDetails.getStatus());
        mBalanceSwitch.setSelected(activityDetails.getStatus());


        ac_notice_in_advance.setText(activityDetails.getNoticeInAdvance()+getString(R.string.hours_notice));
        ac_booking_window.setText(""+activityDetails.getBookingWindow()+getString(R.string.hrs));
        ac_total_capacity.setText(""+activityDetails.getTotalCapacity());
        ac_private_Groups_price.setText(""+activityDetails.getGroupPrice());
        ac_private_group.setText(""+activityDetails.getMinCapacityGroup()+" - "+activityDetails.getMaxCapacityGroup());

        double tmp = (activityDetails.getActivityLength() % 60);
        double tmp2= activityDetails.getActivityLength() / 60;
        double res = tmp2+(tmp/60d);
        int tmp3= (int) (res*100);
        double fres = ((double) tmp3)/100;
        ac_activity_length.setText( ""+fres+" "+getString(R.string.hrs));



        set__booking_available();
        set_availability_list();
        set_organizers();
        set_capacity_list();
        set_price_per_individual_list();

        activityDetails.getIndividualCategories();


    }

    private void set_organizers() {

        for (int i = 0; i < activityDetails.getActivity_Organizer().size(); i++) {

            if (activityDetails.getActivity_Organizer().get(i).getType_id()==1){

                //                case 1 :
                TextView textView = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.setGravity(Gravity.FILL);
                textView.setText(activityDetails.getActivity_Organizer().get(i).getName());

//                textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
//                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.individual_icon);
//                if (drawable != null) {
//                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                }
                layoutParams.setMargins((int) getResources().getDimension(R.dimen._5sdp), 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                textView.setLayoutParams(layoutParams);

//                textView.setCompoundDrawables(drawable, null, null, null);
                textView.setId(View.generateViewId());

                admins_list.addView(textView);
            }
            else{


//                case 0 :
                TextView textView1 = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                GridLayout.LayoutParams layoutParams1 = new GridLayout.LayoutParams();
                layoutParams1.setGravity(Gravity.FILL);
                textView1.setText(activityDetails.getActivity_Organizer().get(i).getName());

//                textView1.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
//                Drawable drawable1 = ContextCompat.getDrawable(getActivity(), R.drawable.private_group_icon);
//                if (drawable1 != null) {
//                    drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
//                }
                layoutParams1.setMargins( (int) getResources().getDimension(R.dimen._5sdp), 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                textView1.setLayoutParams(layoutParams1);

//                textView1.setCompoundDrawables(drawable1, null, null, null);
                textView1.setId(View.generateViewId());

                organizer_list.addView(textView1);
            }

        }


    }

    private void set_price_per_individual_list() {
        price_per_individual_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        price_per_individual_list.setAdapter(new ActivityCapacity_adobter(getActivity(),activityDetails.getIndividualCategories(),2));

    }
    private void set_capacity_list() {
        capacity_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        capacity_list.setAdapter(new ActivityCapacity_adobter(getActivity(),activityDetails.getIndividualCategories(),1));

    }


    private void set__booking_available() {

        if (activityDetails.getBookingAvailableForIndividuals())
            ac_individuals.setVisibility(View.VISIBLE);
        else
            ac_individuals.setVisibility(View.GONE);


        if (activityDetails.getBookingAvailableForGroups())
            ac_private_groups.setVisibility(View.VISIBLE);
        else
            ac_private_groups.setVisibility(View.GONE);


    }

    private void set_availability_list() {


        for (int i = 0; i < activityDetails.getAvaliabilities().size(); i++) {

            if (activityDetails.getAvaliabilities().get(i).getIsprovider()==1){

                    //                case 1 :
                    TextView textView = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.setGravity(Gravity.FILL);
                    textView.setText(activityDetails.getAvaliabilities().get(i).toString());

                    textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.individual_icon);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }
                    layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                    textView.setLayoutParams(layoutParams);

                    textView.setCompoundDrawables(drawable, null, null, null);
                    textView.setId(View.generateViewId());
                textView.setTextSize(11);
                    availability_list.addView(textView);
                }
                else{


//                case 0 :
                    TextView textView1 = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                    GridLayout.LayoutParams layoutParams1 = new GridLayout.LayoutParams();
                    layoutParams1.setGravity(Gravity.FILL);
                    textView1.setText(activityDetails.getAvaliabilities().get(i).toString());

                    textView1.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
                    Drawable drawable1 = ContextCompat.getDrawable(getActivity(), R.drawable.private_group_icon);
                    if (drawable1 != null) {
                        drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
                    }
                    layoutParams1.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                    textView1.setLayoutParams(layoutParams1);

                    textView1.setCompoundDrawables(drawable1, null, null, null);
                    textView1.setId(View.generateViewId());
                textView1.setTextSize(11);
                    availability_list.addView(textView1);
                }

            }


        }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBookingPrefrencesEditBtn = view.findViewById(R.id.booking_prefrences_edit_btn);
        mAvailabilityEditBtn = view.findViewById(R.id.availability_edit_btn);
        mLengthAndCapacityEditBtn = view.findViewById(R.id.length_and_capacity_edit_btn);
        mPricingEditBtn = view.findViewById(R.id.pricing_edit_btn);
        mOrganisersEditBtn = view.findViewById(R.id.organisers_edit_btn);

        mBookingPrefrencesEditBtn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",6);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson()).toJson(activityDetails));
            startActivity(i);
        });
        mAvailabilityEditBtn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",7);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));

            startActivity(i);
        });
        mLengthAndCapacityEditBtn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",8);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });
        mOrganisersEditBtn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",10);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });
        mPricingEditBtn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",9);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });


    }
}
