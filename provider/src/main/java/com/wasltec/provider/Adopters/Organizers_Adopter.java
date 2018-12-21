package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.model.Organizer;
import com.wasltec.provider.model.Price_per_individual;

import java.util.List;

public class Organizers_Adopter  extends RecyclerView.Adapter<Organizers_Adopter.MyViewHolder> {

    List<Organizer> organizers;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView organizer_name,organizer_type;
        ImageView image;





        public MyViewHolder(View view) {
            super(view);
            organizer_name = view.findViewById(R.id.category_name);
            organizer_type = view.findViewById(R.id.ticket_price);
            image = view.findViewById(R.id.delete_image);


        }
    }


    public Organizers_Adopter(Activity activity,List<Organizer> organizers) {
        this.organizers = organizers;
        this.activity= activity;
    }

    @Override
    public Organizers_Adopter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_per_individual_item, parent, false);

        return new Organizers_Adopter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Organizers_Adopter.MyViewHolder holder, final int position) {
        Organizer item = organizers.get(position);
        holder.organizer_name.setText(item.getName());
        if(item.getType().equals("Admin"))//for admin
             holder.organizer_type.setText(activity.getResources().getString(R.string.admin));
        else
            holder.organizer_type.setText(activity.getResources().getString(R.string.helper));


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organizers.remove(position);
                notifyDataSetChanged();
            }
        });





    }

    @Override
    public int getItemCount() {
        return organizers.size();
    }

}
