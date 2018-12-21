package com.wasltec.provider.Adopters;

import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.Reviews;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Post_item;

import java.lang.reflect.Type;
import java.util.List;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.MyViewHolder> {

    List<Post_item> post_Items;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,desc,date,reply,report;
        CircularImageView image;
        RatingBar rate;
        EditText edittext_chatbox;
        Button send;
        LinearLayout reply_lout;




        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.Name);
            desc = view.findViewById(R.id.item_description);
            date = view.findViewById(R.id.Date);
            reply = view.findViewById(R.id.reply);
            report = view.findViewById(R.id.report);
            reply_lout = view.findViewById(R.id.reply_lout);
            edittext_chatbox = view.findViewById(R.id.edittext_chatbox);
            send = view.findViewById(R.id.button_chatbox_send);

            image = view.findViewById(R.id.image);
            rate = view.findViewById(R.id.rate_vote);

        }
    }


    public GoodAdapter(Activity activity,List<Post_item> pastes) {
        this.post_Items = pastes;
        this.activity= activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        Post_item item = post_Items.get(position);
        holder.name.setText(item.getUser_name());
        holder.desc.setText(item.getReview());
        holder.date.setText(item.getDate().toString());

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.reply_lout.setVisibility(View.VISIBLE);
            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edittext_chatbox.getText().toString();


//
//                AndroidNetworking.post( URLManger.getInstance().getReviewReplies(item.getReviewid()+""))
//                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(activity).getToken())
//                        .build()
//                        .getAsJSONArray(new JSONArrayRequestListener() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//
//
//                                Type listType = new TypeToken<List<Post_item>>() {
//                                }.getType();
//
//                                    pastes = gson.fromJson(response.toString(), listType);
//                                    fillter();
//
//
//
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Log.d("", "onResponse:"+anError.getMessage());
//                            }
//                        });
            }
        });

//        holder.image.setImageDrawable(activity.getResources().getDrawable( R.drawable.booked_via_backpack));
        Picasso.get().load(item.getImage_url()).placeholder(R.drawable.activities_footer_icon).error(R.drawable.booked_via_backpack).into(holder.image);


        holder.rate.setRating(item.getRate());


    }

    @Override
    public int getItemCount() {
        return post_Items.size();
    }
}