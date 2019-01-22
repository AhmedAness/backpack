package com.wasltec.backpack.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.AllActivity;
import com.wasltec.backpack.models.ItemsCategory;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;

public class CategoryAdapter extends RecyclerView.Adapter<com.wasltec.backpack.Adapters.CategoryAdapter.Holder> {

    List<ItemsCategory> categories;

    public CategoryAdapter(List<ItemsCategory> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position==0){
            holder.mcontainer.setPadding(140,0,0,0);
        }
        holder.mCategoryName.setText(categories.get(position).getTitle());
        Glide.with(holder.mCategoryImg.getContext()).load(categories.get(position).getImage()).apply(new RequestOptions().error(R.drawable.backpack_icon_gray_watermark)).into(holder.mCategoryImg);


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView mCategoryImg;
        private TextView mCategoryName;
        private RelativeLayout mcontainer;


        public Holder(View itemView) {
            super(itemView);
            mCategoryImg = itemView.findViewById(R.id.category_img);
            mCategoryName = itemView.findViewById(R.id.category_name);
            mcontainer = itemView.findViewById(R.id.container_);
            itemView.setOnClickListener(v->{
                Intent intent=new Intent(itemView.getContext(), AllActivity.class);
                intent.putExtra("ID",categories.get(getAdapterPosition()).getID());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
