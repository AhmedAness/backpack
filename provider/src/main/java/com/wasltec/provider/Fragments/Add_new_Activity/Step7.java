package com.wasltec.provider.Fragments.Add_new_Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.Availability_Adopter;
import com.wasltec.provider.model.Availability;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.AvalibilityPricingModels;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;

public class Step7 extends Fragment implements View.OnClickListener {

    private View view;
    RecyclerView recyclerView;
    List<Availability> availability_data = new ArrayList<>();
    TextView startD, endD, startT, endT;
//    EditText price;

    Availability_Adopter booking_data;
    String start_date = "";
    String end_date = "";
    String start_time = "";
    String end_time = "";

    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    CheckBox mPrivateBox , mIndividualBox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.availability));
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step7, container, false);
        LinearLayout Add_addons = view.findViewById(R.id.Add_availability);


        Add_new_activity.dialog.hide();
        booking_data = new Availability_Adopter();
        recyclerView = view.findViewById(R.id.availability_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AndroidNetworking.get(URLManger.getInstance().getGetAvailablility(""+ActivityID))//"http://backpackapis.wasltec.org/Api/Activity/GetAvailablility?activityid=" + ActivityID
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build().getAsObjectList(Availability.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {

                availability_data = (List<Availability>) response;
                booking_data = new Availability_Adopter(getActivity(), availability_data);
                recyclerView.setAdapter(booking_data);

            }

            @Override
            public void onError(ANError anError) {

            }
        });


        Add_addons.setOnClickListener(v -> openDialog());


        return view;
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_availability, null, false);
        dialog.setContentView(view);
//        dialog.setTitle(R.string.add_add_ons);
        startD = view.findViewById(R.id.start_date);

        endD = view.findViewById(R.id.end_date);
        startT = view.findViewById(R.id.start_time);
        endT = view.findViewById(R.id.end_time);
//        price = view.findViewById(R.id.price);
        startD.setOnClickListener(this);
        endD.setOnClickListener(this);
        startT.setOnClickListener(this);
        endT.setOnClickListener(this);
        mIndividualBox = view.findViewById(R.id.individual_box7);

        mPrivateBox = view.findViewById(R.id.private_box7);

        mIndividualBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                mPrivateBox.setChecked(false);

            }
        });
        mPrivateBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                mIndividualBox.setChecked(false);

            }
        });
        TextView Add_addons_btn = dialog.findViewById(R.id.Add_addons_btn);
        Add_addons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startT.getText().toString().length() < 1)
                    startT.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else if (endT.getText().toString().length() < 1)
                    endT.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else if (startD.getText().toString().length() < 1)
                    startD.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else if (endD.getText().toString().length() < 1)
                    endD.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else if (!mPrivateBox.isChecked()&&!mIndividualBox.isChecked())
                    Toast.makeText(getContext(), "You must select individual or group", Toast.LENGTH_SHORT).show();
//                 else if (price.getText().toString().length()<1)
//                    Toast.makeText(getContext(), "You must enter the price", Toast.LENGTH_SHORT).show();
                else{
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        if (simpleDateFormat.parse(startD.getText().toString() + " " + startT.getText().toString())
                                .after(simpleDateFormat.parse(endD.getText().toString() + " " + endT.getText().toString()))){
                            Toast.makeText(getActivity(),"Start Date must be before End Date",Toast.LENGTH_SHORT).show();
                            return;
                        }
                       else if (simpleDateFormat.parse(startD.getText().toString() + " " + startT.getText().toString())
                                .before(Calendar.getInstance().getTime())){
                            Toast.makeText(getActivity(),"Start Date must be After Today",Toast.LENGTH_SHORT).show();
                            return;
                        }
                         if (simpleDateFormat.parse(endD.getText().toString() + " " + endT.getText().toString())
                                .before(Calendar.getInstance().getTime())){
                            Toast.makeText(getActivity(),"End Date must be After Today",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("activity_id", ActivityID);
//                        jsonObject.put("activity_Start", startD.getText().toString() + " " + startT.getText().toString());
//                        jsonObject.put("activity_End", endD.getText().toString() + " " + endT.getText().toString());
//                        jsonObject.put("isForGroup", mPrivateBox.isChecked() ? 1 : 0);
//                        jsonObject.put("group_Price", price.getText().toString());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    AndroidNetworking.post(URLManger.getInstance().getAddAvalibilty())
//                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
//                            .addJSONObjectBody(jsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            Toast.makeText(getActivity(),anError.getErrorDetail(),Toast.LENGTH_LONG).show();
//                        }
//                    });







                    if (mIndividualBox.isChecked()) {
                        availability_data.add(
                                new Availability(
                                        startD.getText().toString() + " " + startT.getText().toString(),
                                        endD.getText().toString() + " " + endT.getText().toString(),  0,
                                        0)
                        );
                    }
                    if (mPrivateBox.isChecked()){
                        availability_data.add(
                                new Availability(
                                        startD.getText().toString() + " " + startT.getText().toString(),
                                        endD.getText().toString() + " " + endT.getText().toString(), 1,
                                        0)

//                                availability_data.add(
//                                new Availability(
//                                        startD.getText().toString() + " " + startT.getText().toString(),
//                                        endD.getText().toString() + " " + endT.getText().toString(), 1,
//                                        Integer.parseInt( price.getText().toString()))
                        );
                    }

                    booking_data =new Availability_Adopter(getActivity(), availability_data);
                    recyclerView.setAdapter(booking_data);
                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void valid() {


        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonElements = new JSONArray();


            for (Availability availability : booking_data.getAvailability_Items()) {

                if(availability.getId()==0){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("activity_Start", availability.getActivity_Start());
                jsonObject1.put("activity_End", availability.getActivity_End());
                jsonObject1.put("isForGroup", availability.getIsForGroup());
                jsonObject1.put("group_Price", availability.getGroup_Price());
//                jsonObject1.put("total_tickets", "0");
//
//                JSONArray jsonArray2=new JSONArray();
//                List<AvalibilityPricingModels> tmp = availability.getAvalibilityPricingModels();
//                if(tmp!=null)
//                for (int i = 0; i < tmp.size(); i++) {
//                    JSONObject jsonObject2 = new JSONObject();
//                    jsonObject2.put("individualCategoryId",availability.getAvalibilityPricingModels().get(i).getIndividualCategoryId());
//                    jsonObject2.put("price",availability.getAvalibilityPricingModels().get(i).getPrice());
//                    jsonObject2.put("priceAfterDiscount",availability.getAvalibilityPricingModels().get(i).getPriceAfterDiscount());
//                    jsonArray2.put(jsonObject2);
//                }
//
//                jsonObject1.put("avalibilityPricingModels", jsonArray2);
                jsonElements.put(jsonObject1);
            }
            }

            jsonObject.put("avalibilityModels", jsonElements);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URLManger.getInstance().setStepSeven(ActivityID, mode))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mode == 2) {
                            Add_new_activity.step_number = 1;
                            mode = 1;
                            getActivity().finish();
                        }
                        else {
                            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step3));
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step8).commit();
                            Add_new_activity.step_number = 8;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Add_new_activity.dialog.hide();

                        Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });

                        if (mode == 2) {
                            Add_new_activity.step_number = 1;
                            mode = 1;
                            getActivity().finish();
                        }
                        else {
                            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step3));
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step8).commit();
                            Add_new_activity.step_number = 8;
                        }
    }


    private void datePicker(View v) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        if (v.getId() == R.id.start_date) {
                            start_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            startD.setText(start_date);

                        } else if (v.getId() == R.id.end_date) {
                            end_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            endD.setText(end_date);

                        }
                        tiemPicker(v);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(View v) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute) -> {

                    mHour = hourOfDay;
                    mMinute = minute;
                    if (v.getId() == R.id.start_date) {
                        start_time = mHour + ":" + mMinute;
                        SimpleDateFormat inFormat = new SimpleDateFormat("H:m");
                        Date date = null;
                        try {
                            date = inFormat.parse(start_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                        String goal = outFormat.format(date);
                        startT.setText(goal);

                    } else if (v.getId() == R.id.end_date) {
                        end_time = mHour + ":" + mMinute;
                        SimpleDateFormat inFormat = new SimpleDateFormat("H:m");
                        Date date = null;
                        try {
                            date = inFormat.parse(end_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                        String goal = outFormat.format(date);

                        endT.setText(goal);
                    }


                }, mHour, mMinute, false);
        timePickerDialog.show();
    }




    @Override
    public void onClick(View v) {

            if (v.getId() == R.id.start_date || v.getId() == R.id.end_date)
                datePicker(v);
            else
                tiemPicker(v);

    }
    @Override
    public void onResume() {
        super.onResume();
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.availability));

    }
}