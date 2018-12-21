package com.wasltec.backpack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.Adapters.Inboxadopter;
import com.wasltec.backpack.models.InboxModel;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    private RecyclerView mInboxList;

    private TextView mNoData;
    private Inboxadopter inboxadopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mInboxList = findViewById(R.id.inbox_list);
        mNoData = findViewById(R.id.no_data);
        mNoData.setVisibility(View.VISIBLE);


        AndroidNetworking.get(URLManager.getInstance().getCustomerInbox())
                .addHeaders("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiI1IiwiZXhwIjoxNTM2MjM1MDQwLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjYzOTM5LyIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NjM5MzkvIn0.7EbPFC3aM3LJyEtQjtIySXcDHGp7rmz8v8xF9o5dQpw")
//                .addHeaders("Authorization", "bearer "+ Session.getInstance(InboxActivity.this ).getToken())
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<InboxModel>>() {
                }.getType();

                Gson gson=new Gson();


                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(InboxActivity.this,
                        LinearLayoutManager.VERTICAL, false);
                mInboxList.setLayoutManager(horizontalLayoutManagaer);

                inboxadopter= new Inboxadopter(gson.fromJson(response.toString(), listType),InboxActivity.this);
                mInboxList.setAdapter(inboxadopter);
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }





}
