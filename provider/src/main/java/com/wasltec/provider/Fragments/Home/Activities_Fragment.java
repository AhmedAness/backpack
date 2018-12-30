package com.wasltec.provider.Fragments.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.Online_items_list_adopter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.AllActivityReturnOpj;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.wasltec.provider.Activities.Home.actoinbar;
import static com.wasltec.provider.Activities.Home.toolbar;

public class Activities_Fragment extends Fragment  {

    private View view;

    private Gson gson;
    public static List<AllActivityReturnOpj> Online_items ,offline_items,incomplate;
    public  List<AllActivityReturnOpj> f_Online_items ,f_offline_items,f_incomplate;
    RecyclerView Online_items_list;
    TextView online_pointer,offline_pointer,incomplate_pointer;
    private boolean search = false;
    TextView msearch_icon ;
    HorizontalScrollView search_container;
    LinearLayout filter_container;
    EditText msearch_text ;
    ImageView mclose_searsh;
    Online_items_list_adopter list_adopter;
    RotateLoading rotateloading;

    RelativeLayout loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        actoinbar.setDisplayHomeAsUpEnabled(false);
        actoinbar.setDisplayShowHomeEnabled(true);
        gson= new Gson();

        toolbar.setTitle(R.string.activities);

        view =  inflater.inflate(R.layout.empty_activity_fragment_layout, container, false);


        view.findViewById(R.id.addnewActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Add_new_activity.class));
//
            }
        });


        filter_pointers();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Add_new_activity.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


        Online_items_list= view.findViewById(R.id.online_list);
        mclose_searsh= view.findViewById(R.id.close_searsh);
        loader= view.findViewById(R.id.loader);


        msearch_icon= view.findViewById(R.id.search_icon);
        msearch_text= view.findViewById(R.id.search_text);
        msearch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!search){
                show_search(search);//false
                search=true;
                mclose_searsh.setVisibility(View.VISIBLE);
                msearch_icon.setVisibility(View.GONE);
                    msearch_text.setFocusable(true);

                }
            }
        });
        mclose_searsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search) {
                    show_search(search);//true
                    search = false;
                    msearch_text.setText("");
                    mclose_searsh.setVisibility(View.GONE);
                    msearch_icon.setVisibility(View.VISIBLE);
                    msearch_text.setFocusable(false);
                }
            }
        });
        msearch_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Fillter_activities(s);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }


        });

        search_container=view.findViewById(R.id.container3);
        filter_container=view.findViewById(R.id.container2);
        rotateloading=view.findViewById(R.id.rotateloading);


//        KProgressHUD hud = KProgressHUD.create(getActivity())
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
//        hud.setProgress(90);

        rotateloading.start();
        loader.setVisibility(View.VISIBLE);

        AndroidNetworking.get(URLManger.getInstance().getGetAllActivities())
                .addHeaders("Authorization", "bearer "+SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        loader.setVisibility(View.GONE);
                        int empty = 0;
                        online_pointer.setEnabled(true);
                        offline_pointer.setEnabled(true);
                        incomplate_pointer.setEnabled(true);
                        Type listType = new TypeToken<List<AllActivityReturnOpj>>() {
                        }.getType();
                        try {
                            Online_items = gson.fromJson(response.get("onlinneActivites").toString(), listType);
                            (view.findViewById(R.id.container)).setVisibility(View.VISIBLE);
                            if (Online_items.size() == 0) empty += 1;
                        }catch (Exception e ){
                            Online_items= new ArrayList<>();
                            e.printStackTrace();
                        }
                        try {
                            offline_items = gson.fromJson(response.get("offlineActivities").toString(), listType);
                            (view.findViewById(R.id.container)).setVisibility(View.VISIBLE);
                            if (offline_items.size() == 0) empty += 1;

                        }catch (Exception e ){
                            offline_items= new ArrayList<>();
                            e.printStackTrace();
                        }
                        try {
                            incomplate = gson.fromJson(response.get("incompleteActivites").toString(), listType);
                            (view.findViewById(R.id.container)).setVisibility(View.VISIBLE);
                            if (incomplate.size() == 0) empty += 1;

                        }catch (Exception e ){
                            incomplate= new ArrayList<>();
                            e.printStackTrace();
                        }

                        if (empty >= 3)
                            (view.findViewById(R.id.activity_emptypage)).setVisibility(View.VISIBLE);
                        List<AllActivityReturnOpj> inctest = new ArrayList<>();

                        Online_items_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        list_adopter=new Online_items_list_adopter(getActivity(),Online_items,offline_items,incomplate);
                        Online_items_list.setAdapter(list_adopter);
//                        Online_items_list.setAdapter(new Online_items_list_adopter(getActivity(),inctest,inctest,incomplate));
                        if (rotateloading.isStart())
                        rotateloading.stop();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(),""+anError.getMessage(),Toast.LENGTH_LONG).show();
                        loader.setVisibility(View.GONE);
                        if (rotateloading.isStart())
                            rotateloading.stop();

                    }
                });

//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        (view.findViewById(R.id.container)).setVisibility(View.VISIBLE);
//                        (view.findViewById(R.id.activity_emptypage)).setVisibility(View.GONE);
//
//                        online_pointer.setEnabled(true);
//                        offline_pointer.setEnabled(true);
//                        incomplate_pointer.setEnabled(true);
//                        Type listType = new TypeToken<List<AllActivityReturnOpj>>() {
//                        }.getType();
//                        try {
//                            Online_items = gson.fromJson(response.get(2).toString(), listType);
//                        }catch (Exception e ){
//                            Online_items= new ArrayList<>();
//                            e.printStackTrace();
//                        }
//                        try {
//                            offline_items = gson.fromJson(response.get(1).toString(), listType);
//                        }catch (Exception e ){
//                            offline_items= new ArrayList<>();
//                            e.printStackTrace();
//                        }
//                        try {
//                            incomplate = gson.fromJson(response.get(0).toString(), listType);
//                        }catch (Exception e ){
//                            incomplate= new ArrayList<>();
//                            e.printStackTrace();
//                        }
//
//
//                        Online_items_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//                        Online_items_list.setAdapter(new Online_items_list_adopter(getActivity(),Online_items,offline_items,incomplate));
//
//
//
//
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Toast.makeText(getActivity(),""+anError.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });








        return view;
    }

    private void filter_pointers() {



        online_pointer = view.findViewById(R.id.online_pointer);
        offline_pointer =view.findViewById(R.id.offline_pointer);
        incomplate_pointer =view.findViewById(R.id.incomplete_pointer);




        online_pointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Online_items_list.scrollToPosition(0);
            }
        });
        offline_pointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (offline_items.size()>1)
//                Online_items_list.scrollToPosition(Online_items.size()+1);
//                else
//                Online_items_list.scrollToPosition(Online_items.size());

                if (offline_items.size()>1)
                    ((LinearLayoutManager)Online_items_list.getLayoutManager()).
                            scrollToPositionWithOffset(Online_items.size()+1,0);
                else
                    ((LinearLayoutManager)Online_items_list.getLayoutManager()).
                            scrollToPositionWithOffset(Online_items.size(),0);
            }
        });
        incomplate_pointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (incomplate.size()>1)
                    ((LinearLayoutManager)Online_items_list.getLayoutManager()).
                            scrollToPositionWithOffset(offline_items.size()+Online_items.size()+2,0);
                else
                    ((LinearLayoutManager)Online_items_list.getLayoutManager()).
                            scrollToPositionWithOffset(offline_items.size()+Online_items.size(),0);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(true);


        super.onCreateOptionsMenu(menu,inflater);
    }

     
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(R.string.activities);

    }


    public void show_search(boolean display ){
        if (display){
            search_container.setVisibility(View.GONE);
            filter_container.setVisibility(View.VISIBLE);
        }else{
            search_container.setVisibility(View.VISIBLE);
            filter_container.setVisibility(View.GONE);
        }
    }

    private void Fillter_activities(CharSequence s) {

        if (TextUtils.isEmpty(s)) {
            Online_items_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
             list_adopter.setData(Online_items,offline_items,incomplate);
        }
        else
        {
            f_Online_items= new ArrayList<>();
            for(AllActivityReturnOpj activity:Online_items)
               if(activity.getTitle().contains(s))
                    f_Online_items.add(activity);

            f_offline_items= new ArrayList<>();
            for(AllActivityReturnOpj activity:offline_items)
               if(activity.getTitle().contains(s))
                   f_offline_items.add(activity);

            f_incomplate= new ArrayList<>();
            for(AllActivityReturnOpj activity:incomplate)
               if(activity.getTitle().contains(s))
                   f_incomplate.add(activity);



            list_adopter.setData(f_Online_items,f_offline_items,f_incomplate);
        }

    }

}
