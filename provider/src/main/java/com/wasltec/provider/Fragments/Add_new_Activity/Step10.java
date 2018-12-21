package com.wasltec.provider.Fragments.Add_new_Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Activities.Home;
import com.wasltec.provider.Adopters.Organizers_Adopter;
import com.wasltec.provider.R;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Organizer;
import com.wasltec.provider.model.OrganizerType;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Step10 extends Fragment {

    private View view;
    RecyclerView organizer_Recycler;
    List<Organizer> organizers = new ArrayList<>();
    List<OrganizerType> organizersType = new ArrayList<>();
    Organizers_Adopter o;
    private int count = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.orgnizers));
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step10, container, false);
        Add_new_activity.dialog.hide();
        List<String> strings = new ArrayList<>();
        strings.add("999");
        Add_new_activity.next_step.setText(R.string.save_btn);

        strings.add("20");


        organizer_Recycler = view.findViewById(R.id.orgnizers_Recycler);
        o = new Organizers_Adopter(getActivity(), organizers);
        organizer_Recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AndroidNetworking.get(URLManger.getInstance().getGetOrganizerTypes())//http://backpackapis.wasltec.org/api/User/GetOrganizerTypes
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build().getAsObjectList(OrganizerType.class, new ParsedRequestListener<List<OrganizerType>>() {
            @Override
            public void onResponse(List<OrganizerType> response) {
                organizersType = response;

            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayout Add_addons = view.findViewById(R.id.add_an_orgizer);

        Add_addons.setOnClickListener(v -> openDialog());


        return view;
    }


    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_add_organizer);

        final EditText organizer_name = dialog.findViewById(R.id.organizer_name);
        final EditText organizer_mail = dialog.findViewById(R.id.organizer_mail);
        final EditText organizer_phone = dialog.findViewById(R.id.organizer_phone);

        final TextView title = dialog.findViewById(R.id.title);
        title.setText(getString(R.string.invite_an_ornizer ));
        Spinner mNums = dialog.findViewById(R.id.nums);
        List<String> strings = new ArrayList<>();
        strings.add("+999");
        strings.add("+020");

        mNums.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings));
        mNums.setSelection(0);
        mNums.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view!=null)
                ((TextView) view).setTextColor(ContextCompat.getColor(getActivity(), R.color.gray)); //Change selected text color

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView Add_addons_btn = dialog.findViewById(R.id.Add_addons_btn);
        LinearLayout linearLayout = dialog.findViewById(R.id.container);

        for (int i = 5; i < count; i++) {
            linearLayout.removeViewAt(i);

        }
        for (OrganizerType organizerType : organizersType) {

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setBackgroundColor(Color.WHITE);
            checkBox.setText(organizerType.getType());
            checkBox.setId(View.generateViewId());
            checkBox.setTag(organizerType.getId());
            linearLayout.addView(checkBox, count);
            count++;
        }
        Add_addons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (organizer_name.getText().toString().length() < 1)
                    organizer_name.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else if (organizer_mail.getText().toString().length() < 1)
                    organizer_mail.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else if (organizer_phone.getText().toString().length() < 1)
                    organizer_phone.setError(getActivity().getResources().getString(R.string.this_field_is_required));
                else {
                    String type = "";
                    String id = "";
                    for (int i = 5; i < count; i++) {
                        CheckBox checkBox = (CheckBox) linearLayout.getChildAt(i);
                        if (checkBox.isChecked()) {
                            type = checkBox.getText().toString();
                            id = (String) checkBox.getTag();
                        }
                    }

                    organizers.add(new Organizer(Integer.parseInt(id), organizer_name.getText().toString(), type, ((String) mNums.getSelectedItem()) + organizer_phone.getText().toString(), organizer_mail.getText().toString()));
                    Organizers_Adopter organizers_adopter = new Organizers_Adopter(getActivity(), organizers);
                    organizer_Recycler.setAdapter(organizers_adopter);
                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void valid() {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonElement = new JSONArray();
        for (Organizer organizer : organizers) {
            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2.put("name", organizer.getName());
                jsonObject2.put("mobile", organizer.getMobile());
                jsonObject2.put("mail", organizer.getMail());
                jsonObject2.put("Organizer_Typeid", organizer.getId());
                jsonElement.put(jsonObject2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject.put("activity_Organizers", jsonElement);
            Log.v("Json", jsonObject.toString(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URLManger.getInstance().getAddOrginizer(ActivityID, mode))
                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.msg_dialog, null, false);
                        dialog.setContentView(view);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Toolbar toolbar =view.findViewById(R.id.toolbar);

                        toolbar.setTitle(getString(R.string.youractivitynowisonline));
                        EditText textView = view.findViewById(R.id.msg);
                        textView.setVisibility(View.GONE);
                        TextView textView2 = view.findViewById(R.id.makeitofflinebtn);
                        textView2.setVisibility(View.VISIBLE);
                        ((TextView) view.findViewById(R.id.ok_btn)).setText(getString(R.string.ok));
                        view.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startActivity(new Intent(getActivity(), Home.class));
                                Add_new_activity.step_number = 0;
                                Add_new_activity.next_step.setText(R.string.next_txt);
                                dialog.dismiss();
                            }
                        });

                        view.findViewById(R.id.makeitofflinebtn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                AndroidNetworking.get(URLManger.getInstance().getChangeStatus(""+ActivityID))
                                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                startActivity(new Intent(getActivity(), Home.class));
                                                Add_new_activity.step_number = 0;
                                                Add_new_activity.next_step.setText(R.string.next_txt);
                                            }

                                            @Override
                                            public void onError(ANError anError) {

                                                Toast.makeText(getActivity(),anError.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        });


                        dialog.show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Add_new_activity.dialog.hide();
                        Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Add_new_activity.next_step.setText(R.string.next_txt);

                    }
                });

    }
}