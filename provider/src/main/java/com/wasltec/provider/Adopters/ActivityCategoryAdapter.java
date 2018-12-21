package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.AllActivityReturnOpj;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ActivityCategoryAdapter extends RecyclerView.Adapter<ActivityCategoryAdapter.ViewHolder> {

    Context context;

    public List<AllActivityReturnOpj> getData() {
        return data;
    }

    private List<AllActivityReturnOpj> data ;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    // data is passed into the constructor
    public ActivityCategoryAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context=context;




    }
    public void setData(List<AllActivityReturnOpj> data_opj){
        this.data= data_opj;
    }


    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.image.setImageDrawable(context.getResources().getDrawable( R.drawable.booked_via_backpack));


       try {

           Picasso.get().load(data.get(position).getActivityPhotos().get(0).getUrl())
                   .placeholder(R.drawable.activities_footer_icon).error(R.drawable.booked_via_backpack)
                   .into(holder.image);
       }catch (Exception e){
           Log.d(TAG, "onBindViewHolder: "+e.getMessage());
       }

        holder.icon.setImageDrawable(context.getResources().getDrawable( R.drawable.group3));
//        Picasso.with(context).load(data.get(position).getUrl()).into( holder.icon);
        holder.title.setText(data.get(position).getTitle());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image,icon;
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
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
    public AllActivityReturnOpj getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
