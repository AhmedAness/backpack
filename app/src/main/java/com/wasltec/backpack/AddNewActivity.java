package com.wasltec.backpack;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddNewActivity extends AppCompatActivity {

    private TextView mTextView5;
    private Spinner mSpinner;
    private TextInputLayout mDate;
    private TextInputLayout mOtherLicense;
    private TextInputLayout mLicenseNo;
    private Button mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView5 = findViewById(R.id.textView5);
        mSpinner = findViewById(R.id.spinner);
        mDate = findViewById(R.id.date);
        mOtherLicense = findViewById(R.id.other_license);
        mLicenseNo = findViewById(R.id.license_no);
        mConfirmBtn = findViewById(R.id.Confirm_btn);
    }

}
