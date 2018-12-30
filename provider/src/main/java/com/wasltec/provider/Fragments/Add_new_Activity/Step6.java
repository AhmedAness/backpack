package com.wasltec.provider.Fragments.Add_new_Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.activityDetails;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;
import static com.wasltec.provider.Utils.App_Context.TAG;

public class Step6 extends Fragment {

    private View view;
    private Spinner spinner, notice_in_advance_spinner;
    private static final String[] paths = {" Days", " Weeks"," Month"};
    private static final String[] notice_in_advance_spinner_paths = {" Minutes", " Hours", " Days"};
    private CheckBox mBoxIndividual;
    private CheckBox mBoxPrivate;
    private EditText mInAdvanceTxtNotice,booking_window_ET;
    public static boolean isGroup=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.booking_prefrences));
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step6, container, false);
        if (Add_new_activity.loader.isStart())
            Add_new_activity.loader.stop();


        spinner = view.findViewById(R.id.booking_window_spinner);
        notice_in_advance_spinner = view.findViewById(R.id.notice_in_advance_spinner);

        notice_in_advance_spinner_fn();
        booking_window_spinner_fn();

        initView(view);
        if (mode==2)
        {

            mInAdvanceTxtNotice.setText(""+activityDetails.getNoticeInAdvance());
            booking_window_ET.setText(""+activityDetails.getBookingWindow());

            mBoxPrivate.setChecked(activityDetails.getBookingAvailableForGroups());
            mBoxIndividual.setChecked(activityDetails.getBookingAvailableForIndividuals());



        }
        return view;
    }

    private void initView(@NonNull final View itemView) {
        mBoxIndividual = (CheckBox) itemView.findViewById(R.id.individual_box);
        mBoxPrivate = (CheckBox) itemView.findViewById(R.id.private_box);
        mInAdvanceTxtNotice = (EditText) itemView.findViewById(R.id.notice_in_advance_txt);
        booking_window_ET = (EditText) itemView.findViewById(R.id.booking_window_ET);
        mBoxPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGroup=isChecked;
            }
        });
    }

    private void booking_window_spinner_fn() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, paths);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void notice_in_advance_spinner_fn() {
        ArrayAdapter<String> notice_in_advance_spinner_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, notice_in_advance_spinner_paths);


        notice_in_advance_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notice_in_advance_spinner.setAdapter(notice_in_advance_spinner_adapter);
        notice_in_advance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        if(!mBoxIndividual.isChecked()&&!mBoxPrivate.isChecked())
        {
            Toast.makeText(getActivity(),"please select Booking available for.",Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            int nias = Integer.parseInt(mInAdvanceTxtNotice.getText().toString());
            int x = 7;
            if (notice_in_advance_spinner.getSelectedItemPosition() == 1)
                nias = nias * 60;
            else if (notice_in_advance_spinner.getSelectedItemPosition() == 2)
                nias = nias * 60 * 24;

            jsonObject.put("notice_in_advance", nias);


            int tmp = Integer.parseInt(booking_window_ET.getText().toString());

            if (spinner.getSelectedItemPosition() == 1)
                tmp = tmp *7;
            else if (spinner.getSelectedItemPosition() == 2)
                tmp = tmp * 30;
            jsonObject.put("booking_window", tmp);
            jsonObject.put("bookingAvailableForIndividuals", mBoxIndividual.isChecked());
            jsonObject.put("bookingAvailableForGroups", mBoxPrivate.isChecked());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URLManger.getInstance().setStepSix(ActivityID,mode))
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
                            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step2));
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step7).commit();
                            Add_new_activity.step_number = 7;
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
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.booking_prefrences));

    }
}