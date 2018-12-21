package com.wasltec.backpack;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;

import java.util.concurrent.TimeUnit;

import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.android.AndroidAssetsFileParser;
import okhttp3.OkHttpClient;

import static io.appflate.restmock.utils.RequestMatchers.pathContains;

public class App extends Application {
    public static  boolean langSelected;
    public static String lang;
    SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
        RESTMockServerStarter.startSync(new AndroidAssetsFileParser(this));
        RESTMockServer.whenGET(pathContains("category"))
                .delay(TimeUnit.SECONDS,1)
                .thenReturnFile(200, "catogires.json");

        RESTMockServer.whenGET(pathContains("activities"))
                .delay(TimeUnit.SECONDS,1)
                .thenReturnFile(200, "activites.json");

        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
        Stetho.initializeWithDefaults(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        lang = preferences.getString("lang", "");
        langSelected = preferences.getBoolean("langSelected", false);
        SharedPreferences.Editor editor = preferences.edit();

        if (langSelected&&lang.equals("ar")){
            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/GE-SS-Two-Bold.otf"); // font from assets: "assets/fonts/Lucida-Grande_28952.ttf
        }else {
            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/lucida_grand.ttf"); // font from assets: "assets/fonts/Lucida-Grande_28952.ttf
//            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/GE-SS-Two-Bold.otf"); // font from assets: "assets/fonts/Lucida-Grande_28952.ttf
        }

    }
}
