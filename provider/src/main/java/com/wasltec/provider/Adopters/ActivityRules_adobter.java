package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.ActivityRule;

import java.util.ArrayList;

public class ActivityRules_adobter extends  RecyclerView.Adapter<ActivityRules_adobter.MyViewHolder> {

    Activity activity;
    ArrayList<ActivityRule> activityRules;

    public ActivityRules_adobter(Activity activity, ArrayList<ActivityRule> activityRules) {

        this.activityRules=activityRules;
        this.activity= activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_addon_item, parent, false);

        return new ActivityRules_adobter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ActivityRule item = activityRules.get(position);
        holder.name .setText(item.getDescription());
       holder.desc_lout.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return activityRules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        LinearLayout desc_lout;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            desc_lout = view.findViewById(R.id.desc_lout);

        }
    }
}
