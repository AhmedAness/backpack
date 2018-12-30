package com.wasltec.provider.Fragments.Add_new_Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.AddonesDialogAdapter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityAddOn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;
import static com.wasltec.provider.Activities.Add_new_activity.step4;
import static com.wasltec.provider.Activities.Add_new_activity.steper;

public class Step3 extends Fragment {

    private View view;
    private LinearLayout mContainer, addoneAdd;
    private List<CheckBox> checkBoxes;
    int[] start = {0};
    AddonesDialogAdapter dialogAdapter;
    List<ActivityAddOn> list;
    List<View> views;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.add_ons));
        setHasOptionsMenu(true);
        list = new ArrayList<>();
        views = new ArrayList<>();
        if (Add_new_activity.loader.isStart())
            Add_new_activity.loader.stop();
        view = inflater.inflate(R.layout.add_activity_step3, container, false);
        checkBoxes = new ArrayList<>();
        LinearLayout Add_addons = view.findViewById(R.id.Add_addons);

        Add_addons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        initView(view);

        return view;
    }

    private void initView(@NonNull final View itemView) {
        mContainer = itemView.findViewById(R.id.container);

        final int[] start = {0};


    }

    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_add_on, null, false);
        recyclerView = view.findViewById(R.id.add_ones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog.setContentView(view);
        dialog.setTitle(R.string.add_add_ons);
        TextView Add_addons_btn = dialog.findViewById(R.id.Add_addons_btn);
        if (list.size() == 0) {
            AndroidNetworking.get(URLManger.getInstance().getGetAllAdd_Ons())//"http://backpackapis.wasltec.org/api/add_ons/GetAllAdd_Ons"
                    .addHeaders("Content-Type","application/json")
                    .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                    .build().getAsObjectList(ActivityAddOn.class, new ParsedRequestListener<List<ActivityAddOn>>() {
                @Override
                public void onResponse(List<ActivityAddOn> response) {
                    list = response;
                    dialogAdapter = new AddonesDialogAdapter(list, false,false, getActivity());
                    recyclerView.setAdapter(dialogAdapter);
                    if (Add_new_activity.loader.isStart())
                        Add_new_activity.loader.stop();
                    dialog.show();

                }


                @Override
                public void onError(ANError anError) {

                }
            });
        } else {

            dialogAdapter = new AddonesDialogAdapter(list, false,false, getActivity());
            recyclerView.setAdapter(dialogAdapter);
            if (Add_new_activity.loader.isStart())
                Add_new_activity.loader.stop();
            dialog.show();
        }
        dialog.setOnDismissListener(dialog1 -> {
            for (View view1 : views) {
                mContainer.removeView(view1);
            }
            start[0] = 0;

            for (ActivityAddOn activityAddOn : dialogAdapter.getPrices()) {
                if (activityAddOn.isSelected()) {
                    View view1 = LayoutInflater.from(getContext()).inflate(R.layout.add_activity_add_ons_item, null, false);
                    TextView name = view1.findViewById(R.id.name);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 25);
                    view1.setLayoutParams(layoutParams);
                    ImageButton imageButton = view1.findViewById(R.id.remove);
                    imageButton.setOnClickListener(v -> {
                        mContainer.removeView(view1);
                        list.get(start[0]).setSelected(false);
                        start[0]--;

                    });
                    name.setTag(activityAddOn.getId());
                    EditText desc = view1.findViewById(R.id.description);
//                    desc.setPaintFlags(desc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                    name.setText(activityAddOn.getName());
                    mContainer.addView(view1, start[0]);
                    views.add(view1);
                    start[0]++;
                }
            }

        });

        Add_addons_btn.setOnClickListener(v -> {
            JSONObject j = new JSONObject();
            EditText editText = view.findViewById(R.id.Add_addons);

            try {

                if (editText.getText().toString().length() > 0)
                    j.put("name", editText.getText().toString());
                else {
                    if (dialogAdapter.getPrices().size() == 0) {
                        editText.setError(getString(R.string.this_field_is_required));
                        editText.requestFocus();
                    } else {
                        dialog.dismiss();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AndroidNetworking.post(URLManger.getInstance().getCreateAddone())
                    .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                    .addJSONObjectBody(j)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ActivityAddOn activityAddOn = new ActivityAddOn(Integer.parseInt(response.getString("add_Ons_id")),
                                editText.getText().toString(), ""
                        );
                        activityAddOn.setSelected(true);
                        activityAddOn.setIntial(true);

//                        dialogAdapter.addItem(activityAddOn);
//                        list.add(activityAddOn);
                        list.add(activityAddOn);

                        dialogAdapter = new AddonesDialogAdapter(list, false,false, getActivity());
                        recyclerView.setAdapter(dialogAdapter);
                        recyclerView.scrollToPosition(dialogAdapter.getItemCount()-1);
// dialogAdapter.notifyDataSetChanged();

                        editText.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onError(ANError anError) {
                    Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                }
            });


        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void valid() {


        JSONObject jsonObject = new JSONObject();
        JSONArray jsonElements = new JSONArray();
        for (int i = 0; i < start[0]; i++) {
            View view = mContainer.getChildAt(i);
            TextView name = view.findViewById(R.id.name);
            EditText desc = view.findViewById(R.id.description);

            EditText price = view.findViewById(R.id.price);
            EditText provider = view.findViewById(R.id.Provider);
            if (desc.getText().length() <= 0) {
                desc.setError(getString(R.string.error_empty_field));
                desc.requestFocus();
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                return;
            } else if (price.getText().length() <= 0) {
                price.setError(getString(R.string.error_empty_field));
                price.requestFocus();
                if (Add_new_activity.loader.isStart())
                    Add_new_activity.loader.stop();
                return;
            } else {
                try {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("add_ons_id", (int) name.getTag());
                    jsonObject1.put("price", price.getText().toString());
                    jsonObject1.put("provider_name", provider.getText().toString());
                    jsonObject1.put("addons_number", "50");
                    jsonObject1.put("note",  desc.getText().toString());
                    jsonObject1.put("description", desc.getText().toString());
                    jsonElements.put(jsonObject1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
            try {
                jsonObject.put("Activity_Add_Ons", jsonElements);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (start[0] == 0) {
                steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step4));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, step4).commit();
                Add_new_activity.step_number = 4;

            } else {
                try {
                    Log.v("JSOn", jsonObject.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URLManger.getInstance().setStepThree(ActivityID, mode))
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
                                    steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step4));
                                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, step4).commit();
                                    Add_new_activity.step_number = 4;
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
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.add_ons));

    }
}
