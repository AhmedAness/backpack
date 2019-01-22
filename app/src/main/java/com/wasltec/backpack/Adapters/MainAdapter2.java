package com.wasltec.backpack.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.AllActivity;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.ItemsCategory;
import com.wasltec.backpack.models.SectionItem;

import java.util.List;

public class MainAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<SectionItem<ActivityTrip>> items;
    List<ItemsCategory> categories;

    public static final int Activity = -3;
    public static final int Categories = -2;

    public MainAdapter2() {
    }

    public MainAdapter2(List<SectionItem<ActivityTrip>> items, List<ItemsCategory> categories) {
        this.items = items;
        this.categories = categories;
    }


    public void setCategories(List<ItemsCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setItems(List<SectionItem<ActivityTrip>> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Categories;
        }
        return Activity;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Categories) {
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            FrameLayout.LayoutParams f = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            frameLayout.setLayoutParams(f);
            return new Holder3(frameLayout);
        }
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != Categories) {
            if (items.get(position - 1).getActivities().size() == 0) {
                holder.itemView.setVisibility(View.GONE);
            }
            Holder holder1 = (Holder) holder;

            ((Holder) holder).mTitle.setText(String.format(((Holder) holder).mTitle.getContext().getString(R.string.explore_holder), items.get(position - 1).getLabel()));
            ((Holder) holder).mShowMore.setText(String.format(((Holder) holder).mShowMore.getContext().getString(R.string.show_50_more), items.get(position - 1).getMore()));
            AllAdapter allAdapter = new AllAdapter(items.get(position - 1).getActivities().subList(0, items.get(position - 1).getActivities().size() >= 2 ? 2 : items.get(position - 1).getActivities().size()));
            holder1.recyclerView.setAdapter(allAdapter);


        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        private TextView mTitle;
        private TextView mShowMore;

        public Holder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mShowMore = itemView.findViewById(R.id.show_more);
            recyclerView = itemView.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            mShowMore.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AllActivity.class);
                intent.putExtra("ID", items.get(getAdapterPosition() - 1).getActivities().get(0).getCategoryId());
                itemView.getContext().startActivity(intent);
            });

        }
    }


    class Holder3 extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public Holder3(View itemView) {
            super(itemView);
            FrameLayout frameLayout = (FrameLayout) itemView;
            recyclerView = new RecyclerView(itemView.getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
            recyclerView.setAdapter(categoryAdapter);
            LinearLayoutManager linearLayout = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayout);
            layoutParams.topMargin = (int) itemView.getContext().getResources().getDimension(R.dimen._25sdp);
            layoutParams.bottomMargin = (int) itemView.getContext().getResources().getDimension(R.dimen._25sdp);
//            recyclerView.setPadding((int) itemView.getContext().getResources().getDimension(R.dimen._29sdp),0,0,0);
//            layoutParams.leftMargin = (int) itemView.getContext().getResources().getDimension(R.dimen._29sdp);
            recyclerView.setLayoutParams(layoutParams);
            frameLayout.addView(recyclerView);
        }
    }

}
