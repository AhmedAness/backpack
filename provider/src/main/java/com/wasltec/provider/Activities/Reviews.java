package com.wasltec.provider.Activities;

import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.provider.Fragments.reviews.Critical;
import com.wasltec.provider.Fragments.reviews.Good;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.Utils.Post_type;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.AllActivityReturnOpj;
import com.wasltec.provider.model.Post_item;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reviews extends AppCompatActivity {

    private static final String TAG ="Reviews" ;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Resources res;
    LinearLayout good,cretical;
    LinearLayout selector_good,selector_cretical;
    Good good_opj;
    Critical critical_opj;
    boolean flag_fragment= true;
    public static Toolbar toolbar_review ;
    public  static List<Post_item> good_pastes =new ArrayList<>();
    public  static List<Post_item> cretical_pastes =new ArrayList<>();
    List<Post_item> pastes =new ArrayList<>();
    private ActionBar actoinbar;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        int activity_id =getIntent().getExtras().getInt("Activity_id");
        String activity_obj =getIntent().getExtras().getString("Activity_object");
        AllActivityReturnOpj myactivity;
        gson=new Gson();
        Type listType = new TypeToken<AllActivityReturnOpj>() {
        }.getType();
        try {
              myactivity = gson.fromJson(activity_obj, listType);
        }catch (Exception e ){
            myactivity= new AllActivityReturnOpj();
            e.printStackTrace();
        }



        callserver(activity_id);
//        callserver(3);

        View child = getLayoutInflater().inflate(R.layout.review_toolbar, null);

        gson= new Gson();

        toolbar_review  = findViewById(R.id.toolbar);
        actoinbar= getSupportActionBar();


        TextView title =  child.findViewById(R.id.title);
        TextView activity_name =  child.findViewById(R.id.activity_name);
        RatingBar ratingBar =  child.findViewById(R.id.rate_vote);
        TextView rate_num =  child.findViewById(R.id.rate_num);

        ratingBar.setRating(myactivity.getRate());
        title.setText(getResources().getString(R.string.reviews));
        activity_name.setText(myactivity.getTitle());
        rate_num.setText(myactivity.getRate()+"");

        toolbar_review.setTitle(" ");

        toolbar_review.addView(child);

        setSupportActionBar(toolbar_review);

        init();
        clicks();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        res = getResources();
        good_opj = new Good();
          critical_opj = new Critical();
//        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.container1, good_opj).commit();
//        flag_fragment=true;


    }

    private void callserver(int activity_id) {

        AndroidNetworking.get(URLManger.getInstance().getGetActivityReview(activity_id))
                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(Reviews.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {


                            Type listType = new TypeToken<List<Post_item>>() {
                            }.getType();
                            try {
                                pastes = gson.fromJson(response.toString(), listType);
                                fillter();
                                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.container1, good_opj).commit();
                                flag_fragment=true;


                            }catch (Exception e )
                            {
                                Log.d(TAG, "onResponse:"+e.getMessage());
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d(TAG, "onResponse:"+anError.getMessage());
                        }
                    });
//        pastes =get_activity_postes(activity_id);

    }

    private void fillter() {
        good_pastes= new ArrayList<>();
        cretical_pastes= new ArrayList<>();

        for (int i = 0; i < pastes.size(); i++) {
            if (pastes.get(i).getRate()> 2)
                good_pastes.add(pastes.get(i));
            else
                cretical_pastes.add(pastes.get(i));

        }
    }

//    private List<Post_item> get_activity_postes(int activity_id) {
//        List<Post_item> post_items= new ArrayList<>();
////        DateFormat df = null;
////        String date;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
////            df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
////              date = df.format(Calendar.getInstance().getTime());
////        }
//
//        for (int i = 0; i <10 ; i++) {
//            post_items.add(new Post_item("url"+i,"name "+i,"desc"+i,i%5,new Date(),(i>5)?1:2));
//        }
//        return post_items;
//    }

    private void clicks() {
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag_fragment)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container1, good_opj).commit();
                    flag_fragment=true;
                    update_select();
                }

            }
        });
        cretical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_fragment)
                {
                    getSupportFragmentManager().beginTransaction().replace (R.id.container1, critical_opj).commit();
                    flag_fragment=false;
                    update_select();
                }
            }
        });
    }

    private void update_select() {
        if (flag_fragment)
        {
            selector_good.setVisibility(View.VISIBLE);
            selector_cretical.setVisibility(View.GONE);
        }else
        {
            selector_good.setVisibility(View.GONE);
            selector_cretical.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        good= findViewById(R.id.good);
        cretical= findViewById(R.id.cretical);
        selector_good= findViewById(R.id.selector_good);
        selector_cretical= findViewById(R.id.selector_cretical);
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
            Reviews.this.finish();
    }
}
