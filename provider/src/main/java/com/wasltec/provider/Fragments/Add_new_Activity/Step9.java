package com.wasltec.provider.Fragments.Add_new_Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.JsonObject;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.DiscountAdapter;
import com.wasltec.provider.Adopters.PriceAdapter;
import com.wasltec.provider.Adopters.Price_per_individual_Adopter;
import com.wasltec.provider.R;

import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Price;
import com.wasltec.provider.model.Price_per_individual;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Step9 extends Fragment {

    private View view;
    RecyclerView price_per_individual_Recycler;
    List<Price_per_individual> price_per_individuals = new ArrayList<>();
    PriceAdapter adapter;
    private RadioButton mNotApplyBtn;
    private RadioButton mApplyBtn;
    private RecyclerView mDiscountList;
    private RadioGroup mRadiogroup;
    List<String> strings;
    PriceAdapter adapter2;
    List<Price_per_individual> cats;
    EditText group_price ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.pricingandpayments));
        setHasOptionsMenu(true);
        strings = new ArrayList<>();

        view = inflater.inflate(R.layout.add_activity_step9, container, false);
        price_per_individual_Recycler = view.findViewById(R.id.price_per_individual_Recycler);
        mNotApplyBtn = view.findViewById(R.id.not_apply_btn);
        mApplyBtn = view.findViewById(R.id.apply_btn);
        mDiscountList = view.findViewById(R.id.discount_list);
        mRadiogroup = view.findViewById(R.id.radiogroup);
        group_price = view.findViewById(R.id.group_price);

        Add_new_activity.dialog.hide();
        price_per_individual_Recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDiscountList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        AndroidNetworking.get(URLManger.getInstance().getIndividualCategory(""+ActivityID))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build().getAsObjectList(Price_per_individual.class, new ParsedRequestListener<List<Price_per_individual>>() {
            @Override
            public void onResponse(List<Price_per_individual> response) {
                cats= response;
                for (Price_per_individual price_per_individual : response) {
                    strings.add(price_per_individual.getName());
                }
                Add_new_activity.dialog.hide();
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                Add_new_activity.dialog.hide();

            }
        });


        LinearLayout Add_addons = view.findViewById(R.id.add_price_ber_individual);

        Add_addons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        mDiscountList.setVisibility(View.GONE);
        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.apply_btn) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton.isChecked()) {
                        mDiscountList.setVisibility(View.VISIBLE);
                    } else
                        mDiscountList.setVisibility(View.GONE);

                } else if (checkedId == R.id.not_apply_btn) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton.isChecked()) {
                        mDiscountList.setVisibility(View.GONE);
                    } else
                        mDiscountList.setVisibility(View.VISIBLE);

                }
            }


        });

        return view;
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_add_price_per_individual);

        final Spinner category_name = dialog.findViewById(R.id.category_name);

        if (strings.size()>0) {
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, strings);
            category_name.setAdapter(arrayAdapter);

            final EditText ticket_price = dialog.findViewById(R.id.ticket_price);

            TextView Add_addons_btn = dialog.findViewById(R.id.Add_addons_btn);
            Add_addons_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ticket_price.getText().toString().length() < 1)
                        ticket_price.setError(getActivity().getResources().getString(R.string.this_field_is_required));

                    else {

//                        try {


                            Price_per_individual cat = get_cat_data(category_name.getSelectedItem().toString());

                                price_per_individuals.add(new Price_per_individual(
                                        category_name.getSelectedItem().toString(),
                                        Integer.parseInt(ticket_price.getText().toString()),
                                        cat.getId(), cat.getPrice_after_discount(), cat.getCapacity()));
                                adapter = new PriceAdapter(price_per_individuals);
                                adapter2 = new PriceAdapter(price_per_individuals,true);
                                price_per_individual_Recycler.setAdapter(adapter);
                                mDiscountList.setAdapter(adapter2);
                                strings.remove(category_name.getSelectedItemPosition());
                                arrayAdapter.notifyDataSetChanged();
                                dialog.dismiss();

//                            jsonObject.put("name", category_name.getSelectedItem().toString());
//                            jsonObject.put("price", ticket_price.getText().toString());
//                            jsonObject.put("capacity", "0");
//                            jsonObject.put("price_after_discount", ticket_price.getText().toString());
//                            jsonObject.put("activityid", ActivityID);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        AndroidNetworking.post(URLManger.getInstance().getCreate_ActivityPricing(ActivityID,mode))
//                                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
//                                .addJSONObjectBody(jsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    int id = response.getInt("avaliability_id");
//                                    price_per_individuals.add(new Price_per_individual(
//                                            category_name.getSelectedItem().toString(),
//                                            Integer.parseInt(ticket_price.getText().toString()),
//                                            id, 0, 0));
//                                    adapter = new PriceAdapter(price_per_individuals);
//                                    adapter2 = new DiscountAdapter(price_per_individuals);
//                                    price_per_individual_Recycler.setAdapter(adapter);
//                                    mDiscountList.setAdapter(adapter2);
//                                    strings.remove(category_name.getSelectedItemPosition());
//                                    arrayAdapter.notifyDataSetChanged();
//                                    dialog.dismiss();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
//                            }
//                        });

//                price_per_individuals.add(new Price_per_individual(category_name.getText().toString(),
//                        Double.parseDouble(ticket_price.getText().toString())));
//                    Price_per_individual_Adopter price_per_individual_adopter = new Price_per_individual_Adopter(getActivity(), price_per_individuals);
//                    price_per_individual_Recycler.setAdapter(price_per_individual_adopter);

                    }
                }
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }else {
            Toast.makeText(getActivity(), "There's No Names", Toast.LENGTH_SHORT).show();
        }
    }

    private Price_per_individual get_cat_data(String s) {
        for(Price_per_individual price_per_individual: cats)
            if (price_per_individual.getName().equals(s))
                return price_per_individual;
        return null;
    }

    public void valid() {







        JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("group_price", group_price.getText().toString());
                    jsonObject.put("price_discount",0);
                    jsonObject.put("apply_discount", mApplyBtn.isChecked());

                    JSONArray jsonArray = new JSONArray();

                    for (Price_per_individual cat :adapter.getPrices()){
                        jsonObject.put("id",cat.getId());
//                        jsonObject.put("name", prices.get(position).getName());
                        jsonObject.put("price", cat.getPrice());
                        jsonObject.put("price_after_discount", cat.getPrice_after_discount());
//                        jsonObject.put("capacity", prices.get(position).getCapacity());

                    }


                    jsonObject.put("individualCategories", jsonArray);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URLManger.getInstance().getCreate_ActivityPricing(ActivityID,mode))
                        .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                        .addJSONObjectBody(jsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mode == 2) {
                            Add_new_activity.step_number = 1;
                            mode = 1;
                            getActivity().finish();
                        }
                        else {
                            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step5));
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step10).commit();
                            Add_new_activity.step_number = 10;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    @Override
    public void onResume() {
        super.onResume();
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.pricingandpayments));

    }

}