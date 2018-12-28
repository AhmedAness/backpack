package com.wasltec.provider.Fragments.Home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.util.ListUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.Activities.Calender;
import com.wasltec.provider.Activities.CustomisedCalender;
import com.wasltec.provider.Adopters.ActivityCategoryAdapter;
import com.wasltec.provider.Adopters.Activity_list_overview;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.AllActivityReturnOpj;
import com.wasltec.provider.model.OverviewReturnOpj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.wasltec.provider.Activities.Home.actoinbar;
import static com.wasltec.provider.Activities.Home.toolbar;

public class Overview_Fragment extends Fragment implements ActivityCategoryAdapter.ItemClickListener, Activity_list_overview.ItemClickListener {

    private View view;
    private ActivityCategoryAdapter adapter;

    private Gson gson;
    public static List<AllActivityReturnOpj> items;
    RecyclerView recyclerView;
    private RotateLoading rotateloading,rotateloading2;
    int loaderindex=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        actoinbar.setDisplayHomeAsUpEnabled(false);
        actoinbar.setDisplayShowHomeEnabled(true);


        toolbar.setTitle(R.string.overview);
        gson= new Gson();
        view =  inflater.inflate(R.layout.overview_fragment_layout, container, false);


        // set up the RecyclerView
        rotateloading = view.findViewById(R.id.rotateloading);
        rotateloading2 = view.findViewById(R.id.rotateloading2);
        recyclerView = view.findViewById(R.id.category_recycer);
        adapter = new ActivityCategoryAdapter(getActivity());
        adapter.setClickListener(this);


        rotateloading.start();

        AndroidNetworking.get(URLManger.getInstance().getGetAllActivities())
                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                            if (rotateloading.isStart())
                                rotateloading.stop();

                        Type listType = new TypeToken<List<AllActivityReturnOpj>>() {
                        }.getType();

                        List<AllActivityReturnOpj> Online_items ,offline_items;
                        try {
                            Online_items = gson.fromJson(response.get("onlinneActivites").toString(), listType);

                        }catch (Exception e ){
                            Online_items= new ArrayList<>();
                            e.printStackTrace();
                        }
                        try {
                            offline_items = gson.fromJson(response.get("offlineActivities").toString(), listType);


                        }catch (Exception e ){
                            offline_items= new ArrayList<>();
                            e.printStackTrace();
                        }
                        List<AllActivityReturnOpj> newList = new ArrayList<AllActivityReturnOpj>(Online_items);
                        newList.addAll(offline_items);

                        try {
                            adapter.setData( newList);
                            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(horizontalLayoutManagaer);
                            recyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getMessage());

                            if (rotateloading.isStart())
                                rotateloading.stop();
                    }
                });





        adapter.setClickListener(new ActivityCategoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),CustomisedCalender.class);
                intent.putExtra("Activity_id",""+adapter.getData().get(position).getId());
                startActivity(  intent);

            }
        });
        return view;
    }

    private void Setuprecyclerview() {

        RecyclerView recyclerView1 = view.findViewById(R.id.list2);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(horizontalLayoutManagaer1);
        Activity_list_overview adapter1 = new Activity_list_overview(getContext(),getActivity());
        adapter1.setClickListener(this);



        rotateloading2.start();

        AndroidNetworking.get(URLManger.getInstance().getUpcomingActivity())
                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                            if (rotateloading2.isStart())
                                rotateloading2.stop();
                        Type listType = new TypeToken<List<OverviewReturnOpj>>() {
                        }.getType();
                        gson=new Gson();

                        ArrayList<OverviewReturnOpj> data = gson.fromJson(response.toString(), listType);

                        adapter1.setData( data );
                        recyclerView1.setAdapter(adapter1);


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getMessage());

                            if (rotateloading2.isStart())
                                rotateloading2.stop();
                    }
                });

    }

    @Override
    public void onItemClick(View view, int position) {
//        Intent intent=new Intent(getActivity(), Calender.class);
//        intent.putExtra("Activity_id",adapter.getItem(position).getId());
//        getActivity().startActivity(intent);
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onResume() {
        toolbar.setTitle(R.string.overview);
        Setuprecyclerview();
        super.onResume();
    }
}
