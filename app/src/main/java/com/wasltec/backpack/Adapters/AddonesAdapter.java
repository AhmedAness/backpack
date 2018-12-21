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
import com.wasltec.backpack.models.ActivityDetails.ActivityAddOn;


import java.util.List;

public class AddonesAdapter extends RecyclerView.Adapter<AddonesAdapter.Holder> {

    List<ActivityAddOn> categories;


    public AddonesAdapter(List<ActivityAddOn> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_ones, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mName.setText(categories.get(position).getName());
        holder.mDesc.setText(categories.get(position).getDescription());
        String type = "";
        if (categories.get(position).getPrice()==0.0)
            type = holder.mType.getContext().getString(R.string.included_txt);
        else
            type = categories.get(position).getPrice() + holder.itemView.getContext().getString(R.string.currency);
        holder.mType.setText(type);

        Glide.with(holder.itemView.getContext()).load(categories.get(position).getIcon()).into(holder.mIcon);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView mType;
        private ImageView mIcon;
        private TextView mName;
        private TextView mDesc;

        public Holder(View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.type);
            mIcon = itemView.findViewById(R.id.icon);
            mName = itemView.findViewById(R.id.name);
            mDesc = itemView.findViewById(R.id.desc);


        }

        public TextView getmType() {
            return mType;
        }

        public ImageView getmIcon() {
            return mIcon;
        }

        public TextView getmName() {
            return mName;
        }

        public TextView getmDesc() {
            return mDesc;
        }
    }
}
