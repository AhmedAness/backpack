package com.wasltec.provider.Adopters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wasltec.provider.R;


import java.util.List;

public class AddonesAdapter extends RecyclerView.Adapter<AddonesAdapter.Holder> {

    List<Addone> categories;


    public AddonesAdapter(List<Addone> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_activity_add_ons_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        holder.mName.setText(categories.get(position).getName());
//        Glide.with(holder.itemView.getContext()).load(categories.get(position).getIcon()).into(holder.mIcon);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);


        }

    }
}
