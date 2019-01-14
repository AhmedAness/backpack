package com.wasltec.backpack.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<com.wasltec.backpack.Adapters.AllAdapter.Holder> {
    public List<ActivityTrip> items;


    public AllAdapter() {
        this.items = new ArrayList<>();

    }
    public AllAdapter(List<ActivityTrip> items ) {
        this.items = items;

    }

    public void setItems(List<ActivityTrip> items) {
        this.items = items;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder2, int position) {
        holder2.mItemName.setText(items.get(position).getTitle());
        holder2.mItemPrice.setText(String.format(holder2.itemView.getContext().getString(R.string.price_holder), items.get(position).getPrice()));
//        holder.mItemRating.setRating(items.get(position).getTitle());
        holder2.mItemName.setText(items.get(position).getTitle());
        Glide.with(holder2.mItemImg.getContext()).load(items.get(position).getCover()).into(holder2.mItemImg);

        holder2.mRatingCount.setText(String.valueOf(items.get(position).getRate()));
        holder2.mItemRating.setRating(Float.parseFloat(holder2.mRatingCount.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {


        ImageView mItemImg;
        public TextView mItemName;
        TextView mItemPrice;
        public RatingBar mItemRating;
        public TextView mRatingCount;
        private ImageButton mFavBtn;

        public Holder(View itemView) {
            super(itemView);
            mItemImg = itemView.findViewById(R.id.item_img);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemPrice = itemView.findViewById(R.id.item_price);
            mItemRating = itemView.findViewById(R.id.item_rating);
            mRatingCount = itemView.findViewById(R.id.rating_count);
            mFavBtn = itemView.findViewById(R.id.fav_btn);
            mFavBtn.setOnClickListener(v -> {

                JSONObject jsonObject =new JSONObject();
                try {


                    jsonObject.put("activity_id",""+ items.get(getAdapterPosition()).getId());
                    jsonObject.put("user_id",""+ Session.getInstance(v.getContext()).getUser().getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (mFavBtn.isSelected()){
                    AndroidNetworking.post(URLManager.getInstance().getRemoveFromFavourite())
                            .addHeaders("Authorization", "bearer "+ Session.getInstance(v.getContext()).getToken())
                            .addJSONObjectBody(jsonObject)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int state=0;
                                    try {
                                        state=  response.getInt("status");
                                        Toast.makeText(itemView.getContext(),
                                                response.getString("message"),
                                                Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if (state==1)
                                        mFavBtn.setSelected(!mFavBtn.isSelected());
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(itemView.getContext(),
                                            anError.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    Toast.makeText(itemView.getContext(), R.string.added_to_favourite, Toast.LENGTH_SHORT).show();
                }else {

                    AndroidNetworking.post(URLManager.getInstance().getAddToFavourite())
                            .addHeaders("Authorization", "bearer "+ Session.getInstance(v.getContext()).getToken())
                            .addJSONObjectBody(jsonObject)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int state=0;
                                    try {
                                          state=  response.getInt("status");
                                        Toast.makeText(itemView.getContext(),
                                               response.getString("message"),
                                                Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if (state==1)
                                    mFavBtn.setSelected(!mFavBtn.isSelected());
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(itemView.getContext(),
                                            anError.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    Toast.makeText(itemView.getContext(), R.string.added_to_favourite, Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(v.getContext(), DetailsActivity.class);
                i.putExtra("object", items.get(getAdapterPosition()).getId());
                v.getContext().startActivity(i);

            });

        }
    }
}
