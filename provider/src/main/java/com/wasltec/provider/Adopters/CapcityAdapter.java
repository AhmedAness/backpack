package com.wasltec.provider.Adopters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.anychart.graphics.vector.Text;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Price;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CapcityAdapter extends RecyclerView.Adapter<CapcityAdapter.Holder> {

    List<Price> prices;
    boolean hasCapacity = false;
    ChangeListener changeListener;
    public boolean isHasCapacity() {
        return hasCapacity;
    }

    public void setHasCapacity(boolean hasCapacity) {
        this.hasCapacity = hasCapacity;
        notifyDataSetChanged();
    }

    public CapcityAdapter(List<Price> prices, ChangeListener changeListener) {
        this.prices = prices;
        this.changeListener = changeListener;
    }
    public int getTotalCapacity(){
        int count=0;
        for (int i = 1; i < prices.size(); i++) {
          try {
              count+=Integer.parseInt(prices.get(i).getPrice());
          }catch (Exception e)
          {
              count+=0;
          }

            }
            return count;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_prices, parent, false));
    }

    public List<Price> getPrices() {
        return prices;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int tmp =position;
                if (prices.get(position).getId()==0)
                {
                    prices.remove(tmp);
                    notifyDataSetChanged();
                }else
                {
                    AndroidNetworking.get(URLManger.getInstance().getDeleteIndividualCategory(""+prices.get(position).getId()))
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(holder.mCategoryName.getContext()).getToken())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    prices.remove(tmp);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });
                }
            }
        });
        if (position == 0) {
            holder.mCategoryName.setText(prices.get(position).getCategory());
            holder.mCategoryName.setCursorVisible(false);
            holder.mCategoryName.setEnabled(false);
            holder.mCategoryPrice.setText(prices.get(position).getPrice());
            holder.mCategoryPrice.setFreezesText(true);
            holder.mCategoryPrice.setCursorVisible(false);
            holder.mCategoryPrice.setEnabled(false);
            holder.mCategoryName.setTextColor(Color.BLACK);
            holder.delete.setVisibility(View.GONE);
            holder.mCurr.setVisibility(View.INVISIBLE);

            holder.mCategoryPrice.setTextColor(Color.BLACK);
            holder.mCategoryName.setTextSize(14);
            holder.mCategoryPrice.setTextSize(14);

        } else if (position < prices.size()) {
            holder.mCategoryName.setText(prices.get(position).getCategory());
            holder.mCategoryName.setCursorVisible(false);
            holder.mCategoryName.setEnabled(false);
            holder.mCategoryPrice.setText(prices.get(position).getPrice());
            holder.mCategoryPrice.setFreezesText(true);
            holder.mCategoryPrice.setCursorVisible(false);
            holder.delete.setVisibility(View.VISIBLE);
            holder.mCategoryPrice.setEnabled(false);
        } else {
            if (!hasCapacity) {
                holder.mCategoryPrice.setHint("Na");
                holder.mCategoryPrice.setClickable(false);
                holder.mCategoryPrice.setEnabled(false);
                holder.mCategoryPrice.setCursorVisible(false);
                holder.mCategoryName.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) holder.mCategoryName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("name", holder.mCategoryName.getText().toString());
                            jsonObject.put("capacity", 0);
                            jsonObject.put("price", "0.0");
                            jsonObject.put("price_after_discount", "0.0");
                            jsonObject.put("activityid", Add_new_activity.ActivityID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        boolean found=false;
                        for (Price p:prices) {
                            if(p.getCategory().equals(holder.mCategoryName.getText().toString())){
                                found=true;
                                break;
                            }
                        }
                        if (!found)
                        {
                            prices.add(new Price(holder.mCategoryName.getText().toString(), String.valueOf(0)));
                            notifyDataSetChanged();
                        }
                        else {
                            holder.mCategoryName.setError(holder.mCategoryName.getContext().getResources().getString(R.string.error_textview));
                        }


                        int count=0;
                        for (int i = 1; i < prices.size(); i++) {
                            count+=Integer.parseInt(prices.get(i).getPrice());
                        }
                        changeListener.onChange(count);
                        return true; // Focus will do whatever you put in the logic.
                    }
                    return false;  // Focus will change according to the actionId
                });

            } else {

                holder.mCategoryPrice.setText("");
                holder.mCategoryPrice.setClickable(true);
                holder.mCategoryPrice.setEnabled(true);
                holder.mCategoryPrice.setCursorVisible(true);
                holder.mCategoryPrice.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) holder.mCategoryPrice.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("name", holder.mCategoryName.getText().toString());
                            jsonObject.put("capacity", holder.mCategoryPrice.getText().toString());
                            jsonObject.put("price", "0.0");
                            jsonObject.put("price_after_discount", "0.0");
                            jsonObject.put("activityid", Add_new_activity.ActivityID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        prices.add(new Price(holder.mCategoryName.getText().toString(), holder.mCategoryPrice.getText().toString()));
                        notifyDataSetChanged();

                        int count=0;
                        for (int i = 1; i < prices.size(); i++) {
                            count+=Integer.parseInt(prices.get(i).getPrice());
                        }
                        changeListener.onChange(count);

//                    Add_new_activity.dialog.show();
//                    AndroidNetworking.post(URLManger.getInstance().getCreateIndividualCategory())
//                            .addJSONObjectBody(jsonObject)
//                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(holder.mCategoryName.getContext()).getToken())
//                            .build().getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            prices.add(new Price(holder.mCategoryName.getText().toString(), holder.mCategoryPrice.getText().toString()));
//                            notifyDataSetChanged();
//                            Add_new_activity.dialog.hide();
//                            int count=0;
//                            for (int i = 1; i < prices.size(); i++) {
//                                count+=Integer.parseInt(prices.get(i).getPrice());
//
//                                }
//                                changeListener.onChange(count);
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            Add_new_activity.dialog.hide();
//                            Toast.makeText(holder.mCategoryName.getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
//                        }
//                    });


                        return true; // Focus will do whatever you put in the logic.
                    }
                    return false;  // Focus will change according to the actionId
                });

            }
            holder.mCategoryName.setText("");
            holder.delete.setVisibility(View.GONE);
            holder.mCategoryName.setEnabled(true);
            holder.mCategoryPrice.setCursorVisible(true);
            holder.mCategoryName.setCursorVisible(true);

        }


    }

    @Override
    public int getItemCount() {
        return prices.size() + 1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        EditText mCategoryName;
        EditText mCategoryPrice;
        TextView mCurr;
        ImageView delete ;

        public Holder(View convertView) {
            super(convertView);
            mCategoryName = (EditText) convertView.findViewById(R.id.category_name);
            mCategoryPrice = (EditText) convertView.findViewById(R.id.category_price);
            mCategoryPrice.setClickable(false);
            mCategoryName.setImeOptions(EditorInfo.IME_ACTION_DONE);
            delete = (ImageView) convertView.findViewById(R.id.delete);
            mCurr =  convertView.findViewById(R.id.curr);
            mCategoryPrice.setHint(R.string.capcity);

        }
    }
    public interface ChangeListener{
        void onChange(int totalcapcity);
    }
}
