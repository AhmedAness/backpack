package com.wasltec.provider.Adopters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wasltec.provider.R;
import com.wasltec.provider.model.ActivityPhoto;

import java.util.ArrayList;

public class ActivityPhoto_adobter2 extends RecyclerView.Adapter<ActivityPhoto_adobter2.MyViewHolder> {

    ArrayList<ActivityPhoto> activityPhotos;
    Context activity;
    boolean from_info=false;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public ImageButton close ;



        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            close = view.findViewById(R.id.close);
            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams((int)view.getContext().getResources().getDimension(R.dimen._120sdp),
                    (int)view.getContext().getResources().getDimension(R.dimen._120sdp));
            params.setMargins(5,5,5,5);
            view.setLayoutParams(params);
        }
    }


    public ActivityPhoto_adobter2(Context activity, ArrayList<ActivityPhoto> photos) {
        this.activityPhotos = photos;
        this.activity= activity;
    }
    public ActivityPhoto_adobter2(Context activity, ArrayList<ActivityPhoto> photos, boolean from_info ) {
        this.activityPhotos = photos;
        this.activity= activity;
        this.from_info=from_info;
    }

    @Override
    public ActivityPhoto_adobter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_2_photo, parent, false);

        return new ActivityPhoto_adobter2.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityPhoto_adobter2.MyViewHolder holder, int position) {
        ActivityPhoto item = activityPhotos.get(position);



//        holder.image.setImageDrawable(activity.getResources().getDrawable( R.drawable.booked_via_backpack));
        Picasso.get().load(item.getUrl()).placeholder(R.drawable.activities_footer_icon).error(R.drawable.booked_via_backpack).into(holder.image);

        if (from_info)
            holder.close.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
        return activityPhotos.size();
    }
}