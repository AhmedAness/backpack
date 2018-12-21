package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wasltec.provider.Activities.Calender;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.OverviewReturnOpj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Activity_list_overview extends RecyclerView.Adapter<Activity_list_overview.ViewHolder> implements
        overview_list_item_list.ItemClickListener {

    Context context;
    Activity activity;
    private Map<String,List<OverviewReturnOpj>> dataSet;
    private ArrayList<OverviewReturnOpj> alldata;

    ArrayList<String> listdate ;
    ArrayList<String> listdate2 ;
    public static String clickedposition_date;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public Activity_list_overview(Context context,Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.activity=activity;




    }

    public void setData(ArrayList<OverviewReturnOpj> data){
        dataSet= new HashMap<>();
        listdate = new ArrayList<>();
        listdate2=new ArrayList<>();
        alldata=data;
        for (int i = 0; i < data.size(); i++) {
            if (dataSet.containsKey(data.get(i).getActivity_Start()))
                dataSet.get(data.get(i).getActivity_Start()).add(data.get(i));
            else {
                listdate.add(data.get(i).getActivity_Start());
                listdate2.add(data.get(i).getActivity_Start2());
                List<OverviewReturnOpj> tmp = new ArrayList<>();
                tmp .add(data.get(i));
                dataSet.put(data.get(i).getActivity_Start(),tmp);
            }



        }


    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.overview_main_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView2.setLayoutManager(horizontalLayoutManagaer1);
        overview_list_item_list adapter = new overview_list_item_list(context,activity,listdate.get(position));
        adapter.setDataSet(dataSet.get(listdate.get(position)));
        adapter.setDates(listdate,alldata,listdate2);
        adapter.setClickListener(this);
        holder.recyclerView2.setAdapter(adapter);
        holder.title.setText(listdate.get(position));
        holder.recyclerView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "w", Toast.LENGTH_SHORT).show();
            }
        });
        holder.title.setClickable(true);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, holder.title.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
        clickedposition_date=listdate.get(position);

//
//        Intent intent=new Intent(context, Calender.class);
//        intent.putExtra("Activity_id",dataSet.get(position).get());
//        context.startActivity(intent);


    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerView recyclerView2;
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);

            recyclerView2 = itemView.findViewById(R.id.list3);
            title = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Toast.makeText(context, "What", Toast.LENGTH_SHORT).show();
            //TODO
            //go to display  activity
        }
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
