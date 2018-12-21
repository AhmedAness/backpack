package com.wasltec.backpack.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.backpack.R;
import com.wasltec.backpack.models.ActivityDetails.ActivityRule;

import java.util.ArrayList;
import java.util.List;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.Holder> {

    List<String> categories;


//    public RulesAdapter(List<String> categories) {
//        this.categories = categories;
//    }

    public RulesAdapter(List<ActivityRule> activityRules) {
        this.categories = new ArrayList<>();
        for (ActivityRule activityRule : activityRules)
        {
            this.categories.add(activityRule.getDescription());
        }

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rule_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mText.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView mText;

        public Holder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text);

        }

    }
}
