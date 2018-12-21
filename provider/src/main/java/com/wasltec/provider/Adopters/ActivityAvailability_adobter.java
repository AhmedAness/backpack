package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.Avaliability;

import java.util.ArrayList;

public class ActivityAvailability_adobter extends RecyclerView.Adapter<ActivityAvailability_adobter.MyViewHolder> {

    ArrayList<Avaliability> activityAddOns;
    Activity activity;

    public ActivityAvailability_adobter(Activity activity, ArrayList<Avaliability> avaliabilities) {
        this.activity= activity;
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name , desc;


    public MyViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.name);
        desc = view.findViewById(R.id.desc);


    }
}




    @Override
    public ActivityAvailability_adobter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_addon_item, parent, false);

        return new ActivityAvailability_adobter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityAvailability_adobter.MyViewHolder holder, int position) {
        Avaliability item = activityAddOns.get(position);

        holder.name .setText(item.toString());






    }

    @Override
    public int getItemCount() {
        return activityAddOns.size();
    }
}