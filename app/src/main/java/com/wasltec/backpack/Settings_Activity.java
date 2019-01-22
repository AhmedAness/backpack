package com.wasltec.backpack;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wasltec.backpack.activities.MainActivity;
import com.wasltec.backpack.activities.SplashActivity;

public class Settings_Activity extends AppCompatActivity {

    Spinner language;
    Spinner Price_type;
    TextView Helpe;
    TextView Terms_;
    TextView LogOut;
    Dialog dialog;
    View view;
    int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Init();
        OnClick();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    private void Init() {


        language=findViewById(R.id.Language_Settings);
        Price_type=findViewById(R.id.Price_type);
        Helpe=findViewById(R.id.Helpe);
        Terms_=findViewById(R.id.Terms_0f_use);
        LogOut=findViewById(R.id.LogOut);
        dialog = new Dialog(Settings_Activity.this); // Context, this, etc.
        view = LayoutInflater.from(Settings_Activity.this).inflate(R.layout.alert_dialog, null, false);
        if (ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_RTL) {
            // The view has RTL layout
            language.setSelection(1);
        }

    }

    private void OnClick() {
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (++check>2){
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Toolbar bar = view.findViewById(R.id.toolbar);
                bar.setTitle(getString(R.string.Warning_title));
                TextView textView = view.findViewById(R.id.msg);
                textView.setText(getString(R.string.Warning_msg));

                switch(position) {
                    case 0:
                        dialog.show();
                        view.findViewById(R.id.ok_btn).setOnClickListener(v -> {
                            dialog.dismiss();
                                setLocale("en");
                        });
                        break;
                    case 1:
                        dialog.show();
                        view.findViewById(R.id.ok_btn).setOnClickListener(v -> {
                            dialog.dismiss();
                            setLocale("ar");
                        });
                        break;
                    default:
                }
            }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Price_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (++check>1){
                    switch(position) {
                        case 0:
                            break;
                        case 1:
                            break;
                        default:
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Helpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Terms_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Settings_Activity.this); // Context, this, etc.
                View view2 = LayoutInflater.from(Settings_Activity.this).inflate(R.layout.alert_dialog, null, false);
                dialog.setContentView(view2);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Toolbar bar = view2.findViewById(R.id.toolbar);
                bar.setTitle(getString(R.string.Warning_title));
                TextView textView = view2.findViewById(R.id.msg);
                textView.setText(getString(R.string.Warning_Logout_msg));
                view2.findViewById(R.id.ok_btn).setOnClickListener(v -> {
                    dialog.dismiss();

                    Session.getInstance(Settings_Activity.this).logout();
                    Intent intent = new Intent(Settings_Activity.this, SplashActivity.class);
                    startActivity(intent);

                });
                dialog.show();


            }
        });
    }

    public void setLocale(String lang) {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(Settings_Activity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("lang", lang);
        editor.putBoolean("langSelected", true);
        editor.apply();
        editor.commit();
        if (editor.commit()){
            Intent mStartActivity = new Intent(getApplicationContext(), SplashActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }

    }

}
