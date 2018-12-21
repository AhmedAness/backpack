package com.wasltec.provider.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.wasltec.provider.Adopters.DiscountAdapter;
import com.wasltec.provider.Adopters.PriceAdapter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Price_per_individual;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

import static com.wasltec.provider.Activities.CustomisedCalender.activity_id;


public class Calenderdaydetails extends AppCompatActivity implements View.OnClickListener{

    public static  Date day;
    static boolean is_Available= false;
    static boolean day_Available= false;
    private RadioGroup mRadiogroup;
    boolean is_for_group= false;
    boolean have_discount= false;
    private boolean repeat=false;


    private TextView mDayDate;
    private RadioButton mNotAvailableRadioButton;
    private RadioButton mAvailableRadioButton;
    private CheckBox mIndividualsCheckbox;
    private CheckBox mPrivateGroups;
    private RadioButton mNotApplyBtn;
    private RadioButton mApplyBtn;
    private TextView mCancel;
    private TextView mStartD;
    private TextView mStartT;
    private TextView mEndD;
    private TextView mEndT;
    private TextView apply_btn;
    RecyclerView price_per_individual_Recycler;
    private EditText mPrice;
    private CheckBox mRepeatBOX;

    LinearLayout availabe_layout ;

    List<Price_per_individual> price_per_individuals = new ArrayList<>();


    private int mYear;
    private int mMonth;
    private int mDay;
    private String start_date;
    private String end_date;
    private int mHour;
    private int mMinute;
    private String start_time;
    private String end_time;


    private PriceAdapter adapter;
    List<String> strings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calenderdaydetails);

        init();
        setdata();

        getdata();



    }

    private void setdata() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMMM");
        mDayDate.setText(df.format(day));



        mStartD.setOnClickListener(this);
        mStartT.setOnClickListener(this);
        mEndD.setOnClickListener(this);
        mEndT.setOnClickListener(this);







        if(is_Available)
        {
            availabe_layout.setVisibility(View.VISIBLE);
            mAvailableRadioButton.setChecked(true);
        }
        else
        {
            mNotAvailableRadioButton.setChecked(true);
            availabe_layout.setVisibility(View.GONE);
        }



        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_server();
            }
        });
    }

    private void call_server() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("activity_id", activity_id);
            jsonObject.put("activity_Start", start_date + " " + start_date);
            jsonObject.put("activity_End", end_date + " " + end_time);
            jsonObject.put("isForGroup", is_for_group);
            jsonObject.put("group_Price", mPrice.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URLManger.getInstance().getAddAvalibilty())
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Calenderdaydetails.this).getToken())
                .addJSONObjectBody(jsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Calenderdaydetails.this,response.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(Calenderdaydetails.this,anError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.not_availableRadioButton:
                if (checked) {
                    is_Available = false;
                    availabe_layout.setVisibility(View.GONE);
                }
                break;
            case R.id.availableRadioButton:
                if (checked)
                {
                    is_Available=true;
                    availabe_layout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    public void onRadioButtondiscountClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.not_apply_btn:
                if (checked)
                    have_discount=false;
                    break;
            case R.id.apply_rbtn:
                if (checked)
                    have_discount=true;
                    break;
        }
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();



        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.individuals_checkbox:


                    is_for_group=false;
                    mIndividualsCheckbox.setChecked(true);
                mPrivateGroups.setChecked(false);

                break;
            case R.id.private_groups:

                    is_for_group=true;
                    mPrivateGroups.setChecked(true);
                mIndividualsCheckbox.setChecked(false);

                break;
            case R.id.repeat_BOX:
                if (checked)
                {
                    repeat=true;
                }
                else
                    repeat=false;

            // TODO: Veggie sandwich
        }
    }
    private void init() {
        mDayDate = findViewById(R.id.day_date);
        day_Available=is_Available;

        mNotAvailableRadioButton = findViewById(R.id.not_availableRadioButton);
        mAvailableRadioButton = findViewById(R.id.availableRadioButton);
        mIndividualsCheckbox = findViewById(R.id.individuals_checkbox);
        mPrivateGroups = findViewById(R.id.private_groups);
        mNotApplyBtn = findViewById(R.id.not_apply_btn);
        mApplyBtn = findViewById(R.id.apply_rbtn);
        mRadiogroup = findViewById(R.id.radiogroup);
        mCancel = findViewById(R.id.cancel);

        mStartD = findViewById(R.id.startD);
        mStartT = findViewById(R.id.startT);
        mEndD = findViewById(R.id.endD);
        mEndT = findViewById(R.id.endT);
        apply_btn = findViewById(R.id.apply_btn);
        mPrice = findViewById(R.id.price);
        mRepeatBOX = findViewById(R.id.repeat_BOX);
        availabe_layout = findViewById(R.id.availabe_layout);

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject =new JSONObject();
                JSONObject jsonObject1=new JSONObject();


                try {
                    jsonObject1.put("id",""+2085);
                    jsonObject1.put("activity_Start",""+start_date+" "+start_time);
                    jsonObject1.put("activity_End",""+end_date+" "+end_time);
                    jsonObject1.put("isForGroup",is_for_group?1:0);
                    jsonObject1.put("group_Price",""+mPrice.getText().toString());

                    jsonObject.put("avalibilityModel",jsonObject1);
                    JSONArray JSONArray1=new JSONArray();


                    for (int i = 0; i < ((PriceAdapter) price_per_individual_Recycler.getAdapter()).getPrices().size(); i++) {
                        Price_per_individual tmp = ((PriceAdapter) price_per_individual_Recycler.getAdapter()).getPrices().get(i);
                        JSONObject jsonObject2=new JSONObject();
                        jsonObject2.put("individualCategoryId",tmp.getId());
                        jsonObject2.put("price",tmp.getPrice());
                            jsonObject2.put("priceAfterDiscount",tmp.getPrice_after_discount());



                    }
                    
                    
                    jsonObject.put("avalibilityPricingModels",JSONArray1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                AndroidNetworking.post(URLManger.getInstance().getCalenarAvailablility(activity_id))
                        .addJSONObjectBody(jsonObject)
                        .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Calenderdaydetails.this).getToken())
                        .build().getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {

                        Calenderdaydetails.this.finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Calenderdaydetails.this.finish();
                    }
                });

            }
        });

        LinearLayout Add_addons = findViewById(R.id.add_price_ber_individual);

        Add_addons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strings.size()>1)
                openDialog();
                else
                    Toast.makeText(Calenderdaydetails.this,"nodata",Toast.LENGTH_SHORT).show();
            }
        });

        price_per_individual_Recycler = findViewById(R.id.price_per_individual_Recycler);
        price_per_individual_Recycler.setLayoutManager(new LinearLayoutManager(Calenderdaydetails.this, LinearLayoutManager.VERTICAL, false));

    }

    private void datePicker(View v) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Calenderdaydetails.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        if (v.getId() == R.id.startD) {
                            start_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            mStartD.setText(start_date);

                        } else if (v.getId() == R.id.endD) {
                            end_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            mEndD.setText(end_date);

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(Calenderdaydetails.this,
                (view, hourOfDay, minute) -> {

                    mHour = hourOfDay;
                    mMinute = minute;
                    if (v.getId() == R.id.startD) {
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
                        mStartT.setText(goal);

                    } else if (v.getId() == R.id.endD) {
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

                        mEndT.setText(goal);
                    }


                }, mHour, mMinute, false);
        timePickerDialog.show();
    }



    public void getdata() {

        AndroidNetworking.get(URLManger.getInstance().getIndividualCategory(""+activity_id))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Calenderdaydetails.this).getToken())
                .build().getAsObjectList(Price_per_individual.class, new ParsedRequestListener<List<Price_per_individual>>() {
            @Override
            public void onResponse(List<Price_per_individual> response) {
                strings= new ArrayList<>();
                for (Price_per_individual price_per_individual : response) {
                    strings.add(price_per_individual.getName());
                }

            }

            @Override
            public void onError(ANError anError) {


            }
        });



    }


    public void openDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_add_price_per_individual);

        final Spinner category_name = dialog.findViewById(R.id.category_name);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, strings);
        category_name.setAdapter(arrayAdapter);

        final EditText ticket_price = dialog.findViewById(R.id.ticket_price);

        TextView Add_addons_btn = dialog.findViewById(R.id.Add_addons_btn);
        Add_addons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticket_price.getText().toString().length() < 1)
                    ticket_price.setError(getResources().getString(R.string.this_field_is_required));

                else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", category_name.getSelectedItem().toString());
                        jsonObject.put("price", ticket_price.getText().toString());
                        jsonObject.put("capacity", "0");
                        jsonObject.put("price_after_discount", ticket_price.getText().toString());
                        jsonObject.put("activityid", activity_id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AndroidNetworking.post(URLManger.getInstance().getCreateIndividualCategory())
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Calenderdaydetails.this).getToken())
                            .addJSONObjectBody(jsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int id = response.getInt("avaliability_id");

                                price_per_individuals.add(new Price_per_individual(category_name.getSelectedItem().toString(), Integer.parseInt(ticket_price.getText().toString()),
                                        id, 0, 0));
                                adapter = new PriceAdapter(price_per_individuals);

                                price_per_individual_Recycler.setAdapter(adapter);
                                strings.remove(category_name.getSelectedItemPosition());
                                arrayAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Calenderdaydetails.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });

//                price_per_individuals.add(new Price_per_individual(category_name.getText().toString(),
//                        Double.parseDouble(ticket_price.getText().toString())));
//                    Price_per_individual_Adopter price_per_individual_adopter = new Price_per_individual_Adopter(getActivity(), price_per_individuals);
//                    price_per_individual_Recycler.setAdapter(price_per_individual_adopter);

                }
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    public void onClick(View v) {


            if (v.getId() == R.id.startD || v.getId() == R.id.endD)
                datePicker(v);
            else
                tiemPicker(v);

    }
}
