package com.wasltec.provider.Adopters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityAddOn;
import com.wasltec.provider.model.Price;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wasltec.provider.Activities.Book_new.total_price;

public class AddonesDialogAdapter extends RecyclerView.Adapter<AddonesDialogAdapter.Holder> {

    List<ActivityAddOn> prices;
    boolean from_book_now=false,fromCustomer_detail=false;
    Context context;

    public AddonesDialogAdapter(List<ActivityAddOn> addOns , boolean from_book_now,boolean fromCustomer_detail , Context context) {

        this.from_book_now=from_book_now;
        this.fromCustomer_detail=fromCustomer_detail;
        this.prices= new ArrayList<>();
        for (int i = 0; i < addOns.size(); i++) {
            if (addOns.get(i)==null||addOns.get(i).getName()==null||addOns.get(i).getName().trim().equals("")||addOns.get(i).getName().trim().isEmpty())
                continue;
            this.prices.add(addOns.get(i));
        }


        this.context= context;
    }



    @NonNull
    @Override
    public AddonesDialogAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.addones_item, parent, false));

    }

    public List<ActivityAddOn> getPrices() {
        return prices;
    }

    public void addItem(ActivityAddOn activityAddOn){

        prices.add(0,activityAddOn);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull AddonesDialogAdapter.Holder holder, int position) {
        {
            holder.textView.setText(prices.get(position).getName());

            if (from_book_now)
            {
                holder.imageButton.setBackground(context.getResources().getDrawable(R.drawable.edit_boarder));
                holder.textView.setText(prices.get(position).getName()+" ( "+prices.get(position).getPrice()+" SR ) ");

            }

            holder.mdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AndroidNetworking.get(URLManger.getInstance().getDeleteAddons(""+prices.get(position).getId()
                    ))
                            .addHeaders("Content-Type","application/json")
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(v.getContext()).getToken())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    prices.remove(position);
                                    notifyDataSetChanged();

                                }


                                @Override
                                public void onError(ANError anError) {


                                    Toast.makeText(v.getContext(),anError.getErrorDetail(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

                if (prices.get(position).isSelected())
                    holder.imageButton.setImageResource(R.drawable.check_mark);
                else
                    holder.imageButton.setImageResource(android.R.color.transparent);

               if (prices.get(position).isIntial())
                    holder.mdelete.setVisibility(View.GONE);
                else
                   holder.mdelete.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;
        ImageButton mdelete;

        public Holder(View convertView) {
            super(convertView);
            textView = (TextView) convertView.findViewById(R.id.text);
            imageButton = (ImageButton) convertView.findViewById(R.id.checkbox);
            mdelete = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!fromCustomer_detail)
                    {
                        if (prices.get(getAdapterPosition()).isSelected())
                        {
                            total_price-=prices.get(getAdapterPosition()).getPrice();
                        }
                        else{
                            total_price+=prices.get(getAdapterPosition()).getPrice();
                        }
                        prices.get(getAdapterPosition()).setSelected(!prices.get(getAdapterPosition()).isSelected());
                        notifyDataSetChanged();
                    }

                }
            });
        }
    }



}
