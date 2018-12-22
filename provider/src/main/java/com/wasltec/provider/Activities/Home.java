package com.wasltec.provider.Activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wasltec.provider.App;
import com.wasltec.provider.Fragments.Home.Account_Fragment;
import com.wasltec.provider.Fragments.Home.Activities_Fragment;
import com.wasltec.provider.Fragments.Home.Messages_Fragment;
import com.wasltec.provider.Fragments.Home.Overview_Fragment;
import com.wasltec.provider.Fragments.Home.Statistics_Fragment;
import com.wasltec.provider.LocaleUtils;
import com.wasltec.provider.R;
import com.wasltec.provider.TypefaceUtil;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.model.Session;

import static com.wasltec.provider.Activities.Add_new_activity.context;

public class Home extends AppCompatActivity implements View.OnClickListener {

    ImageView statistics,overview,activities,messages,account;
    TextView statistics_tv,overview_tv,activities_tv,messages_tv,account_tv;
    LinearLayout statistics_lout,overview_lout,activities_lout,messages_lout,account_lout;
    static public Toolbar  toolbar;
    static public ActionBar actoinbar;
    static public boolean is_account_fragment = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("home");

        actoinbar= getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Activities_Fragment activities_fragment = new Activities_Fragment();
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.container1, activities_fragment).commit();


        init();



    }

    private void init() {

        statistics =findViewById(R.id.statistics);
        overview   =findViewById(R.id.overview);
        activities =findViewById(R.id.activities);
        messages   =findViewById(R.id.messages);
        account    =findViewById(R.id.account);


        statistics_tv =findViewById(R.id.statistics_tv);
        overview_tv   =findViewById(R.id.overview_tv);
        activities_tv =findViewById(R.id.activities_tv);
        messages_tv   =findViewById(R.id.messages_tv);
        account_tv    =findViewById(R.id.account_tv);


        statistics_lout =findViewById(R.id.statistics_lout);
        overview_lout   =findViewById(R.id.overview_lout);
        activities_lout =findViewById(R.id.activities_lout);
        messages_lout   =findViewById(R.id.messages_lout);
        account_lout    =findViewById(R.id.account_lout);

        statistics_lout .setOnClickListener(this);
        overview_lout .setOnClickListener(this);
        activities_lout .setOnClickListener(this);
        messages_lout .setOnClickListener(this);
        account_lout .setOnClickListener(this);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(true);
        menu.findItem(R.id.lang).setVisible(true);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId())
        {
            case R.id.settings:
                break;
            case R.id.add_item:
                startActivity(new Intent(Home.this,Add_new_activity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.lang:
                final Dialog dialog = new Dialog(Home.this); // Context, this, etc.
                View view = LayoutInflater.from(Home.this).inflate(R.layout.alert_dialog, null, false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Toolbar bar = view.findViewById(R.id.toolbar);
                bar.setTitle(getString(R.string.Warning_title));
                TextView textView = view.findViewById(R.id.msg);
                textView.setText(getString(R.string.Warning_msg));
                view.findViewById(R.id.ok_btn).setOnClickListener(v -> {
                    dialog.dismiss();
                    if (item.getTitle().equals("EN")) {
                        setLocale("en");
                    }else {
                        setLocale("ar");
                    }
                });
                dialog.show();
        }
        return true;
    }

    @Override
    public void onClick(View v) {


        switch(v.getId())
        {
            case R.id.statistics_lout:
                Statistics_Fragment statisticsFragment = new Statistics_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, statisticsFragment).commit();
                set_selected_fragment (5);
                break;
            case R.id.overview_lout:
                Overview_Fragment overview_fragment = new Overview_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, overview_fragment).commit();
                set_selected_fragment (4);

                break;
            case R.id.activities_lout:
                Activities_Fragment activities_fragment = new Activities_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, activities_fragment).commit();
                set_selected_fragment (3);

                break;
            case R.id.messages_lout:
                Messages_Fragment messages_fragment = new Messages_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, messages_fragment).commit();
                set_selected_fragment (2);

                break;
            case R.id.account_lout:

                Account_Fragment account_fragment = new Account_Fragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, account_fragment).commit();
                set_selected_fragment (1);

                break;

        }

    }


    @Override
    public void onBackPressed() {
        if (is_account_fragment){
            is_account_fragment= false;
            getSupportFragmentManager().popBackStack();


            Account_Fragment account_fragment = new Account_Fragment();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, account_fragment).commit();
            set_selected_fragment (1);
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount()>1) {
            Activities_Fragment activities_fragment = new Activities_Fragment();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, activities_fragment).commit();
            set_selected_fragment(3);
            for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
                getSupportFragmentManager().popBackStack();
            }
        }
        else
            Home.this.finish();
    }


    void set_selected_fragment (double index){

        select_account(1/index==1);
        select_messages(2/index==1);
        select_activities(3/index==1);
        select_overview(4/index==1);
        select_statistics(5/index==1);

    }


    private void select_activities(boolean select) {
        if (select){
            activities .setImageDrawable(getResources().getDrawable(R.drawable.activities_footer_icon));
            activities_tv.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            activities .setImageDrawable(getResources().getDrawable(R.drawable.activities_footer_icon_unchecked));
            activities_tv.setTextColor(getResources().getColor(R.color.footer_color_text));
        }
    }

    private void select_statistics(boolean select) {
        if (select){
            statistics .setImageDrawable(getResources().getDrawable(R.drawable.statistics_footer_icon));
            statistics_tv.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            statistics .setImageDrawable(getResources().getDrawable(R.drawable.statistics_footer_icon_unchecked));
            statistics_tv.setTextColor(getResources().getColor(R.color.footer_color_text));
        }
    }

    private void select_overview(boolean select) {
        if (select){
            overview .setImageDrawable(getResources().getDrawable(R.drawable.overview_footer_icon));
            overview_tv.setTextColor(getResources().getColor(R.color.colorAccent));

        }
        else {
            overview .setImageDrawable(getResources().getDrawable(R.drawable.overview_footer_icon_unchecked));
            overview_tv.setTextColor(getResources().getColor(R.color.footer_color_text));
        }
    }

    private void select_messages(boolean select) {
        if (select){
            messages .setImageDrawable(getResources().getDrawable(R.drawable.messages_footer_icon));
            messages_tv.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            messages .setImageDrawable(getResources().getDrawable(R.drawable.messages_footer_icon_checked));
            messages_tv.setTextColor(getResources().getColor(R.color.footer_color_text));

        }
    }

    private void select_account(boolean select) {
        if (select){
            account .setImageDrawable(getResources().getDrawable(R.drawable.account_footer_icon_unchecked_copy));
            account_tv.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            account .setImageDrawable(getResources().getDrawable(R.drawable.account_footer_icon_unchecked));
            account_tv.setTextColor(getResources().getColor(R.color.footer_color_text));
        }
    }

    public void setLocale(String lang) {
//        final Locale loc = new Locale(lang);
//        Locale.setDefault(loc);
//        final Configuration cfg = new Configuration();
//        cfg.locale = loc;
//        getApplicationContext().getResources().updateConfiguration(cfg, null);



        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(Home.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("lang", lang);
        editor.putBoolean("langSelected", true);
        editor.apply();
        editor.commit();
//        LocaleUtils.updateConfig(Home.this,lang);
        if (editor.commit()){
            Intent mStartActivity = new Intent(getApplicationContext(), Splash_Screen.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }
//
//        for (int i = 1; i <0; i++) {
//            if (editor.commit()){
//                Intent mStartActivity = new Intent(getApplicationContext(), Splash_Screen.class);
//                int mPendingIntentId = 123456;
//                PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
//                AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
//                System.exit(0);
//            }
//        }

//        Intent intent = Home.this.getIntent();
//        Home.this.overridePendingTransition(0, 0);
//        Home.this.finish();
//10027954353-38 gary 00178
//11308271578-43 tawfeer 00178

//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Home.this.overridePendingTransition(0, 0);
//        Home.this.startActivity(intent);



//        new Handler().postDelayed(() -> {
//
//        }, 400);



    }
}
