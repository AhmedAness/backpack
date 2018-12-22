package com.wasltec.backpack.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.SimilarActivitiesResponse;

import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.Holder> {

    List<SimilarActivitiesResponse> categories;


    public SimilarAdapter(List<SimilarActivitiesResponse> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
      try {
          holder.mItemName.setText(categories.get(position).getTitle());
          holder.mItemPrice.setText(String.format(holder.itemView.getContext().getString(R.string.price_holder), "" + categories.get(position).getPrice()));
          holder.mRatingCount.setText(String.format(holder.itemView.getContext().getString(R.string.rating_num_holder), "" + categories.get(position).getReviewsCount()));
          Glide.with(holder.itemView.getContext()).load(categories.get(position).getActivityPhoto_Url()).into(holder.mItemImg);
          holder.mItemRating.setRating(Float.parseFloat(categories.get(position).getRate()));
      }
      catch (Exception e )
      {

      }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView mItemImg;
        private TextView mItemName;
        private TextView mItemPrice;
        private RatingBar mItemRating;
        private TextView mRatingCount;


        public Holder(View itemView) {
            super(itemView);
            mItemImg = itemView.findViewById(R.id.item_img);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemPrice = itemView.findViewById(R.id.item_price);
            mItemRating = itemView.findViewById(R.id.item_rating);
            mRatingCount = itemView.findViewById(R.id.rating_count);

        }

    }
}
