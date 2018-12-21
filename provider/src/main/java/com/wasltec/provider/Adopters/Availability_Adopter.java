package com.wasltec.provider.Adopters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Availability;
import com.wasltec.provider.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Availability_Adopter extends RecyclerView.Adapter<Availability_Adopter.MyViewHolder> {

    List<Availability> availability_Items;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ImageView image, close,delete_item;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.availability_time);
            image = view.findViewById(R.id.availability_icon);
            delete_item = view.findViewById(R.id.delete_item);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    public Availability_Adopter() {
    }

    public Availability_Adopter(Activity activity, List<Availability> availability_Items) {
        this.availability_Items = availability_Items;
        this.activity = activity;
//        selected = availability_Items.get(0);
    }


    public List<Availability> getAvailability_Items() {
        if (availability_Items != null) {
            return availability_Items;
        } else
            return new ArrayList<>();
    }

    @Override
    public Availability_Adopter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.availability_item, parent, false);

        return new Availability_Adopter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Availability_Adopter.MyViewHolder holder, int position) {
        Availability item = availability_Items.get(position);
        holder.name.setText(item.toString());

        if (item.getIsForGroup() == 1)
            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.individual_icon));
        else
            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.private_group_icon));
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=position;
                if(item.getId()!=0)
                {
                    AndroidNetworking.get(URLManger.getInstance().getDeleteAvailablility(""+item.getId()))
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(activity).getToken())
                            .build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.has("status")){
                                try {
                                    if (response.getInt("status")==0)
                                    {
                                        Toast.makeText(activity,response.has("message")?response.getString("message"):"Can't delete this availability",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        availability_Items.remove(index);
                                        notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            availability_Items.remove(index);
                            notifyDataSetChanged();
//                            Toast.makeText(activity, "Can't delete this availability"+anError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    availability_Items.remove(index);
                    notifyDataSetChanged();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return availability_Items.size();
    }

}
