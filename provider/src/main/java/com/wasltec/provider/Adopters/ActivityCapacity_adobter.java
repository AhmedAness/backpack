package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.ActivityAddOn;
import com.wasltec.provider.model.IndividualCategory;

import java.util.ArrayList;

public class ActivityCapacity_adobter extends RecyclerView.Adapter<ActivityCapacity_adobter.MyViewHolder> {

    int type=0;

    public ActivityCapacity_adobter(Activity activity, ArrayList<IndividualCategory> individualCategories,int type) {
        this.individualCategories= individualCategories;
        this.activity= activity;
        this.type= type;
    }


    ArrayList<IndividualCategory> individualCategories;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name , desc;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.capacity_num);


        }
    }




    @Override
    public ActivityCapacity_adobter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_capscity_item, parent, false);

        return new ActivityCapacity_adobter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityCapacity_adobter.MyViewHolder holder, int position) {
        IndividualCategory item = individualCategories.get(position);


        holder.name .setText(item.getName() +": ");

        if (type==1)
        {
            if (item.getCapacity()==0)
                holder.desc .setText(R.string.na);
            else
                holder.desc .setText(""+item.getCapacity());
        }else{
            holder.desc .setText(activity.getString(R.string.currency)+" "+ item.getPrice());

        }







    }

    @Override
    public int getItemCount() {
        return individualCategories.size();
    }
}



