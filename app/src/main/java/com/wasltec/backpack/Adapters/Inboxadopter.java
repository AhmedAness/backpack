package com.wasltec.backpack.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.ChatActivity;
import com.wasltec.backpack.models.InboxModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Inboxadopter extends RecyclerView.Adapter<Inboxadopter.MyViewHolder> {

    ArrayList<InboxModel> inboxModels;

    Activity activity;
    public Inboxadopter(ArrayList<InboxModel> inboxModels, Activity activity) {
        this.inboxModels= inboxModels;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InboxModel item = inboxModels.get(position);



        Glide.with(holder.mimage_inbox.getContext()).load(item.getActivityPhotoUrl()).into(holder.mimage_inbox);

        String lastUpdateDate =item.getLastMessage() ;

        if (item.getUnReadableMessageNo()!=0)
            holder.unReadableMessageNo.setText(""+item.getUnReadableMessageNo());
        else
            holder.unReadableMessageNo.setVisibility(View.GONE);

        holder.userName.setText(item.getTitle());


        lastUpdateDate=lastUpdateDate.replace("T"," ");

        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd H:m");
        Date date = null;


        try {
            date = inFormat.parse(lastUpdateDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat1 = new SimpleDateFormat("EEEE");
        SimpleDateFormat outFormat2 = new SimpleDateFormat("h a");
        String day = outFormat1.format(date);
        String daytime = outFormat2.format(date);


        holder.lastUpdateDate.setText(activity.getString(R.string.lastmessage)+day+" at "+daytime);


        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"clicked row id "+item.getChatId(),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(activity,ChatActivity.class);

                intent.putExtra("ChatId",item.getChatId());
                intent.putExtra("ticketId",item.getTicketId());
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return inboxModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mimage_inbox;
        TextView lastUpdateDate;
        TextView unReadableMessageNo;
        TextView userName;
        View row;

        public MyViewHolder(View view) {
            super(view);

            row=view;
            mimage_inbox = view.findViewById(R.id.image_inbox);
            lastUpdateDate = view.findViewById(R.id.lastUpdateDate);
            userName = view.findViewById(R.id.userName);
            unReadableMessageNo = view.findViewById(R.id.unReadableMessageNo);
        }
    }

}




