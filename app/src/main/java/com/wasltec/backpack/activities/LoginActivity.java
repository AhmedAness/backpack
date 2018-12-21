package com.wasltec.backpack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.models.Reg_Response;
import com.wasltec.backpack.models.User;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Spinner mPhoneCode;
    private EditText mPhoneNumber;
    private TextView mHelp;
    private TextView mSkip;
    private static final String TAG = LoginActivity.class.getName();
    private Button mLogin;
//    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPhoneCode = findViewById(R.id.phone_code);
        mPhoneNumber = findViewById(R.id.phone_number);
        mHelp = findViewById(R.id.help);
        mSkip = findViewById(R.id.skip);
        mLogin = findViewById(R.id.login);
//        mRegister = findViewById(R.id.register);

        ArrayList<String> strings = new ArrayList<String>();

        AndroidNetworking.get(URLManager.getInstance().getCountryCode())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                strings.add(jsonObject.getString("dial_code"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        mPhoneCode.setAdapter(new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_spinner_item, strings));
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        strings.add("+020");
                        strings.add("+9661");
                        mPhoneCode.setAdapter(new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_spinner_item, strings));
                    }
                });

        mPhoneCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray)); //Change selected text color

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mPhoneNumber.getText().toString())) {
                Snackbar.make(findViewById(R.id.container), R.string.error_textview, Snackbar.LENGTH_SHORT).show();

            } else {
//                Phonenumber.PhoneNumber swissMobileNumber =
//                        new Phonenumber.PhoneNumber()
//                                .setCountryCode(Integer.parseInt(((String) mPhoneCode.getSelectedItem())
//                                        .replace('+',' ')))
//                                .setNationalNumber(Long.parseLong(mPhoneNumber.getText().toString().trim()));
//                swissMobileNumber.setCountryCodeSource(Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN);
                if (/*PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber)*/true) {
                    JSONObject jsonObject = new JSONObject();

                    try {
//                        Log.i(TAG, "onCreate: "+swissMobileNumber.getCountryCode() + swissMobileNumber.getNationalNumber());
                        Log.i(TAG, "Mobile  : "+ mPhoneNumber.getText().toString());
//                        Log.i(TAG, "LoginType : 0 ");
//                        jsonObject.put("Mobile", ""+swissMobileNumber.getCountryCode() + swissMobileNumber.getNationalNumber());
                        jsonObject.put("Mobile", mPhoneNumber.getText().toString());
//                        jsonObject.put("LoginType", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AndroidNetworking.post(URLManager.getInstance().getMobileRegister())
                            .addJSONObjectBody(jsonObject)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, "onResponse: ");


                            Gson gson= new Gson();

                            Type listType = new TypeToken<Reg_Response>() {
                            }.getType();
                            Reg_Response reg_response;

                            try {
                                reg_response = gson.fromJson(response.toString(), listType);
                            } catch (Exception e) {

                                reg_response= new Reg_Response();
                                e.printStackTrace();
                            }


                            if (reg_response.getStatus()==0||reg_response.isVerified())
                            {
                                try {
                                    jsonObject.put("LoginType","0" );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                AndroidNetworking.post(URLManager.getInstance().getLogin())
                                        .addJSONObjectBody(jsonObject)
                                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.has("code")) {
                                                if (response.getString("code").equals("-1"))
                                                    Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                            else {

                                                User user=new Gson().fromJson(response.getJSONObject("user").toString(),User.class);
                                                Session session= Session.getInstance(LoginActivity.this);
                                                session.setUser(user);
                                                session.setToken(response.getString("token"));

                                                Intent i = new Intent(LoginActivity.this, MainActivity.class);

                                                i.putExtra("type", 0);
                                                startActivity(i);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        Toast.makeText(LoginActivity.this,anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            } else
                            {

                                Intent i = new Intent(LoginActivity.this, VerifyActivity.class);
                                i.putExtra("number", mPhoneNumber.getText().toString());
                                i.putExtra("code",reg_response.getVerificationcode());
                                i.putExtra("isVerified",reg_response.isVerified());

                                Log.i(TAG, "onResponse: "+reg_response.getVerificationcode());
                                Toast.makeText(LoginActivity.this,reg_response.getVerificationcode() , Toast.LENGTH_SHORT).show();

                                i.putExtra("type",1);
                                startActivity(i);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.i(TAG, "onError: " + anError.getErrorDetail());
//
                                    Snackbar.make(findViewById(R.id.container), "There is no Account with this Number", Snackbar.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Snackbar.make(findViewById(R.id.container), "Please Enter Valid Number", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);


            }
        });
    }
}
