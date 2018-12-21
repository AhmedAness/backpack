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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Price;
import com.wasltec.provider.model.Price_per_individual;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.Holder> {

     static List<Price_per_individual> prices;
    boolean discount= false ;

    public PriceAdapter(List<Price_per_individual> prices ,Boolean discount ) {
        this.prices = prices;
        this.discount= discount;
    }

    public PriceAdapter(List<Price_per_individual> prices  ) {
        this.prices = prices;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_prices, parent, false));
    }

    public List<Price_per_individual> getPrices() {
        return prices;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        if (discount){

            holder.mCurr.setVisibility(View.INVISIBLE);
            holder.mCategoryName.setText(prices.get(position).getName());
            holder.mCategoryName.setCursorVisible(false);
            holder.mCategoryName.setEnabled(false);
            holder.mCategoryPrice.setText(String.valueOf(prices.get(position).getPrice_after_discount()));
            holder.mCategoryPrice.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) holder.mCategoryPrice.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    prices.get(position).setPrice_after_discount(Double.parseDouble(holder.mCategoryPrice.getText().toString()));

//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("name", prices.get(position).getName());
//                        jsonObject.put("price", prices.get(position).getPrice());
//                        jsonObject.put("capacity", prices.get(position).getCapacity());
//                        jsonObject.put("price_after_discount", holder.mCategoryPrice.getText().toString());
//                        jsonObject.put("activityid", ActivityID);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    AndroidNetworking.post(URLManger.getInstance().getCreateIndividualCategory())
//                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(holder.mCategoryName.getContext()).getToken())
//                            .addJSONObjectBody(jsonObject).build().getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            prices.get(position).setPrice_after_discount(Double.parseDouble(holder.mCategoryPrice.getText().toString()));
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            Toast.makeText(holder.mCategoryName.getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
//                        }
//                    });


                }
                return true;
            });
        }else{

            holder.mCurr.setVisibility(View.VISIBLE);
            holder.mCategoryName.setText(prices.get(position).getName());
            holder.mCategoryName.setCursorVisible(false);
            holder.mCategoryName.setEnabled(false);
            holder.mCategoryPrice.setText(String.valueOf(prices.get(position).getPrice()));

            holder.mCategoryPrice.setText(String.valueOf(prices.get(position).getPrice()));
            holder.mCategoryPrice.setCursorVisible(false);
            holder.mCategoryPrice.setEnabled(false);
            holder.mCategoryPrice.setText(String.valueOf(prices.get(position).getPrice()));
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AndroidNetworking.get(URLManger.getInstance().getDeleteIndividualCategory("" + prices.get(position).getId()))
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(holder.mCategoryName.getContext()).getToken())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    prices.remove(prices.get(position));
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(ANError anError) {

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
        EditText mCategoryName;
        EditText mCategoryPrice;
        TextView mCurr;
        ImageView delete;

        public Holder(View convertView) {
            super(convertView);
            mCategoryName = (EditText) convertView.findViewById(R.id.category_name);
            mCategoryPrice = (EditText) convertView.findViewById(R.id.category_price);
            mCurr = convertView.findViewById(R.id.curr);
            delete = (ImageView) convertView.findViewById(R.id.delete);
        }
    }

}
