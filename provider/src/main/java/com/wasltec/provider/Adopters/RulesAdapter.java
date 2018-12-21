package com.wasltec.provider.Adopters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.wasltec.provider.model.ActivityRule;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.Holder> {

    List<ActivityRule> prices;

    public RulesAdapter(List<ActivityRule> prices) {
//        for (ActivityAddOn price : prices) {
//            if (!(price.getName().length()<1||price.getName().equals("null")))
//
//
//        }
        this.prices = prices;
    }

    public RulesAdapter() {
    }

    @NonNull
    @Override
    public RulesAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.addones_item, parent, false));

    }

    public List<ActivityRule> getPrices()
    {
        return prices!=null? prices:new ArrayList<>();
    }
    public void addItem(ActivityRule activityAddOn){
        prices.add(activityAddOn);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull RulesAdapter.Holder holder, int position) {
        {
            holder.textView.setText(prices.get(position).getDescription());
            if (prices.get(position).isSelected())
                holder.imageButton.setImageResource(R.drawable.check_mark);
            else
                holder.imageButton.setImageResource(android.R.color.transparent);

            if (prices.get(position).isIntial())
                holder.delete.setVisibility(View.GONE);
            else
                holder.delete.setVisibility(View.VISIBLE);


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    http://backpackapis.wasltec.org/Api/Rule/DeleteRule?id=3


                    AndroidNetworking.get(URLManger.getInstance().getDeleteRule(""+prices.get(position).getId()
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

        }
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;
        ImageButton delete;

        public Holder(View convertView) {
            super(convertView);
            textView = (TextView) convertView.findViewById(R.id.text);
            imageButton = (ImageButton) convertView.findViewById(R.id.checkbox);
            delete = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prices.get(getAdapterPosition()).setSelected(!prices.get(getAdapterPosition()).isSelected());
                    notifyDataSetChanged();
                }
            });

        }
    }

}
