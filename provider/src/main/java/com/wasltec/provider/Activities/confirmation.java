package com.wasltec.provider.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wasltec.provider.R;

import me.philio.pinentry.PinEntryView;


public class confirmation extends AppCompatActivity {
    private PinEntryView pinEntryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


          pinEntryView = findViewById(R.id.pin_entry_border);


        findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View v) {
                                                                       startActivity(new Intent(confirmation.this,Home.class));
                                                                   }
                                                               }
        );


    }
}
