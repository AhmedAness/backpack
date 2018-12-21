package com.wasltec.provider.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wasltec.provider.LocaleUtils;
import com.wasltec.provider.R;
import com.wasltec.provider.TypefaceUtil;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.model.Session;

import java.util.Locale;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.splash_layout);


//        SharedPreferences pref = getApplicationContext().getSharedPreferences("language", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//
//        if (!pref.getBoolean("lan",true)){
//            final Locale loc = new Locale("ar");
//            Locale.setDefault(loc);
//            final Configuration cfg = new Configuration();
//            cfg.locale = loc;
//            getApplicationContext().getResources().updateConfiguration(cfg, null);
//
//        }else {
//            final Locale loc = new Locale("en");
//            Locale.setDefault(loc);
//            final Configuration cfg = new Configuration();
//            cfg.locale = loc;
//            getApplicationContext().getResources().updateConfiguration(cfg, null);
//
//        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = preferences.getString("lang", "");
        boolean langSelected = preferences.getBoolean("langSelected", false);
        SharedPreferences.Editor editor = preferences.edit();

        new Handler().postDelayed(() -> {

            if (langSelected) {
                editor.clear();
                editor.putString("lang", lang);
                editor.putBoolean("langSelected", true);
                editor.apply();
                LocaleUtils.updateConfig(this,lang);
            } else {
                LocaleUtils.updateConfig(this, Locale.getDefault().getLanguage());
                editor.clear();
                editor.putString("lang", "en");
                editor.putBoolean("langSelected", false);
                editor.apply();
            }

            Intent intent;
            SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
            if (sharedPreferencesManager.getToken() == null||sharedPreferencesManager.getToken() == "") {

                intent = new Intent(Splash_Screen.this, Login_Register.class);
            } else {
                Session.getInstance().start(this);
                intent = new Intent(Splash_Screen.this, Home.class);
            }

            startActivity(intent);
            finish();
        }, 500);
    }
}
