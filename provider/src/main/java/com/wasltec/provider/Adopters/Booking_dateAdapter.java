package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
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
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.ChatActivity;
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

import okhttp3.Response;

public class Booking_dateAdapter extends RecyclerView.Adapter<Booking_dateAdapter.MyViewHolder> {

    List<Reservation> Booking_items;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, NumOfTickets, Date, Time;
        LinearLayout row;
        RecyclerView reservatoin_teckits;
          private TextView mPaidFlag;
          private ImageView mBackpackFlag;
          private ImageView mVerifyFlag;
          private ImageView ticket_details;
          private ImageView messageicon;
          private TextView mNumofTickets;
        private ImageView mCheckinFlag;
        private CircularImageView mUser_image;

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
              mUser_image= view.findViewById(R.id.user_image);
              reservatoin_teckits = view.findViewById(R.id.reservatoin_teckits);
              messageicon = view.findViewById(R.id.messageicon);

        }
    }


    public Booking_dateAdapter(List<Reservation> seconds, Activity activity) {

        this.Booking_items= new ArrayList<>();
        for (int i = 0; i < seconds.size(); i++)
            if (seconds.get(i).getBookingTicketDetails().size()>0)
                this.Booking_items .add(seconds.get(i));

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
        final Reservation booking_item = Booking_items.get(position);
        holder.reservatoin_teckits.setVisibility(View.INVISIBLE);
        holder.reservatoin_teckits.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));


        holder.Name.setText(booking_item.getBookingTicketDetails().get(0).getName());
        if(booking_item.getBookingTicketDetails().size()>1)
            holder.NumOfTickets.setText(String.format("%sX", String.valueOf(booking_item.getBookingTicketDetails().size())));
        else
        {
            holder.NumOfTickets.setVisibility(View.INVISIBLE);
            holder.ticket_details.setVisibility(View.INVISIBLE);
        }

        if (booking_item.getBookingTicketDetails().get(0).isMessageIcon())
            holder.messageicon.setVisibility(View.VISIBLE);
        else
            holder.messageicon.setVisibility(View.INVISIBLE);

        holder.messageicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Booking_items.get( holder.getAdapterPosition()).getBookingTicketDetails().get(0).isGroupTicket){

                    Intent intent = new Intent(activity, ChatActivity.class);

                    intent.putExtra("ChatId", -1);
                    intent.putExtra("chatname", Booking_items.get(holder.getAdapterPosition()).getBookingTicketDetails().get(0).getName());

                    activity.startActivity(intent);

                }else {
//                Toast.makeText(activity,"clicked row id "+data.get(getAdapterPosition()).getAvaid(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, ChatActivity.class);

                    intent.putExtra("ChatId", Booking_items.get(holder.getAdapterPosition()).getBookingTicketDetails().get(0).getChat_id());
                    intent.putExtra("chatname", Booking_items.get(holder.getAdapterPosition()).getBookingTicketDetails().get(0).getName());

                    activity.startActivity(intent);
                }
            }
        });

        String curDate,endDate;
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd H:m");
        Date date = null;

        Date date2 = null;
        try {
            date = inFormat.parse(booking_item.getActivityStart());
            date2 = inFormat.parse(booking_item.getActivityEnd());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy H:m");
        curDate = formatter.format(date);
        endDate = formatter.format(date2);

        holder.Date.setText(curDate);
        holder.Time.setText(endDate);

        holder.ticket_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.reservatoin_teckits.getVisibility()==View.GONE)
                {
                    holder.reservatoin_teckits.setVisibility(View.VISIBLE);
                    holder.reservatoin_teckits.setAdapter(new Booking_dateAdapter_item(Booking_items.get(position),activity));
//                    holder.ticket_details.setBackground(activity.getResources().getDrawable(R.drawable.collaps_tickets));
                    holder.ticket_details.setImageResource(R.drawable.collaps_tickets);

                }else
                {
                    holder.reservatoin_teckits.setVisibility(View.GONE);
//                    holder.ticket_details.setBackground(activity.getResources().getDrawable(R.drawable.expand_tickets));
                    holder.ticket_details.setImageResource(R.drawable.expand_tickets);

                }

            }
        });
        holder.row.setOnClickListener(v -> {
            Intent intent = new Intent(activity, Customer_details.class);
//            ArrayList<BookingTicketDetail> d=booking_item.getBookingTicketDetails();
            intent.putExtra("t", Parcels.wrap(booking_item));
            intent.putExtra("index", 0);

            activity.startActivity(intent);

        });
        if (booking_item.isIsPaid())
            holder.mPaidFlag.setVisibility(View.VISIBLE);
        else
            holder.mPaidFlag.setVisibility(View.INVISIBLE);

        if (booking_item.getBookingType()==1)
            holder.mBackpackFlag.setVisibility(View.VISIBLE);
        else
            holder.mBackpackFlag.setVisibility(View.INVISIBLE);


        for (BookingTicketDetail bookingTicketDetail : booking_item.getBookingTicketDetails()) {
            if (bookingTicketDetail.isTicketCheckedIn()) {
                holder.mVerifyFlag.setVisibility(View.VISIBLE);
                break;
            }
            else
                holder.mVerifyFlag.setVisibility(View.INVISIBLE);
        }
        for (BookingTicketDetail bookingTicketDetail : booking_item.getBookingTicketDetails()) {

            Picasso.get()
                    .load(bookingTicketDetail.getUserPhoto_Url())
                    .placeholder(R.drawable.account_footer_icon_unchecked)
                    .error(R.drawable.account_footer_icon_unchecked)
                    .into(holder.mUser_image);

            if (bookingTicketDetail.isTicketCheckedIn()) {
                holder.mCheckinFlag.setVisibility(View.VISIBLE);
                break;
            } else {
                holder.mCheckinFlag.setVisibility(View.INVISIBLE);

            }

        }




    }

    @Override
    public int getItemCount() {
        return Booking_items.size();
    }
}