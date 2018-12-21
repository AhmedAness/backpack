package com.wasltec.backpack.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.backpack.R;
import com.wasltec.backpack.models.Items;

import java.util.List;

public class Price_Adapter extends RecyclerView.Adapter<Price_Adapter.MyViewHolder> {

    List<Items> rules_Items;
    Activity activity;


    public Price_Adapter(Activity activity, List<Items> rules_Items) {
        this.rules_Items = rules_Items;
        this.activity = activity;
    }

    @Override
    public Price_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_item, parent, false);

        return new Price_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Price_Adapter.MyViewHolder holder, int position) {

        holder.mPrice.setText(String.format(holder.mText.getContext()
                .getString(R.string.total_price_payment_holder), rules_Items.get(position).getPrice()));

        holder.mText.setText(rules_Items.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return rules_Items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private TextView mPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text);
            mPrice = itemView.findViewById(R.id.price);

        }
    }
}
