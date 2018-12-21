package com.wasltec.provider.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.provider.R;

import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Session;
import com.wasltec.provider.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Login_Register extends AppCompatActivity {

    EditText email_edit,pass_edit;
    LinearLayout loginPage;
    TextView sgin_up;
    private RelativeLayout mProgress;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);
        init ();
        findViewById(R.id.btns).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email_edit.getText().toString())||TextUtils.isEmpty(pass_edit.getText().toString())) {
                    Snackbar.make(findViewById(R.id.container), R.string.error_empty_field, Snackbar.LENGTH_SHORT).show();

                } else {

                    //TOdo login api
                    showProgress(true);

                    User user = new User(email_edit.getText().toString(),pass_edit.getText().toString());
                      gson = new Gson();
                    String json = gson.toJson(user);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    try {
//                        jsonObject.put("Email",email_edit.getText().toString());
//                        jsonObject.put("Password",pass_edit.getText().toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    AndroidNetworking.post(URLManger.getInstance().getLogin())
                            .addHeaders("Content-Type","application/json")
                            .addJSONObjectBody(jsonObject)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        com.wasltec.provider.model.User_res list = gson.fromJson(response.getJSONObject("user").toString(),
                                                new TypeToken<com.wasltec.provider.model.User_res>(){}.getType());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if (response.has("token"))

                                    {
                                        try {
                                            SharedPreferencesManager.getInstance(Login_Register.this).setToken(response.getString("token"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (response.has("user"))
                                    {



                                        try {
                                            Session.getInstance().setId(response.getJSONObject("user").getInt("id"));
                                            Session.getInstance().setEmail(response.getJSONObject("user").getString("mail"));
                                            Session.getInstance().setFirst_name(response.getJSONObject("user").getString("first_name"));
                                            Session.getInstance().setLast_name(response.getJSONObject("user").getString("last_name"));
                                            Session.getInstance().setPhone(response.getJSONObject("user").getString("mobile"));
                                            Session.getInstance().setUserName(response.getJSONObject("user").getString("userName"));
                                            Session.getInstance().setProvider(response.getJSONObject("user").getBoolean("isProvider"));
                                            Session.getInstance().setUserPhoto_Url(response.getJSONObject("user").getString("userPhoto_Url"));
                                            Session.getInstance().saveToFile(Login_Register.this);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }


                                    startActivity(new Intent(Login_Register.this, Home.class));
                                    Login_Register.this.finish();
                                }

                                @Override
                                public void onError(ANError anError) {

                                    Snackbar.make(email_edit.getRootView(), anError.getErrorBody()+" "+anError.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    showProgress(false);

                                }
                            });

                }
            }
        });
    }

    private void init() {
        email_edit= findViewById(R.id.email_edit);
        pass_edit= findViewById(R.id.pass_edit);
        loginPage = findViewById(R.id.loginPage);
        mProgress= findViewById(R.id.progress_Lout);
        sgin_up= findViewById(R.id.sgin_up);
        sgin_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Register.this , WebViewActivity.class);

                intent.putExtra("sgin_up",URLManger.getInstance().getSignup());
                startActivity(intent);
            }
        });

    }
    public void showProgress(boolean show) {
        loginPage.setVisibility(show ? View.GONE : View.VISIBLE);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
