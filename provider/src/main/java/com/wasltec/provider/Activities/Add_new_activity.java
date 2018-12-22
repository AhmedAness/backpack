package com.wasltec.provider.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wasltec.provider.Fragments.Add_new_Activity.Step1;
import com.wasltec.provider.Fragments.Add_new_Activity.Step10;
import com.wasltec.provider.Fragments.Add_new_Activity.Step2;
import com.wasltec.provider.Fragments.Add_new_Activity.Step3;
import com.wasltec.provider.Fragments.Add_new_Activity.Step4;
import com.wasltec.provider.Fragments.Add_new_Activity.Step5;
import com.wasltec.provider.Fragments.Add_new_Activity.Step6;
import com.wasltec.provider.Fragments.Add_new_Activity.Step7;
import com.wasltec.provider.Fragments.Add_new_Activity.Step8;
import com.wasltec.provider.Fragments.Add_new_Activity.Step9;
import com.wasltec.provider.Fragments.Add_new_Activity.StepStatus;
import com.wasltec.provider.Fragments.Home.Activities_Fragment;
import com.wasltec.provider.R;
import com.wasltec.provider.model.ActivityDetailsReturnObj;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class Add_new_activity extends AppCompatActivity {
    static public Toolbar add_new_toolbar;
    public static int step_number = 1;
    public static TextView next_step;
    public static ImageView steper;
    public static ACProgressPie dialog;
    public static Context activity;
    boolean next = false;

    public static Step1 step1;
    public static Step2 step2;
    public static Step3 step3;
    public static Step4 step4;
    public static Step5 step5;
    public static Step6 step6;
    public static Step7 step7;
    public static Step8 step8;
    public static Step9 step9;
    public static Step10 step10;
    public static StepStatus stepStatus;
    public static int ActivityID;
    public static int mode = 1;
    boolean inComplete;
    public static ActivityDetailsReturnObj activityDetails;

    static Context context  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_activity_layout);

        context =Add_new_activity.this;
        activity = this.getApplicationContext();
        dialog = new ACProgressPie.Builder(this)
                .ringColor(Color.WHITE)
                .pieColor(Color.MAGENTA)
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();
        add_new_toolbar = findViewById(R.id.toolbar);
        next_step = findViewById(R.id.next_step);
        steper = findViewById(R.id.steper);
        setSupportActionBar(add_new_toolbar);
        add_new_toolbar.setTitle("home");

        step_number = 1;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        step1 = new Step1();
        step2 = new Step2();
        step3 = new Step3();
        step4 = new Step4();
        step5 = new Step5();
        step6 = new Step6();
        step7 = new Step7();
        step8 = new Step8();
        step9 = new Step9();
        step10 = new Step10();
        stepStatus = new StepStatus();
        if (getIntent().hasExtra("step")) {
            step_number = getIntent().getIntExtra("step", 2);
            Log.v("Add_NEW", step_number + " ");
            ActivityID = getIntent().getIntExtra("Activity_id", 1);
            if (getIntent().hasExtra("type")) {
                steper.setVisibility(View.VISIBLE);
                show_fn2();

            } else {

                mode = 2;

                steper.setVisibility(View.GONE);


                activityDetails = (new Gson()).fromJson(getIntent().getStringExtra("activityDetails"),ActivityDetailsReturnObj.class);
                Add_new_activity.next_step.setText("Save");



                show_fn();
            }


        }else if(mode==1)
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, step1).addToBackStack(null).commit();

        next_step.setOnClickListener(v -> next_fn());

    }


    public void next_fn() {
        dialog.show();

        if (inComplete) {
            inComplete = false;
            show_fn();
        } else
            switch (step_number) {
                case 1:
                    step1.valid();
                    break;
                case 2:
                    step2.valid();

                    break;
                case 3:
                    step3.valid();
                    break;
                case 4:
                    step4.valid();
                    break;
                case 5:
                    step5.valid();
                    break;
                case 6:
                    step6.valid();
                    break;
                case 7:
                    step7.valid();
                    break;
                case 8:
                    step8.valid();
                    break;
                case 9:
                    step9.valid();
                    break;
                case -1:
                    stepStatus.valid();
                    break;
                case 0:
                    stepStatus.valid();
                    break;

                case 10:
                    step10.valid();
                    break;

            }


    }

    public void show_fn() {
        switch (step_number) {
            case 1:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step1));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step1, "52").addToBackStack(null).commit();

                break;
            case 2:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step2));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step2, "88").addToBackStack(null).commit();

                break;
            case 3:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step3));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step3, "1").addToBackStack(null).commit();

                break;
            case 4:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step4));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step4, "8").addToBackStack(null).commit();
                break;
            case 5:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step5));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step5, "4").addToBackStack(null).commit();
                break;
            case 6:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step1));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step6, "5").addToBackStack(null).commit();
                break;
            case 7:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step2));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step7, "8").addToBackStack(null).commit();
                break;
            case 8:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step3));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step8, "5").addToBackStack(null).commit();
                break;
            case 9:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step4));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step9, "5").addToBackStack(null).commit();
                break;
            case -1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.stepStatus, "d").addToBackStack(null).commit();
                break;
            case 10:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step5));
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step10, "d").addToBackStack(null).commit();
                break;

        }


    }


    public void show_fn2() {
        Bundle bundle = new Bundle();
        bundle.putInt("step", getIntent().getIntExtra("step", 1));
        stepStatus.setArguments(bundle);
        inComplete = true;
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.stepStatus, null).addToBackStack(null).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_tab_menu, menu);
        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);
        if(mode==2 ){
            menu.findItem(R.id.ContinueLater).setVisible(false);
        }else{
            menu.findItem(R.id.ContinueLater).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.settings:
                break;
            case R.id.add_item:
                break;
            case R.id.ContinueLater:
                Add_new_activity.this.finish();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
       if (mode==1){
           if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
               getSupportFragmentManager().popBackStack();
               step_number--;
//            super.onBackPressed();
           }else
               Add_new_activity.this.finish();
       }
       else
       {
           Add_new_activity.this.finish();
       }
    }

    public static void showDialog(int text, int title) {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Toolbar bar = view.findViewById(R.id.toolbar);
        bar.setTitle(title);
        TextView textView = view.findViewById(R.id.msg);
        textView.setText(text);
        view.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            dialog.dismiss();

        });
        dialog.show();
    }
}
