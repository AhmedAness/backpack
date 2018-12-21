package com.wasltec.backpack.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.ActivityDetails.Avaliability;
import com.wasltec.backpack.models.Times;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.Holder> {

    public static List<Avaliability> strings;
    public static int selected = -1;
    private SimpleDateFormat inputFormatter1;
    private SimpleDateFormat dateFormat;

    public TimesAdapter(List<Avaliability> avaliabilities) {

        strings = avaliabilities;
        inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat = new SimpleDateFormat("hh:mm a");
        }

    public List<Avaliability> getStrings() {
        return strings;
    }

    public void setStrings(List<Avaliability> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.times_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (strings.size() > position) {
            try {
                holder.mTextView4.setText(dateFormat.format(inputFormatter1.parse(strings.get(position).getActivityStart().replace("T"," "))) +
                        " - " + dateFormat.format(inputFormatter1.parse(strings.get(position).getActivityEnd().replace("T"," "))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (position == selected) {
            holder.itemView.setSelected(true);
        } else
            holder.itemView.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        protected TextView mTextView4;

        public Holder(View itemView) {
            super(itemView);
            mTextView4 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(v -> {
                if (!mTextView4.getText().toString().isEmpty())
                    if (itemView.isSelected())
                        selected = -1;
                    else {
                        selected = getAdapterPosition();
                    }   notifyDataSetChanged();;
            });


        }
    }
}
