package com.wasltec.provider.Activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.wasltec.provider.Fragments.Calender.Months;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.Post_item;

import java.util.ArrayList;
import java.util.List;

public class Calender extends AppCompatActivity {

    private Resources res;
    LinearLayout month;
    LinearLayout selector_month;
    Months month_opj;
    boolean flag_fragment= true;
    public static Toolbar toolbar_Calender ;
    public  static List<Post_item> booking_list =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        int activity_id =getIntent().getExtras().getInt("Activity_id");
//        Activity_model activity_opj = App_Context.getInstance().getActivities().get(activity_id);
//        callserver(activity_id);





        toolbar_Calender = findViewById(R.id.toolbar);




        toolbar_Calender.setTitle(getResources().getString(R.string.calender));



        setSupportActionBar(toolbar_Calender);

        init();
        clicks();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        res = getResources();
        month_opj = new Months();
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.container1, month_opj).commit();
        flag_fragment=true;


    }



    private void clicks() {
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag_fragment)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container1, month_opj).commit();
                    flag_fragment=true;
                    update_select();
                }

            }
        });

    }

    private void update_select() {
        if (flag_fragment)
        {
            selector_month.setVisibility(View.VISIBLE);
        }else
        {
            selector_month.setVisibility(View.GONE);
        }
    }

    private void init() {
        month= findViewById(R.id.month);
        selector_month= findViewById(R.id.selector_month);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);


        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId())
        {
            case R.id.settings:
                break;
            case R.id.add_item:
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()>1)
            super.onBackPressed();
        else
            Calender.this.finish();
    }

}
