package com.wasltec.backpack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DecelrationActivity extends AppCompatActivity {

    LinearLayout diseasesLinearLayout;
    ArrayList<String> diseases;
    TextView itemText;
    Button save;

    boolean hospitalization, dietaryRestrictions, medication;
    RadioGroup hospitalizationRG, dietaryRestrictionsRG, medicationRG;
    EditText hospitalizationDetails, dietaryRestrictionsDetails, medicationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decelration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setViews();
        setBooleans();

        diseases = new ArrayList<>();
        AndroidNetworking.get(URLManager.getInstance().getLookUp("Diseases"))
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Json", response.toString());
                        Log.d("DisplayLanguage", Locale.getDefault().getDisplayLanguage());
                        String lang = "en";
                        if (Locale.getDefault().getDisplayLanguage().toLowerCase().equals("arabic"))
                            lang = "ar";

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String disease = response.getJSONObject(i).getString("diseases_name_" + lang);
                                diseases.add(disease);
                                addViewToLinearLayout(i, response.getJSONObject(i).getString("diseases_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        diseases.add("NONE of the above");


                        getUserDisease();
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setViews() {
        diseasesLinearLayout = findViewById(R.id.diseases_linear_layout);
        hospitalizationDetails = findViewById(R.id.hospitalization_details);
        dietaryRestrictionsDetails = findViewById(R.id.dietary_restrictions_details);
        medicationDetails = findViewById(R.id.medication_details);
        hospitalizationRG = findViewById(R.id.hospitalization_rg);
        dietaryRestrictionsRG = findViewById(R.id.dietary_restrictions_rg);
        medicationRG = findViewById(R.id.medication);
        save = findViewById(R.id.save_btn);
    }

    private void getUserDisease() {
        AndroidNetworking.get(URLManager.getInstance().getUserDisease("1"))
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Json", response.toString());
                            JSONArray jsonArray = response.getJSONArray("user_DiseasesList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                int id = jsonArray.getJSONObject(i).getInt("diseases_id");
                                setCheckBox(id - 1, true);
                            }

                            JSONObject object = response.getJSONObject("followUpHealth");
                            // TODO: 10/26/2018 handel followUpHealthId
                            hospitalization = object.getBoolean("hospitalization");
                            dietaryRestrictions = object.getBoolean("dietaryRestrictions");
                            medication = object.getBoolean("medication");


                            if (hospitalization)
                                hospitalizationRG.check(hospitalizationRG.getChildAt(0).getId());
                            else
                                hospitalizationRG.check(hospitalizationRG.getChildAt(1).getId());
                            hospitalizationDetails.setText(object.getString("hospitalizationDetails"));

                            if (dietaryRestrictions)
                                dietaryRestrictionsRG.check(dietaryRestrictionsRG.getChildAt(0).getId());
                            else
                                dietaryRestrictionsRG.check(dietaryRestrictionsRG.getChildAt(1).getId());
                            dietaryRestrictionsDetails.setText(object.getString("dietaryRestrictionsDetails"));

                            if (medication)
                                medicationRG.check(medicationRG.getChildAt(0).getId());
                            else
                                medicationRG.check(medicationRG.getChildAt(1).getId());
                            medicationDetails.setText(object.getString("medicationDetails"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });
    }

    private void setCheckBox(int index, boolean checked) {
        View view = diseasesLinearLayout.getChildAt(index);
        ImageView itemCheck = view.findViewById(R.id.item_image);
        if (checked) {
            itemCheck.setImageResource(R.drawable.square_checked_orange);
            itemCheck.setTag("true");
        } else {
            itemCheck.setImageResource(R.drawable.square_unchecked_orange);
            itemCheck.setTag("false");
        }
    }

    private void setBooleans() {
        hospitalizationRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hospitalization = group.getChildAt(0).getId() == checkedId;
            }
        });

        dietaryRestrictionsRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                dietaryRestrictions = group.getChildAt(0).getId() == checkedId;
            }
        });

        medicationRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                medication = group.getChildAt(0).getId() == checkedId;
            }
        });
    }

    private void addViewToLinearLayout(final int index, String tag) {
        View view = LayoutInflater.from(this).inflate(R.layout.decelration_list_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        itemText = view.findViewById(R.id.item_text);
        itemText.setText(diseases.get(index));
        itemText.setTag(tag);

        final ImageView itemCheck = view.findViewById(R.id.item_image);
        itemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCheck.getTag().toString().equals("false")) {
                    itemCheck.setImageResource(R.drawable.square_checked_orange);
                    itemCheck.setTag("true");
                } else {
                    itemCheck.setImageResource(R.drawable.square_unchecked_orange);
                    itemCheck.setTag("false");
                }
            }
        });


        diseasesLinearLayout.addView(view);
    }

    public void save(View v) {

        JSONObject object = new JSONObject();
        try {
            JSONArray user_Diseases = new JSONArray();
            for (int i = 0; i < diseasesLinearLayout.getChildCount(); i++) {
                View view = diseasesLinearLayout.getChildAt(i);
                TextView itemText = view.findViewById(R.id.item_text);
                ImageView itemImage = view.findViewById(R.id.item_image);
                JSONObject userDisease = new JSONObject();

                if (itemImage.getTag().toString().equals("true")) {
                    userDisease.put("diseases_id", itemText.getTag().toString());
                    // TODO: 10/26/2018 handel others
                    user_Diseases.put(userDisease);
                }
            }
            object.put("user_Diseases", user_Diseases);

            JSONObject followUpHealth = new JSONObject();
            followUpHealth.put("hospitalization", hospitalization);
            followUpHealth.put("dietaryRestrictions", dietaryRestrictions);
            followUpHealth.put("medication", medication);
            followUpHealth.put("hospitalizationDetails", hospitalizationDetails.getText().toString());
            followUpHealth.put("dietaryRestrictionsDetails", dietaryRestrictionsDetails.getText().toString());
            followUpHealth.put("medicationDetails", medicationDetails.getText().toString());
            object.put("FollowUpHealth", followUpHealth);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("Json", object.toString());
        AndroidNetworking.post(URLManager.getInstance().getUserDisease("1"))
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .addJSONObjectBody(object)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DecelrationActivity.this, "save successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(DecelrationActivity.this, "failed to save", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
