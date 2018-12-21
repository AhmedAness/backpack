package com.wasltec.backpack.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.Adapters.AllAdapter;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.fragment.FilterDialog;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.ItemsCategory;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllActivity extends AppCompatActivity implements FilterDialog.Listener {

    private LinearLayout mContainer;
    private Button mFilter;
    private RecyclerView mList;
    AllAdapter allAdapter=new AllAdapter();
    private List<Integer> currentCategories;
    private boolean search = false;
    private TextView mErrorTxt;
    private ProgressBar progress;
    private CoordinatorLayout main_content;
    static int typeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            if (search) {

//                allAdapter.setItems(DataManager.getInstance().getSpecificItems(currentCategories));
                getactivittiesbyid();
                search = false;

                showError(R.string.no_data, false);

            } else
                onBackPressed();
        });

          typeID = getIntent().getIntExtra("ID", 0);
        for (ItemsCategory itemsCategory : DataManager.getInstance().getCategories()) {
            if (itemsCategory.getID() == typeID) {
                toolbar.setTitle(itemsCategory.getTitle() + " activities nearby jeddah");
            }
        }
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentCategories = new ArrayList<>();
        mContainer = findViewById(R.id.container);
        mFilter = findViewById(R.id.filter);
        progress = findViewById(R.id.progress);
        main_content = findViewById(R.id.main_content);
        mList = findViewById(R.id.list);
        Log.v(AllActivity.class.getName(), typeID + " ");
        currentCategories.add(typeID);
        for (ItemsCategory type : MainActivity.types) {
            Log.v(AllActivity.class.getName(), MainActivity.types + " ");

            if ((type.getID() != typeID)) {
                Button button = new Button(this, null, android.R.attr.buttonStyleSmall);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginEnd((int) getResources().getDimension(R.dimen._5sdp));
                button.setLayoutParams(layoutParams);
                button.setId(View.generateViewId());
                button.setText(type.getTitle().trim());
                button.setTag(type.getID());
                int padding = (int) getResources().getDimension(R.dimen._5sdp);
                button.setPadding(padding, padding, padding, padding);

                button.setTextColor(getResources().getColor(android.R.color.white));
                button.setBackground(getResources().getDrawable(R.drawable.types_btn_backgroud));
                button.setOnClickListener(v -> {
//                    v.setSelected(!v.isSelected());
//                    if (currentCategories.contains(type.getID())) {
//                        currentCategories.remove((Integer) type.getID());
//
//                    } else
//                    {
//                        currentCategories.add(type.getID());
//                        typeID=type.getID();
//                    }
//                    allAdapter.setItems(DataManager.getInstance().getSpecificItems(currentCategories));
                    typeID=type.getID();
                    getactivittiesbyid();
                });


                mContainer.addView(button);
            }

        }

        Button button = new Button(this, null, android.R.attr.buttonStyleSmall);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginEnd((int) getResources().getDimension(R.dimen._5sdp));
        button.setLayoutParams(layoutParams);
        button.setId(View.generateViewId());
        button.setText(R.string.all_activites_btn);
        int padding = (int) getResources().getDimension(R.dimen._5sdp);
        button.setPadding(padding, padding, padding, padding);
        button.setTextColor(getResources().getColor(android.R.color.white));
        button.setBackground(getResources().getDrawable(R.drawable.types_btn_backgroud));

        button.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());

//            for (int i = 0; i < mContainer.getChildCount() - 1; i++) {
//                Button button1 = (Button) mContainer.getChildAt(i);
//                if (!button1.isSelected()) {
//                    currentCategories.add((Integer) button1.getTag());
//                    button1.setSelected(true);
//                }
//            }
//            allAdapter.setItems(DataManager.getInstance().getItems());
            startActivity(new Intent(AllActivity.this,MainActivity.class));
            finish();
        });
        mContainer.addView(button);


        mList.setLayoutManager(new LinearLayoutManager(this));

        //call with type id
//        allAdapter = new AllAdapter(DataManager.getInstance().getSpecificItems(currentCategories));

        getactivittiesbyid();
        mList.setAdapter(allAdapter);

        mFilter.setOnClickListener(v -> {
            FilterDialog activity = new FilterDialog();
            activity.show(getFragmentManager(), "5");


        });

        mErrorTxt = findViewById(R.id.error_txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.WHITE);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.v("fff", "close2");
//                    allAdapter.setItems(DataManager.getInstance().getSpecificItems(currentCategories));


                    getactivittiesbyid();

                    search = false;

                    showError(R.string.no_internet, false);

                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    if (TextUtils.isEmpty(query)) {
//                        allAdapter.setItems(DataManager.getInstance().getSpecificItems(currentCategories));

                        getactivittiesbyid();
                        showError(R.string.no_data, false);
                        search = false;
                        return true;
                    }
                    List<ActivityTrip> activityTrips = new ArrayList<>();

                    for (ActivityTrip item : allAdapter.items) {
                        if (item.getTitle().contains(query)) {
                            activityTrips.add(item);
                        }

                    }
                allAdapter.setItems(activityTrips);

                search = true;
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
//                    allAdapter.setItems(DataManager.getInstance().getSpecificItems(currentCategories));
                    getactivittiesbyid();
                    showError(R.string.no_internet, false);
                    search = false;
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onResult(List<ActivityTrip> trips) {
        allAdapter.setItems(trips);
    }

    private void showError(int String, boolean show) {
        mList.setVisibility(show ? View.GONE : View.VISIBLE);
        mErrorTxt.setVisibility(show ? View.VISIBLE : View.GONE);
        mErrorTxt.setText(String);
    }

    @Override
    public void onBackPressed() {
        if (search) {
//            allAdapter.setItems(DataManager.getInstance().getSpecificItems(currentCategories));
            getactivittiesbyid();
            search = false;
            showError(R.string.no_data, false);

        } else {
            super.onBackPressed();
        }
    }

    private void showError(boolean show) {
        mList.setVisibility(show ? View.GONE : View.VISIBLE);
        mErrorTxt.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void getactivittiesbyid() {
        showProgress(true);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MapLat", "0");
            jsonObject.put("MapLng", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(URLManager.getInstance().getActivitiesByCategory(typeID + ""))
                .addHeaders("Authorization", "bearer "+ Session.getInstance(AllActivity.this).getToken())
                .addJSONObjectBody(jsonObject)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<ActivityTrip>>() {
                }.getType();

                Gson gson=new Gson();



                allAdapter.setItems(gson.fromJson(response.toString(), listType));
                mList.setAdapter(allAdapter);
                showProgress(false);



            }

            @Override
            public void onError(ANError anError) {



                showProgress(false);
                Snackbar.make(mList.getRootView(), anError.getErrorBody(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showProgress(boolean show) {
        main_content.setVisibility(show ? View.GONE : View.VISIBLE);
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
