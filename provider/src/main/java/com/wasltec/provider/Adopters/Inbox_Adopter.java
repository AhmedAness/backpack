package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.ChatActivity;
import com.wasltec.provider.R;
import com.wasltec.provider.model.Inbox_model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inbox_Adopter extends RecyclerView.Adapter<Inbox_Adopter.Holder> {
    List<Inbox_model> data ;

    Activity activity;
    public Inbox_Adopter(FragmentActivity activity, ArrayList<Inbox_model> data) {
        this.data=data;
        this.activity=activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        Inbox_model item = data.get(position);

        Picasso.get().load(item.getUserPhoto_Url()).placeholder(R.drawable.individual_icon)
                .error(R.drawable.booked_via_backpack)
                .into(holder.mimage_inbox);

        String lastUpdateDate =item.getLastUpdateDate() ;

        if (item.getUnReadableMessageNo()!=0)
            holder.unReadableMessageNo.setText(""+item.getUnReadableMessageNo());
        else
            holder.unReadableMessageNo.setVisibility(View.GONE);

        holder.userName.setText(item.getUserName());


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


        holder.lastUpdateDate.setText(activity.getString(R.string.lastmessage)+" "+ day+" at "+daytime);


        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"clicked row id "+item.getChatId(),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(activity,ChatActivity.class);

                intent.putExtra("ChatId",item.getChatId());
                intent.putExtra("chatname",item.getUserName());
                activity.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView mimage_inbox;
        TextView lastUpdateDate;
        TextView unReadableMessageNo;
        TextView userName;
        View row;

        public Holder(View view) {
            super(view);

            row=view;
              mimage_inbox = view.findViewById(R.id.image_inbox);
              lastUpdateDate = view.findViewById(R.id.lastUpdateDate);
              userName = view.findViewById(R.id.userName);
              unReadableMessageNo = view.findViewById(R.id.unReadableMessageNo);
        }
    }

}






