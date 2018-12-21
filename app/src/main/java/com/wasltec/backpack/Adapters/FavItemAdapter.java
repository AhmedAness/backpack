package com.wasltec.backpack.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.backpack.R;
import com.wasltec.backpack.models.FavListItem;

import java.util.List;

public class FavItemAdapter extends RecyclerView.Adapter<FavItemAdapter.MyViewHolder> {

    List<FavListItem> rules_Items;


    public FavItemAdapter(List<FavListItem> rules_Items) {
        this.rules_Items = rules_Items;
    }

    @Override
    public FavItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_list_item, parent, false);

        return new FavItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavItemAdapter.MyViewHolder holder, int position) {

        holder.mCount.setText(String.valueOf(rules_Items.get(position).getCount()));

        holder.mText.setText(rules_Items.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return rules_Items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private TextView mCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text);
            mCount = itemView.findViewById(R.id.count);

        }
    }
}
