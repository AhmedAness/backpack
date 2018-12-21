package com.wasltec.backpack.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wasltec.backpack.LocaleUtils;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.utils.SharedPreferencesManager;

import java.util.Locale;

import static com.wasltec.backpack.App.lang;
import static com.wasltec.backpack.App.langSelected;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String lang = preferences.getString("lang", "");
//        boolean langSelected = preferences.getBoolean("langSelected", false);
        SharedPreferences.Editor editor = preferences.edit();

        new Handler().postDelayed(() -> {
            if (langSelected&&lang.equals("ar")) {
                editor.clear();
                editor.putString("lang", "ar");
                editor.putBoolean("langSelected", true);
                editor.apply();
                LocaleUtils.updateConfig(this,"ar");
            } else {
                LocaleUtils.updateConfig(this, Locale.getDefault().getLanguage());
                editor.clear();
                editor.putString("lang", "en");
                editor.putBoolean("langSelected", false);
                editor.apply();
            }

            Intent intent;


            if (Session.getInstance(this).getToken() == null||Session.getInstance(this).getToken().equals("")) {

                intent = new Intent(SplashActivity.this, LoginActivity.class);
            } else {
                Session.getInstance(SplashActivity.this).start();
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 500);
    }
}
