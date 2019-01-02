package com.wasltec.provider.Fragments.Add_new_Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.R;

import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static android.support.constraint.Constraints.TAG;
import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.activityDetails;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;
import static com.wasltec.provider.Activities.Add_new_activity.step2;
import static com.wasltec.provider.Utils.App_Context.current_Activity;

public class Step1 extends Fragment {

    private View view;
    EditText activity_title;
    private RadioButton mRadioLand;
    private RadioButton mRadiowater;
    private RadioButton mRadiosky;
    private RadioButton mRadioEvent;
    private RadioGroup mRadio;
    private EditText mDisc;
    private LinearLayout mContainer;
    List<View> checkBoxes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.add_new_activity));
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step1, container, false);
        checkBoxes = new ArrayList<>();
        activity_title = view.findViewById(R.id.activity_title);
        initView(view);
        Add_new_activity.loader.start();

        if(mode==2){
            activity_title.setText(activityDetails.getTitle());

            mDisc.setText(activityDetails.getDescription());
        }
        AndroidNetworking.get(URLManger.getInstance().getGetActivityTypes())//"http://backpackapis.wasltec.org/Api/Activity/GetActivityTypes"
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "getGetActivityTypes: " + response);


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        RadioButton radioButton = new RadioButton(getActivity());
                        radioButton.setId(View.generateViewId());
                        radioButton.setText(jsonObject.getString("name"));
                        radioButton.setTag(jsonObject.getInt("id"));

                        if (mode==2)
                        if (activityDetails.getCategory().getName().equals(jsonObject.getString("name")))
                            radioButton.setChecked(true);


                        mRadio.addView(radioButton);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
            }

            @Override
            public void onError(ANError anError) {
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                Log.d(TAG, "getGetActivityTypes error: " + anError.getMessage());
            }
        });
        final int[] start = {8};
            Add_new_activity.loader.start();
        AndroidNetworking.get(URLManger.getInstance().getGetOptions())//"http://backpackapis.wasltec.org/Api/Activity/GetOptions"
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "getGetOptions: " + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.option_layout, null);
                        CheckBox checkBox = view.findViewById(R.id.checkbox);
                        EditText mAgeFrom = view.findViewById(R.id.age_from);
                        EditText mAgeTo = view.findViewById(R.id.age_to);

                        RelativeLayout mHasAge = view.findViewById(R.id.has_age);
                        if (jsonObject.getBoolean("haveAge"))
                        {
                            
                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked){
                                        mHasAge.setVisibility(View.VISIBLE);
                                        mAgeFrom.setEnabled(true);
                                        mAgeTo.setEnabled(true);
                                    }
                                    else{
                                        mHasAge.setVisibility(View.INVISIBLE);
                                        mAgeFrom.setText("");
                                        mAgeTo.setText("");
                                        mAgeFrom.setEnabled(false);
                                        mAgeTo.setEnabled(false);
                                    }
                                }
                            });

                        }
                        checkBox.setText(jsonObject.getString("name"));
                        checkBox.setTag(jsonObject.getInt("id"));
                        if (mode==2)
                        {
                            for (int j = 0; j <  activityDetails.getActivityOption().size(); j++) {
                                if (activityDetails.getActivityOption().get(j).getId() ==jsonObject.getInt("id")||
                                        activityDetails.getActivityOption().get(j).getName() ==jsonObject.getString("name")
                                        )
                                {
                                    checkBox.setChecked(true);
                                    mAgeFrom.setText(""+ activityDetails.getActivityOption().get(j).getFromAge());
                                    mAgeTo.setText(""+ activityDetails.getActivityOption().get(j).getToAge());
                                }
                            }
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        mContainer.addView(view, start[0], params);
                        start[0]++;
                        checkBoxes.add(view);
                        if (Add_new_activity.loader.isStart())
                            Add_new_activity.loader.stop();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        if (Add_new_activity.loader.isStart())
                            Add_new_activity.loader.stop();

                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                Log.d(TAG, "getGetOptions: " + anError.getMessage());

            }
        });

        return view;
    }

    private void initView(@NonNull final View view) {
        mRadio = (RadioGroup) view.findViewById(R.id.radio);
        mDisc = (EditText) view.findViewById(R.id.disc);
        mContainer = (LinearLayout) view.findViewById(R.id.container);
    }


    public void valid() {


        if (activity_title.getText().toString().length() < 1) {
            activity_title.setError(getActivity().getResources().getString(R.string.this_field_is_required));
            activity_title.requestFocus();
            if (Add_new_activity.loader.isStart())
                Add_new_activity.loader.stop();

        } else if (mDisc.getText().toString().length() < 1) {
            mDisc.setError(getActivity().getResources().getString(R.string.this_field_is_required));
            mDisc.requestFocus();

            if (Add_new_activity.loader.isStart())
                Add_new_activity.loader.stop();

        } else {

                Add_new_activity.loader.start();

            current_Activity.setTitle(activity_title.getText().toString());
            String description = mDisc.getText().toString();
            JSONObject jsonObject = new JSONObject();


            try {
                jsonObject.put("title", activity_title.getText().toString());
                jsonObject.put("description", description);
                int id = mRadio.getCheckedRadioButtonId();
                RadioButton radioButton = mRadio.findViewById(id);
                jsonObject.put("type_id", (int) radioButton.getTag());
                JSONArray jsonElements = new JSONArray();

                for (View view : checkBoxes) {
                    CheckBox checkBox = view.findViewById(R.id.checkbox);
                    EditText mAgeFrom = view.findViewById(R.id.age_from);
                    EditText mAgeTo = view.findViewById(R.id.age_to);
                    RelativeLayout mHasAge = view.findViewById(R.id.has_age);
                    String fromAge, toAge;
                    if (checkBox.isChecked()) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("option_id", (int) checkBox.getTag());

                        if (mHasAge.getVisibility() == View.VISIBLE) {
                            if (mAgeFrom.getText().toString().length()>0){
                                fromAge = mAgeFrom.getText().toString();
                            jsonObject1.put("fromAge",fromAge);
                            }
                            else {
                                mAgeFrom.setError(getString(R.string.this_field_is_required));
                                mAgeFrom.requestFocus();
                                if (Add_new_activity.loader.isStart())
                                    Add_new_activity.loader.stop();
                                return;
                            }
                            if (mAgeTo.getText().toString().length()>0){
                                toAge = mAgeTo.getText().toString();
                                jsonObject1.put("toAge",toAge);

                            }

                            else {
                                mAgeTo.setError(getString(R.string.this_field_is_required));
                                mAgeTo.requestFocus();
                                if (Add_new_activity.loader.isStart())
                                    Add_new_activity.loader.stop();
                                return;
                            }
                            if (!fromAge.equals("")&&!toAge.equals(""))
                                if (Integer.parseInt(fromAge)>=Integer.parseInt(toAge)){
                                    Toast.makeText(getActivity(), R.string.age_verfiy_error,Toast.LENGTH_SHORT).show();
                                    if (Add_new_activity.loader.isStart())
                                        Add_new_activity.loader.stop();

                                    return;
                                }


                        }

                        jsonElements.put(jsonObject1);
                    }
                }
                jsonObject.put("Activity_Options", jsonElements);
            } catch (JSONException e) {
                e.printStackTrace();

            }

            AndroidNetworking.post(URLManger.getInstance().setStepOne(ActivityID, mode))
                    .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Add_new_activity.ActivityID = response.getInt("activityId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (mode == 2) {
                                Add_new_activity.step_number = 1;
                                mode = 1;
                                getActivity().finish();
                            } else {
                                Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step2));
                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step2).commit();
                                Add_new_activity.step_number = 2;
                            }
                            if (Add_new_activity.loader.isStart())
                                Add_new_activity.loader.stop();

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
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.titleandtypeanddescription));
    }
}