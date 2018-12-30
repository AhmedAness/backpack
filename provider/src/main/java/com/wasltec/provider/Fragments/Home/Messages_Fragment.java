package com.wasltec.provider.Fragments.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.Adopters.Inbox_Adopter;
import com.wasltec.provider.Adopters.Online_items_list_adopter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.AllActivityReturnOpj;
import com.wasltec.provider.model.Inbox_model;
import com.wasltec.provider.model.OverviewReturnOpj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.wasltec.provider.Activities.Home.toolbar;

public class Messages_Fragment extends Fragment {

    private View view;
    private LinearLayout mContainer,empty_msg;
    RecyclerView inbox_list;
    RotateLoading loader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        toolbar.setTitle(R.string.messages);

        view =  inflater.inflate(R.layout.messages_fragment_layout, container, false);

        mContainer = (LinearLayout) view.findViewById(R.id.container);
        empty_msg = (LinearLayout) view.findViewById(R.id.empty_msg);
        inbox_list = (RecyclerView) view.findViewById(R.id.inbox_list);

        empty_msg.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);

        loader = (RotateLoading) view.findViewById(R.id.rotateloading);
        loader.start();

        AndroidNetworking.get(URLManger.getInstance().getInbox())
                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<Inbox_model>>() {
                }.getType();
                Gson gson = new Gson();

                ArrayList<Inbox_model> data = gson.fromJson(response.toString(), listType);

                if (data.size()>0)
                {
                    inbox_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    inbox_list.setAdapter(new Inbox_Adopter(getActivity(),data));

                    mContainer.setVisibility(View.VISIBLE);
                }
               else
                    empty_msg.setVisibility(View.VISIBLE);
                if (loader.isStart())
                loader.stop();

            }

            @Override
            public void onError(ANError anError) {
                empty_msg.setVisibility(View.VISIBLE);
                if (loader.isStart())
                    loader.stop();
            }
        });

        return view;
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
        super.onResume();
        toolbar.setTitle(R.string.messages);

    }

}
