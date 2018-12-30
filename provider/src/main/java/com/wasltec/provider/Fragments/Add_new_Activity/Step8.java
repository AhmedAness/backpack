package com.wasltec.provider.Fragments.Add_new_Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.CapcityAdapter;
import com.wasltec.provider.R;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Price;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wasltec.provider.Activities.Add_new_activity.mode;
import static com.wasltec.provider.Utils.App_Context.TAG;

public class Step8 extends Fragment {

    Spinner time_spinner, different_categories_sp, each_category_have_spacefic_capacity_sp;
    private View view;
    private static final String[] time_spinner_paths = {" Minutes", " Hours", " Days"};
    private static final String[] different_categories_sp_paths = {" _"," No", " Yes"};
    private static final String[] each_category_have_spacefic_capacity_sp_paths = {" _"," No", " Yes"};
    private RecyclerView mList;
    private Spinner mDifferentCategoriesSp;
    private EditText mCpacityNumberForActivity;
    private EditText mMaxNumberGroup;
    private EditText mMinNumberGroup;
    private EditText mTimeTxt;
    CapcityAdapter capcityAdapter;
    private CheckBox mUnlimitedCapacity;
    LinearLayout mMaxMinGroup;
    boolean isGroup = false;
    LinearLayout capacity_layout;
    LinearLayout capacity_num_for_activity;
    String tmp="0";

    LinearLayout li1,li2,li3,li4;

    public void initViews(View view) {
        mList = (RecyclerView) view.findViewById(R.id.list);
        mCpacityNumberForActivity = (EditText) view.findViewById(R.id.cpacity_number_for_activity);
        mMaxNumberGroup = (EditText) view.findViewById(R.id.max_number_group);
        mMinNumberGroup = (EditText) view.findViewById(R.id.min_number_group);
        mTimeTxt = view.findViewById(R.id.time_txt);
        mUnlimitedCapacity = view.findViewById(R.id.unlimited_capacity);
        capacity_layout=view.findViewById(R.id.capacity_layout);
        capacity_num_for_activity=view.findViewById(R.id.capacity_num_for_activity);
        mUnlimitedCapacity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                     tmp = mCpacityNumberForActivity.getText().toString();
                    mCpacityNumberForActivity.setText(R.string.unlimated_txt);
                    mCpacityNumberForActivity.setEnabled(false);
                } else {
                    mCpacityNumberForActivity.setText(tmp);
                    mCpacityNumberForActivity.setEnabled(true);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.lengthandcapacity));
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step8, container, false);
        initViews(view);
        if (Add_new_activity.loader.isStart())
            Add_new_activity.loader.stop();

        mMaxMinGroup = view.findViewById(R.id.max_min_group);
        time_spinner = view.findViewById(R.id.time_spinner);
        different_categories_sp = view.findViewById(R.id.different_categories_sp);
        li1=view.findViewById(R.id.li1);
        li2=view.findViewById(R.id.li2);
        li3=view.findViewById(R.id.li3);
        li4=view.findViewById(R.id.li4);


            Add_new_activity.loader.start();
        AndroidNetworking.get(URLManger.getInstance().getGetActivityDetails(String.valueOf(ActivityID)))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    isGroup = response.getJSONObject(0).getBoolean("bookingAvailableForGroups");
                    if (isGroup) {
                        mMaxMinGroup.setVisibility(View.VISIBLE);
                        li4.setVisibility(View.VISIBLE);
                        isGroup = true;
                    } else {
                        mMaxMinGroup.setVisibility(View.GONE);
                        li4.setVisibility(View.GONE);
                        isGroup = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();

            }

            @Override
            public void onError(ANError anError) {
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                Toast.makeText(getActivity(),anError.getErrorDetail(),Toast.LENGTH_SHORT).show();
            }
        });
        each_category_have_spacefic_capacity_sp = view.findViewById(R.id.each_category_have_spacefic_capacity_sp);
        List<Price> list = new ArrayList<>();
        list.add(new Price("Categories", "Capacity"));

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        capcityAdapter = new CapcityAdapter(list, new CapcityAdapter.ChangeListener() {
            @Override
            public void onChange(int totalcapcity) {
                if (mUnlimitedCapacity.isChecked()){
                    mCpacityNumberForActivity.setText(R.string.unlimated_txt);

                }else {
                    mCpacityNumberForActivity.setText(String.valueOf(totalcapcity));
                }
            }
        });
        mList.setAdapter(capcityAdapter);
        time_spinner_fn();
        different_categories_sp_fn();
        each_category_have_spacefic_capacity_sp_fn();

        if (mode==2){
            mTimeTxt.setText(""+ Add_new_activity.activityDetails.getActivityLength());
            mMinNumberGroup.setText(""+Add_new_activity.activityDetails.getMinCapacityGroup());
            mMaxNumberGroup.setText(""+Add_new_activity.activityDetails.getMaxCapacityGroup());
           if (Add_new_activity.activityDetails.getIndividualCategories().size()>0)
           {
               different_categories_sp.setSelection(1);

               for (int i = 0; i <Add_new_activity.activityDetails.getIndividualCategories().size(); i++) {

                   list.add(
                           new Price(Add_new_activity.activityDetails.getIndividualCategories().get(i).getName(),
                          ""+ Add_new_activity.activityDetails.getIndividualCategories().get(i).getCapacity(),
                                   Add_new_activity.activityDetails.getIndividualCategories().get(i).getId()
                           )
                         );
               }

               mList.setLayoutManager(new LinearLayoutManager(getContext()));
               capcityAdapter = new CapcityAdapter(list, new CapcityAdapter.ChangeListener() {
                   @Override
                   public void onChange(int totalcapcity) {
                       if (mUnlimitedCapacity.isChecked()){
                           mCpacityNumberForActivity.setText(R.string.unlimated_txt);

                       }else {
                           mCpacityNumberForActivity.setText(String.valueOf(totalcapcity));
                       }
                   }
               });
               mList.setAdapter(capcityAdapter);
           }
           else
               different_categories_sp.setSelection(2);


        }



        return view;
    }

    private void each_category_have_spacefic_capacity_sp_fn() {
        ArrayAdapter<String> each_category_have_spacefic_capacity_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, each_category_have_spacefic_capacity_sp_paths);
        each_category_have_spacefic_capacity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        each_category_have_spacefic_capacity_sp.setAdapter(each_category_have_spacefic_capacity_adapter);
        each_category_have_spacefic_capacity_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:

                        mList.setVisibility(View.GONE);
                        capacity_layout.setVisibility(View.VISIBLE);
                        mCpacityNumberForActivity.setEnabled(false);
                        mCpacityNumberForActivity.setText("");
                        mUnlimitedCapacity.setVisibility(View.GONE);
                        capacity_num_for_activity.setVisibility(View.GONE);
//                        li1.setVisibility(View.VISIBLE);
//                        li2.setVisibility(View.GONE);
//                        li3.setVisibility(View.GONE);

                        break;

                    case 1:
                        capcityAdapter.setHasCapacity(false);
                        mUnlimitedCapacity.setVisibility(View.VISIBLE);

                        mCpacityNumberForActivity.setEnabled(true);
                        mCpacityNumberForActivity.setText("");

                        mList.setVisibility(View.VISIBLE);

                        capacity_num_for_activity.setVisibility(View.VISIBLE);
                        li1.setVisibility(View.VISIBLE);
                        li2.setVisibility(View.VISIBLE);
                        li3.setVisibility(View.GONE);


                        break;
                    case 2:
                        // Whatever you want to happen when the first item gets selected
                        capcityAdapter.setHasCapacity(true);
                        mUnlimitedCapacity.setVisibility(View.GONE);

                        mList.setVisibility(View.VISIBLE);

                        capacity_num_for_activity.setVisibility(View.VISIBLE);
                        li1.setVisibility(View.VISIBLE);
                        li2.setVisibility(View.VISIBLE);
                        li3.setVisibility(View.VISIBLE);


                        try {

                        if(String.valueOf(capcityAdapter.getTotalCapacity()).length()>0){
                            mCpacityNumberForActivity.setText(String.valueOf(capcityAdapter.getTotalCapacity()));
                        }else {
                            mCpacityNumberForActivity.setText(tmp);
                        }
                        }catch (Exception e){
                            mCpacityNumberForActivity.setText(tmp);
                        }

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void different_categories_sp_fn() {


        ArrayAdapter<String> different_categories_sp_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, different_categories_sp_paths);


        different_categories_sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        different_categories_sp.setAdapter(different_categories_sp_adapter);
        different_categories_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mList.setVisibility(View.GONE);
                        capacity_layout.setVisibility(View.GONE);
                        mCpacityNumberForActivity.setEnabled(false);
                        mCpacityNumberForActivity.setText("");
                        mUnlimitedCapacity.setVisibility(View.GONE);
                        capacity_num_for_activity.setVisibility(View.GONE);
                        li1.setVisibility(View.VISIBLE);
                        li2.setVisibility(View.GONE);
                        li3.setVisibility(View.GONE);
                        break;
                    case 1:
                        mList.setVisibility(View.GONE);
                        capacity_layout.setVisibility(View.GONE);
                        mCpacityNumberForActivity.setEnabled(false);
                        mCpacityNumberForActivity.setText("");
                        capacity_num_for_activity.setVisibility(View.VISIBLE);
                        mUnlimitedCapacity.setVisibility(View.VISIBLE);
                        li1.setVisibility(View.VISIBLE);
                        li2.setVisibility(View.GONE);
                        li3.setVisibility(View.VISIBLE);

                        break;
                    case 2:
//                        mList.setVisibility(View.VISIBLE);
//                        capacity_layout.setVisibility(View.VISIBLE);
//                        mCpacityNumberForActivity.setEnabled(true);
//                        mCpacityNumberForActivity.setText(String.valueOf(capcityAdapter.getTotalCapacity()));
//                        capacity_num_for_activity.setVisibility(View.VISIBLE);
//                        mUnlimitedCapacity.setVisibility(View.GONE);
//                        li1.setVisibility(View.VISIBLE);
//                        li2.setVisibility(View.VISIBLE);
//                        li3.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> each_category_have_spacefic_capacity_adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, each_category_have_spacefic_capacity_sp_paths);
                        each_category_have_spacefic_capacity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        each_category_have_spacefic_capacity_sp.setAdapter(each_category_have_spacefic_capacity_adapter);

                        mList.setVisibility(View.GONE);
                        capacity_layout.setVisibility(View.VISIBLE);
                        mCpacityNumberForActivity.setEnabled(false);
                        mCpacityNumberForActivity.setText("");
                        capacity_num_for_activity.setVisibility(View.GONE);
                        mUnlimitedCapacity.setVisibility(View.GONE);
                        li1.setVisibility(View.VISIBLE);
                        li2.setVisibility(View.VISIBLE);
                        li3.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void time_spinner_fn() {
        ArrayAdapter<String> notice_in_advance_spinner_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, time_spinner_paths);


        notice_in_advance_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(notice_in_advance_spinner_adapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        Log.d(TAG, "onItemSelected: you choose item " + 0);
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        Log.d(TAG, "onItemSelected: you choose item " + 1);
                        break;
                    case 2:
                        Log.d(TAG, "onItemSelected: you choose item " + 2);
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void valid() {

        if (mTimeTxt.getText().toString().length() < 1) {
            mTimeTxt.setError(getString(R.string.this_field_is_required));
            mTimeTxt.requestFocus();
            if (Add_new_activity.loader.isStart())
                Add_new_activity.loader.stop();
            return;

        }

        if (isGroup) {
            if (mMaxNumberGroup.getText().toString().length() < 1) {
                mMaxNumberGroup.setError(getString(R.string.this_field_is_required));
                mMaxNumberGroup.requestFocus();
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                return;
            } else if (mMinNumberGroup.getText().toString().length() < 1) {
                mMinNumberGroup.setError(getString(R.string.this_field_is_required));
                mMinNumberGroup.requestFocus();
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();

                return;
            } else if (Integer.parseInt(mMaxNumberGroup.getText().toString()) <= Integer.parseInt(mMinNumberGroup.getText().toString())) {
                Add_new_activity.showDialog( R.string.max_notes, R.string.warning);
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                return;


            }

        }

        JSONObject jsonObject = new JSONObject();
        try {
            int length = (Integer.parseInt(mTimeTxt.getText().toString()));
            if (time_spinner.getSelectedItemPosition() == 1) {
                length *= 60;
            } else if (time_spinner.getSelectedItemPosition() == 2) {
                length *= 60 * 24;
            }
            jsonObject.put("Activity_length", length);
            if (TextUtils.isDigitsOnly(mCpacityNumberForActivity.getText().toString())) {

                jsonObject.put("totalCapacity", mCpacityNumberForActivity.getText().toString());
                jsonObject.put("capacityIsUnlimited", false);

            } else
            {
                jsonObject.put("capacityIsUnlimited", true);
                jsonObject.put("totalCapacity","0");

            }

            jsonObject.put("min_capacity_group", mMinNumberGroup.getText().toString());
            jsonObject.put("max_capacity_group", mMaxNumberGroup.getText().toString());

            JSONArray jsonArray = new JSONArray();
            for (int i = 1; i < capcityAdapter.getPrices().size(); i++) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",  capcityAdapter.getPrices().get(i).getId());
                jsonObject2.put("name", capcityAdapter.getPrices().get(i).getCategory());
                jsonObject2.put("capacity", capcityAdapter.getPrices().get(i).getPrice());

                jsonArray.put(jsonObject2);

            }

            jsonObject.put("individualCategories", jsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.v("Json", jsonObject.toString(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(URLManger.getInstance().setStepEight(ActivityID, mode))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mode == 2) {
                            Add_new_activity.step_number = 1;
                            mode = 1;
                            getActivity().finish();
                        } else {
                            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step4));
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step9).commit();
                            Add_new_activity.step_number = 9;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {


                        Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        if (Add_new_activity.loader.isStart())
                            Add_new_activity.loader.stop();
                    }
                });


    }
    @Override
    public void onResume() {
        super.onResume();
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.lengthandcapacity));

    }

}