package com.wasltec.backpack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.models.User;
import com.wasltec.backpack.utils.SharedPreferencesManager;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONException;
import org.json.JSONObject;

import me.philio.pinentry.PinEntryView;

public class VerifyActivity extends AppCompatActivity {

    private TextView mWorngPhoneNumberTxt;
    private PinEntryView mPinEntryBorder;
//    private Button mVerify;
    private TextView mPhoneNumber2;
    private TextView mResendCount;
    private static final String TAG = VerifyActivity.class.getName();

    String code;
    int type;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mWorngPhoneNumberTxt = findViewById(R.id.worng_phone_number_txt);
        mPinEntryBorder = findViewById(R.id.pin_entry_border);
//        mVerify = findViewById(R.id.verify);
        code=getIntent().getStringExtra("code");
        number=getIntent().getStringExtra("number");
        type=getIntent().getIntExtra("type",1);
        mPinEntryBorder.setOnPinEnteredListener(new PinEntryView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                if (mPinEntryBorder.getText().toString().equals(code)) {


                    JSONObject jsonObject= new JSONObject();
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
                                        Toast.makeText(VerifyActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    User user=new Gson().fromJson(response.getJSONObject("user").toString(),User.class);
                                    Session session= Session.getInstance(VerifyActivity.this);
                                    session.setUser(user);
                                    session.setToken(response.getString("token"));

                                    Intent i = new Intent(VerifyActivity.this, MainActivity.class);

                                    i.putExtra("type", 0);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(VerifyActivity.this,anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                        }
                    });



//                    Intent i = new Intent(VerifyActivity.this, MainActivity.class);
//                    Session session= Session.getInstance(VerifyActivity.this);
//                    i.putExtra("type", type);
//                    startActivity(i);

//                    if (type==1) {
//                        User user = new User();
//                        Log.i(TAG, "onCreate: " + number.replace(" ", "").replace("+", ""));
//                        user.setMobile(number.replace(" ", "").replace("+", ""));
//                        session.setUser(user);
//                    }


                } else {
                    Snackbar.make(findViewById(R.id.container), "Please Enter Valid Number", Snackbar.LENGTH_SHORT).show();
                    mPinEntryBorder.clearText();
                    ((Vibrator) VerifyActivity.this.getSystemService(VIBRATOR_SERVICE)).vibrate(100);
                }
            }
        });
//        mPinEntryBorder.setOnFocusChangeListener((view, b) -> {
//
//        });



//        mVerify.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(mPinEntryBorder.getText().toString())) {
//                Snackbar.make(findViewById(R.id.container), R.string.error_textview, Snackbar.LENGTH_SHORT).show();
//
//            } else {
//                if (mPinEntryBorder.getText().toString().equals(code)) {
//                    Intent i = new Intent(this, MainActivity.class);
//                        Session session= Session.getInstance(VerifyActivity.this);
//                    i.putExtra("type", type);
//                    startActivity(i);
//
//                    if (type==1) {
//                        User user = new User();
//                        Log.i(TAG, "onCreate: " + number.replace(" ", "").replace("+", ""));
//                        user.setMobile(number.replace(" ", "").replace("+", ""));
//                        session.setUser(user);
//                    }
//
//
//                } else {
//                    Snackbar.make(findViewById(R.id.container), "Please Enter Valid Number", Snackbar.LENGTH_SHORT).show();
//                    mPinEntryBorder.clearText();
//                    ((Vibrator) this.getSystemService(VIBRATOR_SERVICE)).vibrate(100);
//                }
//            }
//        });
        mPhoneNumber2 = findViewById(R.id.phone_number2);
        mPhoneNumber2.setText(getIntent().getStringExtra("number"));
        mResendCount = findViewById(R.id.resend_count);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                mResendCount.setText(String.format(new String(getString(R.string.resend_code_holder)), millisUntilFinished / 1000));
            }

            public void onFinish() {
                Toast.makeText(VerifyActivity.this, getIntent().getStringExtra("code"), Toast.LENGTH_SHORT).show();
            }

        }.start();
    }
}
