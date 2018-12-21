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

public class incomplate_list_adopter extends   RecyclerView.Adapter<incomplate_list_adopter.MyViewHolder> {
    private Context context;
    public static List<AllActivityReturnOpj> data= new ArrayList<AllActivityReturnOpj>();


    public incomplate_list_adopter(Context context,List<AllActivityReturnOpj> data) {
        this.context = context;
        this.data= data;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item_incomplete, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{
            Picasso.get( ).load(data.get(position).getUrl()).into(holder.imageView);
        }catch (Exception e){
            Log.d("logs", "getView: "+e.getMessage());
        }

        holder.title.setText(data.get(position).getTitle());

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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View row_item;
        ImageView imageView;
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            row_item= itemView;
            imageView = itemView.findViewById(R.id.image_in);
            title = itemView.findViewById(R.id.title);
        }
    }
}

