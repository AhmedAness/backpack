package com.wasltec.provider.Fragments.Add_new_Activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.RulesAdapter;
import com.wasltec.provider.Adopters.Rules_Adopter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityRule;
import com.wasltec.provider.model.User_res;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.activityDetails;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;

public class Step5 extends Fragment {

    private View view;
    RecyclerView recyclerView;
    private EditText mRequirements;
    List<ActivityRule> rules_data = new ArrayList<>();
    RulesAdapter rules_adopter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.rulesandrequirment));
        if (Add_new_activity.loader.isStart())
            Add_new_activity.loader.stop();
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step5, container, false);
rules_adopter=new RulesAdapter();
        recyclerView = view.findViewById(R.id.rules_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        initView(view);

        EditText rule = view.findViewById(R.id.add_another_rule);
        if(mode==2)
            mRequirements.setText(activityDetails.getRequirements());

        view.findViewById(R.id.edit_text).setOnClickListener(v -> {
            // TODO: 9/13/2018 add api
            if (rule.getText().toString().length() > 1) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("description", rule.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URLManger.getInstance().getCreateRule())
                        .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    rules_data.add(
                                            new ActivityRule(String.valueOf(response.getInt("rule_id")), rule.getText().toString()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                rule.setText("");
                                rules_adopter = new RulesAdapter(rules_data);
                                recyclerView.setAdapter(rules_adopter);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });


            }
        });
        rule.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (rule.getText().toString().length() > 1) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("description", rule.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AndroidNetworking.post(URLManger.getInstance().getCreateRule())
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                            .addJSONObjectBody(jsonObject)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        rules_data.add(new ActivityRule(String.valueOf(response.getInt("rule_id")), rule.getText().toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    rule.setText("");
                                    rules_adopter = new RulesAdapter(rules_data);
                                    recyclerView.setAdapter(rules_adopter);
                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });


                }

            }
            return true;
        });


        AndroidNetworking.get(URLManger.getInstance().getAllRules())
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsObjectList(ActivityRule.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        rules_data = (List<ActivityRule>) response;
                        if (mode==2)
                        for (int i = 0; i < rules_data.size(); i++) {
                            for (int j = 0; j < activityDetails.getActivityRules().size(); j++) {
                                if (rules_data.get(i).getId() .equals(activityDetails.getActivityRules().get(j).getId()) ){
                                    rules_data.get(i).setSelected(true);
                                }
                            }
                        }
                        rules_adopter = new RulesAdapter(rules_data);
                        recyclerView.setAdapter(rules_adopter);

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                    }
                });


        return view;
    }

    private void initView(@NonNull final View view) {
        mRequirements = (EditText) view.findViewById(R.id.requirements);
    }

    public void valid() {
        if (mRequirements.getText().toString().length() < 1) {
            mRequirements.setError(getActivity().getResources().getString(R.string.this_field_is_required));
            mRequirements.requestFocus();
            if (Add_new_activity.loader.isStart())
                Add_new_activity.loader.stop();
        } else {

            JSONObject jsonObject = new JSONObject();
            try {
                JSONArray jsonElements = new JSONArray();
                for (ActivityRule activityRule : rules_adopter.getPrices()) {
                    if (activityRule.isSelected()) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("rule_id", activityRule.getId());
                        jsonElements.put(jsonObject1);
                    }

                }
                jsonObject.put("Activity_Rules", jsonElements);
                jsonObject.put("requirements", mRequirements.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post(URLManger.getInstance().setStepFive(ActivityID,mode))
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
                            }
                            else {
                                Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step1));

                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                        .replace(R.id.container1, Add_new_activity.stepStatus).commit();
                                Add_new_activity.step_number = -1;

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

    }

    @Override
    public void onResume() {
        super.onResume();
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.rulesandrequirment));

    }


}