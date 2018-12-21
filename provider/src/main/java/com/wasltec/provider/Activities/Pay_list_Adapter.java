package com.wasltec.provider.Activities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wasltec.provider.Adopters.Customes_Adapter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.model.Booking_item;
import com.wasltec.provider.model.Key_value;

import java.util.ArrayList;
import java.util.List;

class Pay_list_Adapter extends RecyclerView.Adapter<Pay_list_Adapter.ViewHolder> {

    Context context;
    private List<Key_value> data ;
    private LayoutInflater mInflater;
    private Pay_list_Adapter.ItemClickListener mClickListener;
    private int current_id;

    // data is passed into the constructor
    public Pay_list_Adapter(Context context,int current_id, List<Key_value> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.current_id=current_id;

        this.data=data;

    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public Pay_list_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.key_value_item_layout, parent, false);
        return new Pay_list_Adapter.ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull Pay_list_Adapter.ViewHolder holder, int position) {



//        Picasso.with(context).load(data.get(position).getUrl()).into( holder.icon);
        holder.key.setText(data.get(position).getKey());
        holder.value.setText(""+data.get(position).getVAlue());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView key,value;

        ViewHolder(View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.key);
            value = itemView.findViewById(R.id.value);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            //TODO
            //go to display  activity
        }
    }

    // convenience method for getting data at click position
    public Key_value getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(Pay_list_Adapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
