package com.wasltec.backpack.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.CustomerFavoriteActivitiesModel;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder> {
    List<CustomerFavoriteActivitiesModel> items;

    public FavouriteAdapter(List<CustomerFavoriteActivitiesModel> items) {
        this.items = items;
    }

    public void setItems(List<CustomerFavoriteActivitiesModel> items) {
        this.items = items;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item, parent, false));

    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        if (position>0) {
        holder.mItemName.setText(items.get(position).getTitle());
        holder.mItemPrice.setText(holder.mActivityImg.getContext().getString(R.string.price_holder)+"  "+ items.get(position).getPrice());
//        holder.mItemRating.setRating(items.get(position).getTitle());
        holder.mItemName.setText(items.get(position).getTitle());
        holder.mItemDescription.setText(items.get(position).getDescription());
        Glide.with(holder.mActivityImg.getContext()).load(items.get(position).getActivityPhotos()).into(holder.mActivityImg);
//        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {


        private ImageView mActivityImg;
        private TextView mItemName;
        private TextView mItemPrice;
        private TextView mItemDescription;


        public Holder(View itemView) {
            super(itemView);
            mActivityImg = itemView.findViewById(R.id.activity_img);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemPrice = itemView.findViewById(R.id.item_price);
            mItemDescription = itemView.findViewById(R.id.item_description);
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(v.getContext(), DetailsActivity.class);
                i.putExtra("object", items.get(getAdapterPosition()).getId());
                v.getContext().startActivity(i);

            });

        }
    }
}
