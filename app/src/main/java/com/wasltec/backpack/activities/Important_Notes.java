package com.wasltec.backpack.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wasltec.backpack.R;

public class Important_Notes extends AppCompatActivity {

    TextView Notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important__notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Notes=findViewById(R.id.Notes_text);
        Notes.setText(getIntent().getExtras().getString("Notes"));
    }
}
