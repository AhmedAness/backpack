package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.Price_per_individual;

import java.util.List;

public class Price_per_individual_Adopter  extends RecyclerView.Adapter<Price_per_individual_Adopter.MyViewHolder> {

    List<Price_per_individual> price_per_individuals;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category_name,ticket_price;
        ImageView image;





        public MyViewHolder(View view) {
            super(view);
            category_name = view.findViewById(R.id.category_name);
            ticket_price = view.findViewById(R.id.ticket_price);
            image = view.findViewById(R.id.delete_image);


        }
    }


    public Price_per_individual_Adopter(Activity activity,List<Price_per_individual> price_per_individuals) {
        this.price_per_individuals = price_per_individuals;
        this.activity= activity;
    }

    @Override
    public Price_per_individual_Adopter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_per_individual_item, parent, false);

        return new Price_per_individual_Adopter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Price_per_individual_Adopter.MyViewHolder holder, final int position) {
        Price_per_individual item = price_per_individuals.get(position);
        holder.category_name.setText(item.getName());
        holder.ticket_price.setText(item.getPrice()+" "+activity.getResources().getString(R.string.sr));

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    price_per_individuals.remove(position);
                    notifyDataSetChanged();
                }
            });





    }

    @Override
    public int getItemCount() {
        return price_per_individuals.size();
    }

}
