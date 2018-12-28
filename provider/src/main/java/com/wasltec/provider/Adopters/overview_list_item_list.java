package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.wasltec.provider.Activities.BookingActivity;
import com.wasltec.provider.Activities.Calender;
import com.wasltec.provider.Activities.ChatActivity;
import com.wasltec.provider.Activities.CustomisedCalender;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Activity_model;
import com.wasltec.provider.model.OverviewReturnOpj;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class overview_list_item_list extends RecyclerView.Adapter<overview_list_item_list.ViewHolder> {

    Context context;
    Activity activity;
    private List<OverviewReturnOpj> data;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<String> dates;
    private ArrayList<String> dates2;
    String selected_position_date;

    private ArrayList<OverviewReturnOpj> alldata;

    // data is passed into the constructor
    public overview_list_item_list(Context context, Activity activity,String selected_position_date) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity=activity;
        this.selected_position_date=selected_position_date;

    }

    public void setDates(ArrayList<String> dates, ArrayList<OverviewReturnOpj> data, ArrayList<String> dates2) {
        this.dates = dates;
        this.alldata = data;
        this.dates2 = dates2;

    }

    void setDataSet(List<OverviewReturnOpj> data) {

        this.data = data;

    }


    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.overview_day_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.title.setText(data.get(position).getTitle());
        holder.number_of_views.setText(context.getString(R.string.view_all) + " (" + data.get(position).getTotal_tickets() +" Tickets"+ ")");

        holder.mSencMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(activity, ChatActivity.class);

                intent.putExtra("ChatId", -1);
                intent.putExtra("fromoverview", data.get(position).getAvaid());
                intent.putExtra("chatname", data.get(position).getTitle());


                activity.startActivity(intent);


//                    Toast.makeText(activity,"clicked row id "+data.get(getAdapterPosition()).getAvaid(),Toast.LENGTH_LONG).show();
//                    Intent intent= new Intent(activity,ChatActivity.class);
//
//
//                    intent.putExtra("ChatId",data.get(getAdapterPosition()).getAvaid());
//                    activity.startActivity(intent);

//                    final Dialog dialog = new Dialog(context); // Context, this, etc.
//                    View view = LayoutInflater.from(context).inflate(R.layout.msg_dialog, null, false);
//                    dialog.setContentView(view);
//                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    EditText textView = view.findViewById(R.id.msg);
//
//                    view.findViewById(R.id.ok_btn).setOnClickListener(v2 -> {
//                    if (textView.getText().toString().length()<1){
//                        textView.setError(v.getResources().getString(R.string.error_empty_field));
//                        textView.requestFocus();
//                    }
//                    else {
//                        JSONObject jsonObject=new JSONObject();
//                        try {
//                            jsonObject.put("Message",textView.getText().toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////
//////                        AndroidNetworking.post(URLManger.getInstance().getMsg(data.get(getAdapterPosition()).getId()))
////                        AndroidNetworking.post(URLManger.getInstance().getsendMessageForAll(""+data.get(getAdapterPosition()).getAvaid()))
////                                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(v.getContext()).getToken())
////                                .addHeaders("Content-Type","application/json")
////                                .addStringBody(textView.getText().toString())
//////                                .addJSONObjectBody(jsonObject)
////                                .build()
////                                .getAsJSONObject(new JSONObjectRequestListener() {
////                                    @Override
////                                    public void onResponse(JSONObject response) {
////
////                                        dialog.dismiss();
////
////                                    }
////
////                                    @Override
////                                    public void onError(ANError anError) {
////
////                                        dialog.dismiss();
////                                        Toast.makeText(v.getContext(),anError.getErrorDetail(),Toast.LENGTH_SHORT).show();
////                                    }
////                                });
//
//
//                        ///////////////////////////////
//                        AndroidNetworking.post(URLManger.getInstance().getSend_message(0,data.get(getAdapterPosition()).getAvaid()))
//                                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(context).getToken())
//                                .addJSONObjectBody(jsonObject)
//                                .build()
//                                .getAsOkHttpResponse(new OkHttpResponseListener() {
//                                    @Override
//                                    public void onResponse(Response response) {
//                                        Toast.makeText(context,"sent ",Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void onError(ANError anError) {
//                                        Toast.makeText(context,"  "+anError.getMessage(),Toast.LENGTH_SHORT).show();
//
//                                        dialog.dismiss();
//                                    }
//                                });
//                    }
//                    });
//                    dialog.show();
            }
        });

//        holder.number_of_views.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i=new Intent(context, BookingActivity.class);
//                i.putExtra("id",String.valueOf(data.get(position).getId()));
////                i.putStringArrayListExtra("dates",dates);
////                i.putStringArrayListExtra("dates2",dates2);
////                i.putParcelableArrayListExtra("data",alldata);
////
////                i.putExtra("curractivity", Parcels.wrap(alldata.get(position) ));
//
//                v.getContext().startActivity(i);
//            }
//        });




    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, number_of_views;
        TextView mSencMessage;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.name);
            number_of_views = itemView.findViewById(R.id.number_of_views);

            mSencMessage = itemView.findViewById(R.id.sencMessage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//            //TODO
//            //go to display  activity
//
//            Intent intent = new Intent(context, CustomisedCalender.class);
//            intent.putExtra("Activity_id", "" + data.get(getAdapterPosition()).getId());
////            context.startActivity(intent);
//
//            context.startActivity(intent);


            Intent i=new Intent(context, BookingActivity.class);
            i.putExtra("id",String.valueOf(data.get(getAdapterPosition()).getId()));
            i.putExtra("position",selected_position_date);
//                i.putStringArrayListExtra("dates",dates);
//                i.putStringArrayListExtra("dates2",dates2);
//                i.putParcelableArrayListExtra("data",alldata);
//
//                i.putExtra("curractivity", Parcels.wrap(alldata.get(position) ));

            view.getContext().startActivity(i);
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return data.get(id).toString();
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
