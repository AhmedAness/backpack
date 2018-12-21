package com.wasltec.backpack.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.CustomerActivitiesModel;

import java.util.List;

public class OurActivityAdapter extends RecyclerView.Adapter<com.wasltec.backpack.Adapters.OurActivityAdapter.Holder> {
    List<CustomerActivitiesModel> items;

    public OurActivityAdapter(List<CustomerActivitiesModel> items) {
        this.items = items;
    }

    public void setItems(List<CustomerActivitiesModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activites_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mItemName.setText(items.get(position).getTitle());
        holder.mItemPrice.setText("" + items.get(position).getPrice());
//        holder.mItemRating.setRating(items.get(position).getTitle());
        holder.mItemName.setText(items.get(position).getTitle());
        Glide.with(holder.mActivityImg.getContext()).load(items.get(position).getActivityPhotoUrl()).into(holder.mActivityImg);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {


        private ImageView mActivityImg;
        private TextView mItemName;
        private TextView mItemPrice;
        private TextView mItemProvider;


        public Holder(View itemView) {
            super(itemView);
            mActivityImg = itemView.findViewById(R.id.activity_img);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemPrice = itemView.findViewById(R.id.item_price);
            mItemProvider = itemView.findViewById(R.id.item_provider);

        }
    }
}
