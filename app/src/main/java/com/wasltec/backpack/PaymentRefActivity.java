package com.wasltec.backpack;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PaymentRefActivity extends AppCompatActivity {

    private TextView mBalance;
    private ImageButton mBalanceSwitch;
    private RecyclerView mPaymentMethodList;
    RelativeLayout Pay_MasterCard;
    RelativeLayout Pay_Visa;
    LinearLayout Pay_Cash;
    ImageView CasiChecked;
    ImageView VisaChecked;
    ImageView MasterCardChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_ref);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Pay_MasterCard=findViewById(R.id.Pay_MasterCard);
        Pay_Visa=findViewById(R.id.Pay_Visa);
        Pay_Cash=findViewById(R.id.Pay_Cash);
        CasiChecked = findViewById(R.id.CashisCheck);
        VisaChecked = findViewById(R.id.VisaisCheck);
        MasterCardChecked = findViewById(R.id.MasterisCheck);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow =  ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(PaymentRefActivity.this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        PaymentRefActivity.this.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        mBalance = findViewById(R.id.balance);
        mBalanceSwitch = findViewById(R.id.balance_switch);

        OnClick();
//        mPaymentMethodList = findViewById(R.id.payment_method_list);

        mBalance.setText("150 SAR");

    }

    private void OnClick() {
        mBalanceSwitch.setOnClickListener(v -> {

            v.setSelected(!v.isSelected());
        });


        Pay_MasterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Pay_MasterCard.setBackgroundDrawable(ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.border_3) );
                    Pay_Visa.setBackgroundDrawable(null);
                    Pay_Cash.setBackgroundDrawable(null);

                } else {
                    Pay_MasterCard.setBackground(ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.border_3));
                    Pay_Visa.setBackground(null);
                    Pay_Cash.setBackground(null);
                }

                MasterCardChecked.setVisibility(View.VISIBLE);
                CasiChecked.setVisibility(View.INVISIBLE);
                VisaChecked.setVisibility(View.INVISIBLE);


            }
        });

        Pay_Visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Pay_Visa.setBackgroundDrawable(ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.border_3) );
                    Pay_MasterCard.setBackgroundDrawable(null);
                    Pay_Cash.setBackgroundDrawable(null);
                } else {
                    Pay_Visa.setBackground(ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.border_3));
                    Pay_MasterCard.setBackground(null);
                    Pay_Cash.setBackground(null);
                }

                MasterCardChecked.setVisibility(View.INVISIBLE);
                CasiChecked.setVisibility(View.INVISIBLE);
                VisaChecked.setVisibility(View.VISIBLE);
            }
        });

        Pay_Cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Pay_Cash.setBackgroundDrawable(ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.border_3) );
                    Pay_Visa.setBackgroundDrawable(null);
                    Pay_MasterCard.setBackgroundDrawable(null);
                } else {
                    Pay_Cash.setBackground(ContextCompat.getDrawable(PaymentRefActivity.this, R.drawable.border_3));
                    Pay_Visa.setBackground(null);
                    Pay_MasterCard.setBackground(null);
                }
                MasterCardChecked.setVisibility(View.INVISIBLE);
                CasiChecked.setVisibility(View.VISIBLE);
                VisaChecked.setVisibility(View.INVISIBLE);
            }
        });
    }

}
