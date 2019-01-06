package com.wasltec.backpack.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.AccountActivity;
import com.wasltec.backpack.Adapters.MainAdapter2;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.FavouriteActivity;
import com.wasltec.backpack.InboxActivity;
import com.wasltec.backpack.LocaleUtils;
import com.wasltec.backpack.PaymentRefActivity;
import com.wasltec.backpack.R;
import com.wasltec.backpack.RewardActivity;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.fragment.UserDataFragment;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.ItemsCategory;
import com.wasltec.backpack.models.SectionItem;
import com.wasltec.backpack.utils.CheckConnection;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserDataFragment.OnFragmentInteractionListener {

    private RecyclerView mItemsList;
    private ProgressBar mProgress;
    private Gson gson;
    private SearchView searchView;

    public static List<ItemsCategory> types;

    Snackbar snackbar = null;
    public static List<ActivityTrip> items;
    List<SectionItem<ActivityTrip>> sections;
    boolean search = false;
    MainAdapter2 mainAdapter2;
    private TextView mErrorTxt;
    DrawerLayout drawer;
    Button btn_activity_pro;

    NavigationView navigationView;
    Session session;
    int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        mainAdapter2 = new MainAdapter2();
        session = Session.getInstance(this);
        mErrorTxt = findViewById(R.id.error_txt);
        btn_activity_pro = findViewById(R.id.btn_activity_pro);
        type=getIntent().getIntExtra("type",0);
        toolbar.setNavigationOnClickListener(v -> {
            if (search) {
                mainAdapter2.setItems(sections);
                search = false;

                showError(R.string.no_internet, false);

            } else
                onBackPressed();
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mItemsList = findViewById(R.id.items_list);
        mItemsList.setLayoutManager(new LinearLayoutManager(this));
        mProgress = findViewById(R.id.progress);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd MM yyyy");

        btn_activity_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("sgin_up",URLManager.getInstance().get_became_activity_pro);
                startActivity(intent);
            }
        });
        gson = gsonBuilder.create();
        if (CheckConnection.checkConnection(this)) {
            makeRequest();
        } else {
            snackbar = Snackbar.make(findViewById(R.id.con), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Retry", v -> {
                if (CheckConnection.checkConnection(MainActivity.this)) {
                    makeRequest();
                    snackbar.dismiss();
                    showError(R.string.no_internet, false);

                } else {
                    snackbar.show();
                }
            });
            snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                @Override
                public void onShown(Snackbar transientBottomBar) {
                    super.onShown(transientBottomBar);
                    transientBottomBar.getView().findViewById(R.id.snackbar_action).setOnClickListener(v -> {
                        if (CheckConnection.checkConnection(MainActivity.this)) {
                            makeRequest();
                            snackbar.dismiss();
                            showError(R.string.no_internet, false);

                        }
                    });
                }
            });
            snackbar.show();

            showError(R.string.no_internet, true);
        }
        navigationView.getHeaderView(0).setOnClickListener(v -> {
            if (session.getToken() != null) {
                Intent i = new Intent(this, AccountActivity.class);
                startActivity(i);
            }
        });
        if (Session.getInstance(this).isFirstTime()&&type==1) {
            Session.getInstance(this).setisFirstTime(false);
            UserDataFragment userDataFragment = new UserDataFragment();
            userDataFragment.show(getSupportFragmentManager(), "f");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView name2 = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        ImageView img = navigationView.getHeaderView(0).findViewById(R.id.user_img);
        try {

            if (session.getUser().getFirstName()!=null&&session.getUser().getFirstName().length() > 0)
                name2.setText(session.getUser().getName());
            else
                name2.setText("Login/Register");
            Glide.with(this).load(session.getUser().getUserPhotoUrl()).apply(new RequestOptions().error(R.drawable.backpack_icon_gray_watermark)).into(img);
        }catch (Exception e){
            name2.setText("Login/Register");
            Glide.with(this).load(getResources().getDrawable(R.drawable.user)).apply(new RequestOptions().error(R.drawable.user)).into(img);
        }
    }


    private void makeRequest() {
        showProgress(true);

        AndroidNetworking.get(URLManager.getInstance().getActivitiesType())
                .addHeaders("Authorization", "bearer "+ Session.getInstance(MainActivity.this).getToken())
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<ItemsCategory>>() {
                }.getType();

                types = gson.fromJson(response.toString(), listType);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("MapLat", "0");
                    jsonObject.put("MapLng", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                AndroidNetworking.post(URLManager.getInstance().getGetHomeActivities())
                        .addHeaders("Authorization", "bearer "+ Session.getInstance(MainActivity.this).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build().getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<SectionItem<ActivityTrip>>>() {
                        }.getType();

//                        items = gson.fromJson(response.toString(), listType);
                        sections  = gson.fromJson(response.toString(), listType);
//                        sections = new ArrayList<>();
//                        DataManager.getInstance().setItems(items);
//
                        DataManager.getInstance().setCategories(types);
                        for (SectionItem  sectionItem : sections) {


                            for (Object  activityTrip : sectionItem.getActivities()) {
                                DataManager.getInstance().items.add((ActivityTrip) activityTrip);
                            }


                        }
                        mainAdapter2 = new MainAdapter2(sections, types);
                        mItemsList.setAdapter(mainAdapter2);
                        showProgress(false);
                        if (sections.size() <= 0) {

                            showError(R.string.no_internet, true);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        showProgress(false);
                        Snackbar.make(mItemsList.getRootView(), anError.getErrorBody(), Snackbar.LENGTH_SHORT).show();

                        showError(R.string.no_internet, true);

                    }
                });

            }

            @Override
            public void onError(ANError anError) {
                showProgress(false);
                Snackbar.make(mItemsList.getRootView(), anError.getErrorBody(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setIconifiedByDefault(false);

        EditText txtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.WHITE);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.v("fff", "close2");
                    mainAdapter2.setItems(sections);
                    search = false;

                    showError(R.string.no_internet, false);

                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    mainAdapter2.setItems(sections);
                    search = false;
                    showError(R.string.no_data, false);
                    return true;

                }
                List<ActivityTrip> activityTrips = new ArrayList<>();
                List<SectionItem<ActivityTrip>> list = new ArrayList<>();

                for (ActivityTrip item : items) {
                    if (item.getTitle().contains(query)) {
                        activityTrips.add(item);
                    }

                }
                SectionItem<ActivityTrip> sectionItem = new SectionItem<>("", activityTrips);
                list.add(sectionItem);
                search = true;
                mainAdapter2.setItems(list);
                if (activityTrips.size() == 0) {
                    showError(R.string.no_data, true);

                } else
                    showError(R.string.no_data, false);
                searchView.setIconified(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s.trim())) {
                    mainAdapter2.setItems(sections);
                    search = false;

                    return true;

                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (search) {
            mainAdapter2.setItems(sections);
            search = false;
            showError(R.string.no_data, false);

        } else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    public void showProgress(boolean show) {
        mItemsList.setVisibility(show ? View.GONE : View.VISIBLE);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_activities) {
            Intent intent = new Intent(this, MyActivites.class);
            startActivity(intent);
        } else if (id == R.id.nav_favourite) {
            Intent intent = new Intent(this, FavouriteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_payment) {
            Intent intent = new Intent(this, PaymentRefActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_redeem) {
            Intent intent = new Intent(this, RewardActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_messages) {
            Intent intent = new Intent(this, InboxActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            Session.getInstance(MainActivity.this).logout();
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        } else if (id == R.id.lang) {
            final Dialog dialog = new Dialog(MainActivity.this); // Context, this, etc.
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_dialog, null, false);
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


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void setLocale(String lang) {
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(refresh);
//        finish();
//    }


    public void setLocale(String lang) {
//        final Locale loc = new Locale(lang);
//        Locale.setDefault(loc);
//        final Configuration cfg = new Configuration();
//        cfg.locale = loc;
//        getApplicationContext().getResources().updateConfiguration(cfg, null);



        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("lang", lang);
        editor.putBoolean("langSelected", true);
        editor.apply();
        editor.commit();
//        LocaleUtils.updateConfig(MainActivity.this,lang);
        if (editor.commit()){
            Intent mStartActivity = new Intent(getApplicationContext(), SplashActivity.class);
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




    private void showError(int String, boolean show) {
        mItemsList.setVisibility(show ? View.GONE : View.VISIBLE);
        mErrorTxt.setVisibility(show ? View.VISIBLE : View.GONE);
        mErrorTxt.setText(String);
    }

    private void showError(boolean show) {
        mItemsList.setVisibility(show ? View.GONE : View.VISIBLE);
        mErrorTxt.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onFragmentInteraction() {
        TextView name2 = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        ImageView img = navigationView.getHeaderView(0).findViewById(R.id.user_img);
        if (session.getUser().getFirstName().length() > 0)
            name2.setText(session.getUser().getName());
        else
            name2.setText("Login/Register");
        Glide.with(this).load(session.getUser().getUserPhotoUrl()).apply(new RequestOptions().error(R.drawable.backpack_icon_gray_watermark)).into(img);

    }
}
