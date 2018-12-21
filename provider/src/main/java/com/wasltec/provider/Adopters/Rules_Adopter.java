package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.ActivityRule;

import java.util.List;

public class Rules_Adopter extends RecyclerView.Adapter<Rules_Adopter.MyViewHolder> {

    List<ActivityRule> rules_Items;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,close;
        ImageView image;





        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.Name);
            close = view.findViewById(R.id.close);
            image = view.findViewById(R.id.rule_image);


        }
    }


    public Rules_Adopter(Activity activity,List<ActivityRule> rules_Items) {
        this.rules_Items = rules_Items;
        this.activity= activity;
    }

    public List<ActivityRule> getRules_Items() {
        return rules_Items;
    }

    @Override
    public Rules_Adopter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rule_item, parent, false);

        return new Rules_Adopter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Rules_Adopter.MyViewHolder holder, int position) {
        ActivityRule item = rules_Items.get(position);
        holder.name.setText(item.getDescription());
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rules_Items.remove(position);
                notifyDataSetChanged();
            }
        });

        if (item.isStatus())
            holder.image.setVisibility(View.VISIBLE);
        else
            holder.image.setVisibility(View.GONE);






    }

    @Override
    public int getItemCount() {
        return rules_Items.size();
    }

}
