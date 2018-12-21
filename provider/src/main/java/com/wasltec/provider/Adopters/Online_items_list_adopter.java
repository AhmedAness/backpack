package com.wasltec.provider.Adopters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.loopeer.shadow.ShadowView;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.Activity_details;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Activities.Home;
import com.wasltec.provider.Activities.Reviews;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.AllActivityReturnOpj;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Online_items_list_adopter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public List<AllActivityReturnOpj> data = new ArrayList<AllActivityReturnOpj>();
    public List<AllActivityReturnOpj> data2 = new ArrayList<AllActivityReturnOpj>();
    public List<AllActivityReturnOpj> data3 = new ArrayList<AllActivityReturnOpj>();


    public Online_items_list_adopter(Context context, List<AllActivityReturnOpj> data) {
        this.data = data;
        this.context = context;
    }

    public Online_items_list_adopter(Context context, List<AllActivityReturnOpj> data, List<AllActivityReturnOpj> data2, List<AllActivityReturnOpj> data3) {
        this.data = data;
        this.data2 = data2;
        this.data3 = data3;
        this.context = context;
        Log.v("TAG", "on" + data.size() + " of" + data2.size() + " in" + data3.size());
    }

    public void setData( List<AllActivityReturnOpj> data, List<AllActivityReturnOpj> data2, List<AllActivityReturnOpj> data3) {
        this.data = data;
        this.data2 = data2;
        this.data3 = data3;
       notifyDataSetChanged();
        Log.v("TAG", "on" + data.size() + " of" + data2.size() + " in" + data3.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == data.size() + 1 || (position == data2.size() + data.size() + 2))
            return 1;
        else if ((position - 1) < (data.size()))
            return 3;
        else if ((position - (data.size() + 2)) < data2.size()) {
            return 4;
        } else if ((position - (data.size() + data2.size()) + 3) < data3.size())
            return 2;
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 3 || viewType == 4) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_list_item, parent, false);

            return new Online_items_list_adopter.MyViewHolder(itemView);
        } else if (viewType == 2) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_list_item_incomplete, parent, false);
            return new MyViewHolder3(itemView);
        }else {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(16);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);
            return new MyViewHolder2(textView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            if (position == 0) {
                MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
                myViewHolder2.textView.setText(R.string.online);
                myViewHolder2.textView.setPadding(10, 30, 0, 20);
                myViewHolder2.textView.setGravity(Gravity.LEFT);

            } else if (position == data.size() + 1) {
                MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
                myViewHolder2.textView.setText(R.string.offline);
                myViewHolder2.textView.setPadding(10,30,0,20);
                myViewHolder2.textView.setGravity(Gravity.LEFT);


            } else if (position == data2.size() + data.size() + 2) {
                MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
                myViewHolder2.textView.setText(R.string.incomplete);
                myViewHolder2.textView.setPadding(10,30,0,20);
                myViewHolder2.textView.setGravity(Gravity.LEFT);


            }
        } else if (getItemViewType(position) == 2) {
            MyViewHolder3 myViewHolder3 = (MyViewHolder3) holder;
            try {
                Picasso.get().load(data3.get((position - (data.size() + data2.size()) + 3)).getUrl()).into(myViewHolder3.imageView);
            } catch (Exception e) {
                Log.d("logs", "getView: " + e.getMessage());
            }

            myViewHolder3.title.setText(data3.get((position - (data.size() + data2.size()) + 3)).getTitle());

        } else if (getItemViewType(position) == 4) {
            AllActivityReturnOpj item = data2.get(position - (data.size() + 2));

            Log.v("Pos", position + " " + getItemCount());
            MyViewHolder holder1 = (MyViewHolder) holder;
            if (item != null) {
                holder1.title.setText(item.getTitle());
            }
            holder1.desc.setText(item.getActivity_Location());
            holder1.vote_number.setText("" + item.getRate());
            holder1.ratingBar.setRating(item.getRate());
            holder1.preview.setVisibility(View.GONE);
            holder1.reload_layout.setVisibility(View.VISIBLE);
            holder1.reload_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AndroidNetworking.get(URLManger.getInstance().getChangeStatus(item.getId()+""))
                            .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(context).getToken())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    context.startActivity(new Intent(context,Home.class));

                                }

                                @Override
                                public void onError(ANError anError) {

                                    Toast.makeText(context,anError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });

            AllActivityReturnOpj finalItem = item;
            holder1.reviews_LL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Reviews.class);

                    Gson gson = new Gson();
                    String json = gson.toJson(finalItem);
                    intent.putExtra("Activity_id", finalItem.getId());
                    intent.putExtra("Activity_object", json);
                    context.startActivity(intent);
                }
            });
            holder1.row_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String json = gson.toJson(finalItem);
                    Intent intent = new Intent(context, Activity_details.class);
                    intent.putExtra("Activity_id", finalItem.getId());
                    intent.putExtra("Activity_object", json);
                    context.startActivity(intent);
                }
            });

            try {
//      Picasso.get().load("https://images.pexels.com/photos/462118/pexels-photo-462118.jpeg?auto=compress&cs=tinysrgb&h=350")
//              .error(R.drawable.booked_via_backpack)
//              .into(imageView);
                Picasso.get().load(item.getActivityPhotos().get(0).getUrl()).placeholder(R.drawable.activities_footer_icon)
                        .error(R.drawable.booked_via_backpack)
                        .into(holder1.imageView);
            } catch (Exception e) {
                Log.d(TAG, "getView: " + e.getMessage());
            }
        } else if (getItemViewType(position) == 3) {
            AllActivityReturnOpj item;

            item = data.get(position - 1);

            Log.v("Pos", position + " " + getItemCount());
            MyViewHolder holder1 = (MyViewHolder) holder;
            if (item != null) {
                holder1.title.setText(item.getTitle());
            }
            holder1.desc.setText(item.getActivity_Location());
            holder1.vote_number.setText("" + item.getRate());
            holder1.ratingBar.setRating(item.getRate());
            holder1.preview.setVisibility(View.VISIBLE);
            holder1.reload_layout.setVisibility(View.GONE);


            AllActivityReturnOpj finalItem = item;
            holder1.reviews_LL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Reviews.class);

                    Gson gson = new Gson();
                    String json = gson.toJson(finalItem);
                    intent.putExtra("Activity_id", finalItem.getId());
                    intent.putExtra("Activity_object", json);
                    context.startActivity(intent);
                }
            });
            holder1.row_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String json = gson.toJson(finalItem);
                    Intent intent = new Intent(context, Activity_details.class);
                    intent.putExtra("Activity_id", finalItem.getId());
                    intent.putExtra("Activity_object", json);
                    context.startActivity(intent);
                }
            });

            try {
//      Picasso.get().load("https://images.pexels.com/photos/462118/pexels-photo-462118.jpeg?auto=compress&cs=tinysrgb&h=350")
//              .error(R.drawable.booked_via_backpack)
//              .into(imageView);
                Picasso.get().load(item.getActivityPhotos().get(0).getUrl()).placeholder(R.drawable.activities_footer_icon)
                        .error(R.drawable.booked_via_backpack)
                        .into(holder1.imageView);
            } catch (Exception e) {
                Log.d(TAG, "getView: " + e.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + data2.size() + data3.size() + 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        final LinearLayout reviews_LL;
        TextView title, desc;
        RatingBar ratingBar;
        ShadowView preview;
        LinearLayout reload_layout;
        TextView vote_number;
        View row_item;

        public MyViewHolder(View row) {
            super(row);
            row_item = row;
            imageView = row.findViewById(R.id.image);
            title = row.findViewById(R.id.title);
            desc = row.findViewById(R.id.desc);
            reviews_LL = row.findViewById(R.id.reviews_LL);
            ratingBar = row.findViewById(R.id.rate_vote);
            preview = row.findViewById(R.id.preview);
            reload_layout = row.findViewById(R.id.reload_layout);
            vote_number = row.findViewById(R.id.vote_num);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder2(View row) {
            super(row);
            textView = (TextView) row;
        }
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        View row_item;
        ImageView imageView;
        TextView title;
        ImageView unverify;

        public MyViewHolder3(View itemView) {
            super(itemView);
            row_item = itemView;
            imageView = itemView.findViewById(R.id.image_in);
            unverify = itemView.findViewById(R.id.Unverify_icon);

            title = itemView.findViewById(R.id.title);

            row_item.setOnClickListener(v -> {

                Gson gson = new Gson();
                Log.v("Ahmed","3");
                String json = gson.toJson(data3.get((getAdapterPosition() - (data.size() + data2.size()) + 3)));
                Intent intent = new Intent(context, Add_new_activity.class);
                intent.putExtra("Activity_id", data3.get((getAdapterPosition() - (data.size() + data2.size()) + 3)).getId());
                intent.putExtra("step", data3.get((getAdapterPosition() - (data.size() + data2.size()) + 3)).getStepNumber());
                intent.putExtra("type", 1);

                context.startActivity(intent);
            });
        }
    }
}



