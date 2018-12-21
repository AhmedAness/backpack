package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.ActivityAddOn;

import java.util.ArrayList;

public class ActivityAddon_adobter extends RecyclerView.Adapter<ActivityAddon_adobter.MyViewHolder> {
    public ActivityAddon_adobter(Activity activity, ArrayList<ActivityAddOn> activityAddOns) {
        this.activityAddOns= activityAddOns;
        this.activity= activity;
    }

    ArrayList<ActivityAddOn> activityAddOns;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name , desc;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.desc);


        }
    }




    @Override
    public ActivityAddon_adobter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_addon_item, parent, false);

        return new ActivityAddon_adobter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityAddon_adobter.MyViewHolder holder, int position) {
        ActivityAddOn item = activityAddOns.get(position);


        holder.name.setText(item.getName());
        if (item.getPrice()==0)
        {
            holder.desc .setText(R.string.included);
        }
        else{
            holder.desc.setText(String.format("%s %d", holder.desc.getResources().getString(R.string.currency),item.getPrice()));
        }





    }

    @Override
    public int getItemCount() {
        return activityAddOns.size();
    }
}