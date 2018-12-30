package com.wasltec.provider.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.Fragments.Activity_details.Info;
import com.wasltec.provider.Fragments.Activity_details.Settings;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityDetailsReturnObj;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.Activities.Calender;
import com.wasltec.provider.model.AllActivityReturnOpj;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


import okhttp3.Response;

import static com.wasltec.provider.Activities.Add_new_activity.context;


public class Activity_details extends AppCompatActivity {


    private Resources res;
    LinearLayout info, settings;
    LinearLayout selector_info, selector_settings;
    Info info_opj;
    Settings settings_opj;
//    boolean flag_fragment = true;
    public static Toolbar toolbar_details;

    private Gson gson;
    AllActivityReturnOpj myactivity;
    private ActionBar actoinbar;
    public static ActivityDetailsReturnObj activityDetails;
    int activity_id;
    private String TAG = "ActivityDetails";
    public static int sett=-1;
    RotateLoading loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        activity_id = getIntent().getExtras().getInt("Activity_id");
        String activity_obj = getIntent().getExtras().getString("Activity_object");
        gson = new Gson();
        Type listType = new TypeToken<AllActivityReturnOpj>() {
        }.getType();
        try {
            myactivity = gson.fromJson(activity_obj, listType);
        } catch (Exception e) {
            myactivity = new AllActivityReturnOpj();
            e.printStackTrace();
        }

        toolbar_details = findViewById(R.id.toolbar);


        toolbar_details.setTitle(myactivity.getTitle());


        setSupportActionBar(toolbar_details);



    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        clicks();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        res = getResources();
        info_opj = new Info();
//        flag_fragment = true;
        update_select();
        Bundle bundle = new Bundle();
        bundle.putInt("id", activity_id);
        info_opj.setArguments(bundle);
        settings_opj = new Settings();
        settings_opj.setArguments(bundle);
        callserver();
        if (sett==0){
//            getSupportFragmentManager().beginTransaction().replace(R.id.container1, settings_opj).commit();
//            flag_fragment = false;
            update_select();
        }

    }

    private void callserver() {
        info.setClickable(false);
        settings.setClickable(false);

        loader = (RotateLoading) findViewById(R.id.rotateloading);
        loader.start();

        AndroidNetworking.get(URLManger.getInstance().getGetActivityDetails(""+myactivity.getId()))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Activity_details.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        gson = new Gson();
                        Type listType = new TypeToken<ActivityDetailsReturnObj>() {
                        }.getType();
                        try {
                            activityDetails = gson.fromJson(response.get(0).toString(), listType);
                            settings.setClickable(true);
                            info.setClickable(true);
                        } catch (JSONException e) {
                            activityDetails = new ActivityDetailsReturnObj();
                            e.printStackTrace();
                        } catch (Exception e) {
                            activityDetails = new ActivityDetailsReturnObj();
                            e.printStackTrace();
                        }
                        try {
                            if (sett==0){
                                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, settings_opj).commit();

                            }else {
                                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, info_opj).commit();
                            }
                            }
                        catch (Exception c){
                            Log.d(TAG, "onResponse: "+c.getMessage());

                        }
                        if (loader.isStart())
                            loader.stop();
//                        flag_fragment = true;
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError.getMessage());
                        if (loader.isStart())
                            loader.stop();
                    }
                });


    }


    private void clicks() {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!flag_fragment) {
                    sett=1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.container1, info_opj).commit();
//                    flag_fragment = true;
                    update_select();
//                }

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (flag_fragment) {
                    sett=0;
                    getSupportFragmentManager().beginTransaction().replace(R.id.container1, settings_opj).commit();
//                    flag_fragment = false;
                    update_select();
//                }
            }
        });
    }

    private void update_select() {
        if (sett==0) {
            selector_info.setVisibility(View.GONE);
            selector_settings.setVisibility(View.VISIBLE);

        } else {
            selector_info.setVisibility(View.VISIBLE);
            selector_settings.setVisibility(View.GONE);
        }
    }

    private void init() {
        info = findViewById(R.id.info);
        settings = findViewById(R.id.settings);
        selector_info = findViewById(R.id.selector_info);
        selector_settings = findViewById(R.id.selector_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);
        menu.findItem(R.id.individual_item).setVisible(false);
        menu.findItem(R.id.calender).setVisible(true);
        menu.findItem(R.id.delete_activity).setVisible(true);
        menu.findItem(R.id.delete_activity).setCheckable(true);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.delete_activity:
                Delete_Activity();
                break;

            case R.id.settings:
                break;
            case R.id.add_item:
                break;
            case R.id.calender:
                Intent intent = new Intent(Activity_details.this, CustomisedCalender.class);
                intent.putExtra("Activity_id", ""+myactivity.getId());
                intent.putExtra("title", ""+myactivity.getTitle());
                startActivity(intent);

                break;
            case R.id.individual_item:
                //                Intent intent = new Intent(Activity_details.this, Calender.class);
                //                intent.putExtra("Activity_id",myactivity.getId());
                //                startActivity(intent);

                break;

            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void Delete_Activity() {
//
//
//        final Dialog dialog = new Dialog(getApplicationContext()); // Context, this, etc.
//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.msg_dialog, null, false);
//        dialog.setContentView(view);
//        TextView okdialod=dialog.findViewById(R.id.ok_btn);
//        Toolbar toolbar=dialog.findViewById(R.id.toolbar);
//        okdialod.setText("Cancell");
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//        okdialod.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });








        final Dialog dialog = new Dialog(Activity_details.this); // Context, this, etc.
        View view = LayoutInflater.from(Activity_details.this).inflate(R.layout.msg_dialog, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText textView = view.findViewById(R.id.msg);
        TextView ok_btn = view.findViewById(R.id.ok_btn);
        TextView msg = view.findViewById(R.id.msg);
        msg.setText("Are you sure to delete it !");
        ok_btn.setText("Delete");
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Warning");


        ok_btn.setOnClickListener(v2 -> {

            AndroidNetworking.get(URLManger.getInstance().getDeleteActivity_URL()+myactivity.getId())
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Activity_details.this).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals(1)) {
                                Toast.makeText(Activity_details.this, "Deleted", Toast.LENGTH_SHORT).show();
                                Activity_details.this.finish();
                                startActivity(new Intent(Activity_details.this, Home.class));
                            }else {
                                Toast.makeText(Activity_details.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Activity_details.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


            dialog.dismiss();
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
        else
            Activity_details.this.finish();
            startActivity(new Intent(Activity_details.this,Home.class));
    }
}
