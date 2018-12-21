package com.wasltec.provider.Adopters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopeer.shadow.ShadowView;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.Activity_details;
import com.wasltec.provider.Activities.Reviews;
import com.wasltec.provider.R;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.AllActivityReturnOpj;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class offline_items_adopter extends  RecyclerView.Adapter<offline_items_adopter.MyViewHolder> {
    private Context context;
    public static List<AllActivityReturnOpj> data= new ArrayList<AllActivityReturnOpj>();


    public offline_items_adopter(Context context,List<AllActivityReturnOpj> allActivityReturnOpjs) {
        this.context = context;
        data =allActivityReturnOpjs;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.preview.setVisibility(View.GONE);
        holder.reload_layout.setVisibility(View.VISIBLE);
        holder.vote_number.setText(""+data.get(position).getRate());
        holder.title.setText(data.get(position).getTitle());
        holder.desc.setText(data.get(position).getActivity_Location());
        holder.reviews_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Reviews.class);


                Gson gson = new Gson();
                String json = gson.toJson(data.get(position));
                intent.putExtra("Activity_id",data.get(position).getId());
                intent.putExtra("Activity_object",json);

                context.startActivity(intent);
            }
        });
        holder.row_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(data.get(position));

                Intent intent=new Intent(context, Activity_details.class);
                intent.putExtra("Activity_id",data.get(position).getId());
                intent.putExtra("Activity_object",json);
                context.startActivity(intent);
            }
        });

//        imageView.setImageDrawable(context.getResources().getDrawable( R.drawable.booked_via_backpack));
        try {
            Picasso.get()
                    .load(data.get(position).getActivityPhotos().get(0).getUrl()).placeholder(R.drawable.activities_footer_icon)
                    .error(R.drawable.booked_via_backpack)
                    .into(holder.imageView);
        }catch (Exception e )
        {
            Log.d(TAG, "getView: " +e.getMessage());
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout reviews_LL;
        TextView title,desc,vote_number;
        ShadowView preview;
        LinearLayout reload_layout;
        View row_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            row_item=itemView;
            vote_number = itemView.findViewById(R.id.vote_num);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            reviews_LL = itemView.findViewById(R.id.reviews_LL);
            preview =itemView.findViewById(R.id.preview);
            reload_layout=itemView.findViewById(R.id.reload_layout);
        }
    }
}

