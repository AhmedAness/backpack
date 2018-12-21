package com.wasltec.provider.Adopters;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.Customer_details;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.BookingTicketDetail;
import com.wasltec.provider.model.Booking_item;
import com.wasltec.provider.model.Reservation;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking_dateAdapter_item extends RecyclerView.Adapter<Booking_dateAdapter_item.MyViewHolder> {

    Reservation Booking_items;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, NumOfTickets, Date, Time;
        LinearLayout row;
        RecyclerView reservatoin_teckits;
        private TextView mPaidFlag;
        private ImageView mBackpackFlag;
        private ImageView mVerifyFlag;
        private ImageView ticket_details;
        private TextView mNumofTickets;
        private ImageView messageicon;
        private ImageView mCheckinFlag;
        private CircularImageView muser_image;



        public MyViewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.Name);
            NumOfTickets = view.findViewById(R.id.NumofTickets);
            Date = view.findViewById(R.id.Date);
            Time = view.findViewById(R.id.end_date);
            ticket_details = view.findViewById(R.id.ticket_details);
            row = view.findViewById(R.id.row);
            mPaidFlag = view.findViewById(R.id.paid_flag);
            mBackpackFlag = view.findViewById(R.id.backpack_flag);
            mVerifyFlag = view.findViewById(R.id.verify_flag);
            mNumofTickets = view.findViewById(R.id.NumofTickets);
            mCheckinFlag = view.findViewById(R.id.checkin_flag);
            muser_image = view.findViewById(R.id.user_image);
            reservatoin_teckits = view.findViewById(R.id.reservatoin_teckits);
            messageicon = view.findViewById(R.id.messageicon);

        }
    }


    public Booking_dateAdapter_item(Reservation seconds, Activity activity) {


        this.Booking_items = seconds;

        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_item, parent, false);


        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Reservation booking_item = Booking_items;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.row.setBackgroundColor(activity.getResources().getColor(R.color.Platinum,null));
        }else{
            holder.row.setBackgroundColor(activity.getResources().getColor(R.color.Platinum));
        }

        holder.Name.setText(booking_item.getBookingTicketDetails().get(position+1).getName());
        holder.NumOfTickets.setVisibility(View.GONE);
        String curDate,endDate;
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd H:m");
        Date date = null;


        if (booking_item.getBookingTicketDetails().get(position+1).isMessageIcon())
            holder.messageicon.setVisibility(View.VISIBLE);
        else
            holder.messageicon.setVisibility(View.GONE);

        holder.messageicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity); // Context, this, etc.
                View view = LayoutInflater.from(activity).inflate(R.layout.msg_dialog, null, false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                EditText textView = view.findViewById(R.id.msg);

                view.findViewById(R.id.ok_btn).setOnClickListener(v2 -> {
                    if (textView.getText().toString().length()<1){
                        textView.setError(v.getResources().getString(R.string.error_empty_field));
                        textView.requestFocus();
                    }
                    else {
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("Message",textView.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        AndroidNetworking.post(URLManger.getInstance().getsendMessage(""+
                                Booking_items.getBookingTicketDetails().get(position+1).getId()))
                                .addHeaders("Content-Type","application/json")
                                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(v.getContext()).getToken())
//                                .addJSONObjectBody(jsonObject)
                                .addStringBody(textView.getText().toString())

                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                        dialog.dismiss();
                                        Toast.makeText(v.getContext(),anError.getErrorDetail(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });

        Date date2 = null;
        try {
            date = inFormat.parse(booking_item.getActivityStart());
            date2 = inFormat.parse(booking_item.getActivityEnd());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        curDate = formatter.format(date);

        endDate = formatter.format(date2);
        holder.Date.setText(curDate);
        holder.Time.setText(endDate);
        holder.ticket_details.setVisibility(View.GONE);
        holder.row.setOnClickListener(v -> {
            Intent intent = new Intent(activity, Customer_details.class);
//            ArrayList<BookingTicketDetail> d=booking_item.getBookingTicketDetails();
            intent.putExtra("t", Parcels.wrap(booking_item));
            intent.putExtra("index",position+1);

            activity.startActivity(intent);

        });
        if (booking_item.isIsPaid())
            holder.mPaidFlag.setVisibility(View.VISIBLE);
        else
            holder.mPaidFlag.setVisibility(View.INVISIBLE);



            if (booking_item.getBookingTicketDetails().get(position+1).isTicketCheckedIn()) {
                holder.mVerifyFlag.setVisibility(View.VISIBLE);
            }
            else
                holder.mVerifyFlag.setVisibility(View.INVISIBLE);

        holder.mBackpackFlag.setVisibility(View.VISIBLE);
//        if (booking_item.getBookingTicketDetails().get(position+1).isTicketCheckedIn()) {
//                holder.mCheckinFlag.setImageResource(R.drawable.check_circle);
//            }
//            else {
//                holder.mCheckinFlag.setImageResource(R.color.gray);
//            }


        Picasso.get()
                .load(booking_item.getBookingTicketDetails().get(position+1).getUserPhoto_Url())
                .placeholder(R.drawable.account_footer_icon_unchecked)
                .error(R.drawable.account_footer_icon_unchecked)
                .into(holder.muser_image);

        if ((booking_item.getBookingTicketDetails().get(position+1).isTicketCheckedIn())) {
            holder.mCheckinFlag.setVisibility(View.VISIBLE);

        } else {
            holder.mCheckinFlag.setVisibility(View.GONE);

        }



    }

    @Override
    public int getItemCount() {
        return Booking_items.getBookingTicketDetails().size()-1;
    }
}