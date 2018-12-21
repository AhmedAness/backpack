package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Activities.Customer_details;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityPhoto;
import com.wasltec.provider.model.Post_item;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class ActivityPhoto_adobter extends RecyclerView.Adapter<ActivityPhoto_adobter.MyViewHolder> {

    ArrayList<ActivityPhoto> activityPhotos;
    Context activity;
    int fromAddactivity=0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        CheckBox iscover_img;


        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            iscover_img = view.findViewById(R.id.iscover_img);
            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams((int)view.getContext().getResources().getDimension(R.dimen._120sdp),
                    (int)view.getContext().getResources().getDimension(R.dimen._120sdp));
            params.setMargins(5,5,5,5);
            view.setLayoutParams(params);
            ImageButton mClose = view.findViewById(R.id.close);
            mClose.setOnClickListener(v->{
                if(fromAddactivity==1)
                {
                    activityPhotos.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }else if(fromAddactivity==2)
                {
                    int postion=getAdapterPosition();
                    AndroidNetworking.delete(URLManger.getInstance()
                            .getDeleteActivityPhoto(Add_new_activity.ActivityID+"",
                                    activityPhotos.get(getAdapterPosition()).getId()+""))
                            .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(activity).getToken())
                            .build()
                            .getAsOkHttpResponse(new OkHttpResponseListener() {
                                @Override
                                public void onResponse(Response response) {

                                    activityPhotos.remove(postion);
                                    notifyDataSetChanged();

                                    Toast.makeText(activity,"response code : "+response.code(),Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onError(ANError anError) {

                                    Toast.makeText(activity,""+anError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });




        }
    }


    public ActivityPhoto_adobter(Context activity, ArrayList<ActivityPhoto> photos,int fromAddactivity) {
        this.activityPhotos = photos;
        this.activity= activity;
        this.fromAddactivity = fromAddactivity;
    }

    @Override
    public ActivityPhoto_adobter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_2_photo, parent, false);

        return new ActivityPhoto_adobter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityPhoto_adobter.MyViewHolder holder,final int position) {
       final ActivityPhoto item = activityPhotos.get(position);



//        holder.image.setImageDrawable(activity.getResources().getDrawable( R.drawable.booked_via_backpack));
        Picasso.get().load(item.getUrl()).error(R.drawable.booked_via_backpack).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.image.setImageBitmap(bitmap);
                item.setBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


        if (fromAddactivity==1||fromAddactivity==2){
            holder.iscover_img.setVisibility(View.VISIBLE);
            holder.iscover_img.setChecked(activityPhotos.get(position).getCoverPhoto());
        }

        holder.iscover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < activityPhotos.size(); i++) {
                    activityPhotos.get(i).setCoverPhoto(false);
                }
                activityPhotos.get(position).setCoverPhoto(true);
                notifyDataSetChanged();
            }
        }) ;
//
//        holder.iscover_img.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            for (int i = 0; i < activityPhotos.size(); i++) {
//                activityPhotos.get(i).setCoverPhoto(false);
//            }
//            activityPhotos.get(position).setCoverPhoto(true);
//            notifyDataSetChanged();
//
//        });


    }

    public ArrayList<ActivityPhoto> getActivityPhotos() {
        return activityPhotos;
    }

    @Override
    public int getItemCount() {
        return activityPhotos.size();
    }
}