package com.wasltec.provider.Adopters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.model.BookingTicketDetail;
import com.wasltec.provider.model.Booking_item;
import com.wasltec.provider.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Customes_Adapter extends RecyclerView.Adapter<Customes_Adapter.ViewHolder> {

    Context context;
    private List<BookingTicketDetail> data;
    private LayoutInflater mInflater;
    private Customes_Adapter.ItemClickListener mClickListener;
    private int current_id;


    public Customes_Adapter(Context context, int current_id, List<BookingTicketDetail> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.current_id = current_id;
        this.data = data;


    }

    public int remove(int id) {
        data.remove(id);
        notifyItemRemoved(id);
        if (id < data.size() - 1){
            current_id++;
            notifyDataSetChanged();
        }
        else if (data.size()>0)
            current_id--;
        return
                current_id;


    }

    public int getCurrent_id() {
        return current_id;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public Customes_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.customes_item_layout, parent, false);
        return new Customes_Adapter.ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull Customes_Adapter.ViewHolder holder, int position) {

        if (data.get(position).getId() != current_id)
            holder.image.setVisibility(View.GONE);
        else {
            holder.image.setVisibility(View.VISIBLE);
        }

//      Picasso.with(context).load(data.get(position).getUrl()).into( holder.icon);
        holder.name.setText(data.get(position).getName());
        holder.number.setText("" + data.get(position).getTicketNumber());
        holder.note.setText("");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name, note, number;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.customer_name);
            note = itemView.findViewById(R.id.customer_note);
            number = itemView.findViewById(R.id.customer_num);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            //TODO
            //go to display  activity
        }
    }

    // convenience method for getting data at click position
    public BookingTicketDetail getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(Customes_Adapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
