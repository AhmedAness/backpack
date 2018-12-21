package com.wasltec.provider.Fragments.Activity_details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.ActivityAddon_adobter;
import com.wasltec.provider.Adopters.ActivityPhoto_adobter;
import com.wasltec.provider.Adopters.ActivityPhoto_adobter2;
import com.wasltec.provider.Adopters.ActivityRules_adobter;
import com.wasltec.provider.R;

import static com.wasltec.provider.Activities.Activity_details.activityDetails;

public class Info extends Fragment {

    private View view;

    TextView ac_title,ac_category,ac_description ,ac_location,ac_meetingpoint,ac_Requirments;
    TextView  title_edit_btn,photo_edit_btn,location_edit_btn,rulesandrequirment_edit_btn,add_ones_edit_btn;
    RecyclerView ac_photos_list,activity_addons,activity_rules;
    GridLayout activity_Option;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.fragment_info, container, false);


        init();

        return view;
    }

    private void init() {
        ac_title= view.findViewById(R.id.ac_title);
        ac_category= view.findViewById(R.id.ac_category);
        ac_description= view.findViewById(R.id.ac__description);
        ac_location= view.findViewById(R.id.ac_location);
        ac_meetingpoint= view.findViewById(R.id.ac_meetingpoint);
        ac_Requirments= view.findViewById(R.id.ac_Requirments);

        title_edit_btn= view.findViewById(R.id.title_edit_btn);
        title_edit_btn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",1);
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
                i.putExtra("Activity_id",getArguments().getInt("id",0));
            startActivity(i);
        });
        photo_edit_btn= view.findViewById(R.id.photos_edit_btn);
        photo_edit_btn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",2);
                i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });
        location_edit_btn= view.findViewById(R.id.location_edit_btn);
        location_edit_btn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",4);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });

        rulesandrequirment_edit_btn= view.findViewById(R.id.rulesandrequirment_edit_btn);
        rulesandrequirment_edit_btn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",5);
            i.putExtra("Activity_id",getArguments().getInt("id"));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });
        add_ones_edit_btn=view.findViewById(R.id.add_ons_edit_btn);
        add_ones_edit_btn.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), Add_new_activity.class);
            i.putExtra("step",3);
            i.putExtra("Activity_id",getArguments().getInt("id",0));
            i.putExtra("activityDetails",(new Gson ()).toJson(activityDetails));
            startActivity(i);
        });


        ac_photos_list= view.findViewById(R.id.ac_photos_list);
        activity_Option = view.findViewById(R.id.types_container);
        activity_addons = view.findViewById(R.id.activity_addons);
        activity_rules = view.findViewById(R.id.activity_rules);

        //set data
      try {


          ac_title.setText(activityDetails.getTitle());
          ac_category.setText(activityDetails.getCategory().getName());
          ac_description.setText(activityDetails.getDescription());
          ac_location.setText(activityDetails.getActivityLocation());
          ac_meetingpoint.setText(activityDetails.getMeetingLocation());
          ac_Requirments.setText(activityDetails.getRequirements());
          set_activity_Option();
          set_ac_photos_list();
          set_activity_addons();
          set_activity_rules();
      }catch (Exception e){
          Toast.makeText(getActivity()," error",Toast.LENGTH_LONG).show();
      }








    }

    private void set_activity_rules() {
        activity_rules.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        activity_rules.setAdapter(new ActivityRules_adobter(getActivity(),activityDetails.getActivityRules()));

    }

    private void set_activity_addons() {
        activity_addons.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        activity_addons.setAdapter(new ActivityAddon_adobter(getActivity(),activityDetails.getActivityAddOns()));

    }

    private void set_ac_photos_list() {
        ac_photos_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ac_photos_list.setAdapter(new ActivityPhoto_adobter2(getActivity(),activityDetails.getActivityPhotos(),true));

    }

    private void set_activity_Option() {


        for (int i = 0; i < activityDetails.getActivityOption().size(); i++) {

            switch (activityDetails.getActivityOption().get(i).getId()){
                case 1 :
                    TextView textView = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.setGravity(Gravity.FILL);
                    textView.setText(activityDetails.getActivityOption().get(i).getName());

                    textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.male_icon);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }
                    layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                    textView.setLayoutParams(layoutParams);

                    textView.setCompoundDrawables(drawable, null, null, null);
                    textView.setId(View.generateViewId());

                    activity_Option.addView(textView);
                    break;
                    case 2 :
                    TextView textView1 = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                    GridLayout.LayoutParams layoutParams1 = new GridLayout.LayoutParams();
                    layoutParams1.setGravity(Gravity.FILL);
                    textView1.setText(activityDetails.getActivityOption().get(i).getName());

                    textView1.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
                    Drawable drawable1 = ContextCompat.getDrawable(getActivity(), R.drawable.female_icon);
                    if (drawable1 != null) {
                        drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
                    }
                    layoutParams1.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                    textView1.setLayoutParams(layoutParams1);

                    textView1.setCompoundDrawables(drawable1, null, null, null);
                    textView1.setId(View.generateViewId());

                    activity_Option.addView(textView1);
                    break;
                    default:
                        TextView textView2 = new TextView(getActivity(), null, android.R.style.TextAppearance_Large);
                        GridLayout.LayoutParams layoutParams2 = new GridLayout.LayoutParams();
                        layoutParams2.setGravity(Gravity.FILL);
                        int from = 0 , to = 0;
                        if (activityDetails.getActivityOption().get(i).getFromAge()==0&&
                                activityDetails.getActivityOption().get(i).getToAge()==0)
                            textView2.setText(activityDetails.getActivityOption().get(i).getName());
                        else
                            textView2.setText(activityDetails.getActivityOption().get(i).getName()+
                                    " ( "+activityDetails.getActivityOption().get(i).getFromAge()+" , "+
                                    activityDetails.getActivityOption().get(i).getToAge()+" ) ");

                        textView2.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
                        Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.drawable.male_icon);
                        if (drawable2 != null) {
                            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                        }
                        layoutParams2.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                        textView2.setLayoutParams(layoutParams2);

                        textView2.setCompoundDrawables(drawable2, null, null, null);
                        textView2.setId(View.generateViewId());

                        activity_Option.addView(textView2);
                        break;

            }


        }







    }


}
