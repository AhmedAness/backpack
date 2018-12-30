package com.wasltec.provider.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.provider.Adopters.AddonesDialogAdapter;
import com.wasltec.provider.Adopters.Customes_Adapter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityAddOn;
import com.wasltec.provider.model.BookingTicketDetail;
import com.wasltec.provider.model.Reservation;
import com.wasltec.provider.model.booking.BookingDetail;
import com.wasltec.provider.model.booking.BookingTicketAddonsDetail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer_details extends AppCompatActivity {
    TextView customer_name,name_, mobile_numper, email_edit, cancel, price, pay,notpaid,Sataus,add_ons_btn,identification_btn;
//    ImageView scan;
    RecyclerView recyclerView;
    private Customes_Adapter adapter;
    int customer_id;
    private Reservation booking_item;
    private ImageView mClose;
    int index=0;
    boolean has_ticket=true;
    private Gson gson;
    List<BookingDetail> booking_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info);

        init();
        gson=new Gson();
        booking_item = Parcels.unwrap(getIntent().getParcelableExtra("t"));
        index  = getIntent().getIntExtra("index",0);

        callserver();

        customers_recycler_fn();

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (booking_item.getBookingTicketDetails().size() > 0&&booking_item.getBookingTicketDetails().size() >index ) {
            BookingTicketDetail booking_item2 = booking_item.getBookingTicketDetails().get(index);
            customer_name.setText(booking_item2.getName());
            name_.setText(booking_item2.getName());
            mobile_numper.setText(booking_item2.getMobile());
            email_edit.setText(booking_item2.getMail());


        }
        else
            has_ticket=false;





        price.setText(getResources().getString(R.string.sr)+" "+booking_item.getBookingAmount());

        add_ons_btn.setVisibility(View.INVISIBLE);
        add_ons_btn.setOnClickListener(v ->
                openDialog( booking_details.get(0).getBookingTicketDetails_individual().get(index).getBookingTicketAddonsDetails_individual()));
        identification_btn.setOnClickListener(v -> Toast.makeText(Customer_details.this,"identification",Toast.LENGTH_LONG).show());

    }

    private void callserver() {

        AndroidNetworking.post(URLManger.getInstance().getGetBookingDetails(""+booking_item.getId(),""+booking_item.getBookingTicketDetails().get(index).getTicketNumber()))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Customer_details.this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {


                    @Override
                    public void onResponse(JSONArray response) {

                        Type listType = new TypeToken<List<BookingDetail>>() {

                        }.getType();

                        booking_details = gson.fromJson(response.toString(), listType);



                        try {
                         if(booking_details.get(0).getBookingTicketDetails_individual().get(index).getBookingTicketAddonsDetails_individual().size()>0)
                         {
                             add_ons_btn.setVisibility(View.VISIBLE);
                             add_ons_btn.setEnabled(true);
                             BookingTicketDetail booking_item2 = booking_item.getBookingTicketDetails().get(index);
                             customer_name.setText(booking_item2.getName());
                             name_.setText(booking_item2.getName());
                             mobile_numper.setText(booking_item2.getMobile());
                             email_edit.setText(booking_item2.getMail());

                         }
                        }catch (Exception e){
                            Log.d("tag", "onResponse: "+e.getMessage());
                            add_ons_btn.setVisibility(View.INVISIBLE);
                        }




                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("err", "onError: "+anError.getErrorBody());

                    }
                });

    }
    public void openDialog(List<BookingTicketAddonsDetail> list) {
        final Dialog dialog = new Dialog(Customer_details.this); // Context, this, etc.
        View view = LayoutInflater.from(Customer_details.this).inflate(R.layout.dialog_add_add_on, null, false);
        dialog.setContentView(view);
        dialog.setTitle(R.string.add_add_ons);
        TextView Add_addons_btn = dialog.findViewById(R.id.Add_addons_btn);
        EditText Add_addons = dialog.findViewById(R.id.Add_addons);
        Add_addons.setVisibility(View.GONE);

        RecyclerView recyclerView = view.findViewById(R.id.add_ones);
        recyclerView.setLayoutManager(new LinearLayoutManager(Customer_details.this));

        List<ActivityAddOn> addons_data= new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            addons_data.add(new ActivityAddOn(
                    list.get(i).getAddonId(),
                    list.get(i).getName(),
                    list.get(i).getIcon()
                    )
            );
        }

        recyclerView.setAdapter( new AddonesDialogAdapter(addons_data, false,true, Customer_details.this));
        if (Add_new_activity.loader!=null)
            if (Add_new_activity.loader.isStart())
                Add_new_activity.loader.stop();
        dialog.show();

        Add_addons_btn.setText(getResources().getString(R.string.ok));

        Add_addons_btn.setOnClickListener(v -> {

            dialog.dismiss();


        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    private void customers_recycler_fn() {

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Customer_details.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter = new Customes_Adapter(Customer_details.this, 0, booking_item.getBookingTicketDetails());
        if (!booking_item.getBookingTicketDetails().get(index).isUserverified())
            Sataus.setText(  getResources().getString(R.string.verify) );
        else
            Sataus.setText(  getResources().getString(R.string.check_in) );
        adapter.setClickListener((view, position) -> {
            try {
                index=position;
                BookingTicketDetail booking_item2 = booking_item.getBookingTicketDetails().get(position);
                customer_name.setText(booking_item2.getName());
                name_.setText(booking_item2.getName());
                mobile_numper.setText(booking_item2.getMobile());
                email_edit.setText(booking_item2.getMail());


                if (!booking_item.getBookingTicketDetails().get(index).isUserverified())
                    Sataus.setText(  getResources().getString(R.string.verify) );
                else
                    Sataus.setText(  getResources().getString(R.string.check_in) );

            } catch (Exception e) {
                Toast.makeText(Customer_details.this, "fail to get customer data ", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void init() {
        recyclerView = findViewById(R.id.customers_recycer);
        customer_name = findViewById(R.id.c_name);
        name_ = findViewById(R.id.name_);
        mobile_numper = findViewById(R.id.mobile_numper);
        email_edit = findViewById(R.id.email_edit);
        cancel = findViewById(R.id.cancel);
        price = findViewById(R.id.price);
        pay = findViewById(R.id.pay);
        notpaid = findViewById(R.id.notpaid);
        Sataus = findViewById(R.id.Sataus);
        mClose = findViewById(R.id.close);
        add_ons_btn = findViewById(R.id.add_ons_btn);
        identification_btn = findViewById(R.id.identification_btn);
//        scan = findViewById(R.id.scan);
//        hash = findViewById(R.id.hash);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (has_ticket)
                {
//                    Intent intent = new Intent(Customer_details.this, BookingActivity.class);
//
//                    intent.putExtra("id",booking_item.getActivityId());
//                    startActivity(intent);
//                    finish();
                    AndroidNetworking.get(URLManger.getInstance().getCancel_Ticket(""+booking_item.getBookingTicketDetails().get(index).id))
                            .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Customer_details.this).getToken())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    index=adapter.remove(index);
                                    if (adapter.getItemCount()==0){
                                        finish();
                                    }
                                    else {
                                        if (index==-1){
                                            finish();
                                        }else {
                                            BookingTicketDetail booking_item2 = booking_item.getBookingTicketDetails().get(index);
                                            customer_name.setText(booking_item2.getName());
                                            name_.setText(booking_item2.getName());
                                            mobile_numper.setText(booking_item2.getMobile());
                                            email_edit.setText(booking_item2.getMail());
                                        }
                                    }
//                                    Toast.makeText(Customer_details.this, response.toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(Customer_details.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                    finish();
//                Toast.makeText(Customer_details.this,"click scan ",Toast.LENGTH_LONG).show();
            }
        });
//        scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Customer_details.this, Scaner.class));
////                Toast.makeText(Customer_details.this,"click scan ",Toast.LENGTH_LONG).show();
//                finish();
//            }
//        });
//        hash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Customer_details.this, "# ", Toast.LENGTH_LONG).show();
//
//            }
//        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Customer_details.this, Payment.class);
                intent.putExtra("t", Parcels.wrap(booking_item));
                intent.putExtra("booking_id",booking_item.getId());
                intent.putExtra("customer_id", customer_id);
                startActivity(intent);
                finish();
            }
        });

        Sataus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (Sataus.getText().toString().equals( getResources().getString(R.string.verify)))
               {
                   AndroidNetworking.get(URLManger.getInstance().getUser_Verified_Ticket(""+booking_item.getBookingTicketDetails().get(index).getId()))
                           .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(Customer_details.this).getToken())
                           .build()
                           .getAsJSONObject(new JSONObjectRequestListener() {
                               @Override
                               public void onResponse(JSONObject response) {


                                   Sataus.setText( getResources().getString(R.string.check_in));
                                   booking_item.getBookingTicketDetails().get(index).setUserverified(true);
                                   Toast.makeText(Customer_details.this,"done "+response.toString(),Toast.LENGTH_LONG).show();
                               }

                               @Override
                               public void onError(ANError anError) {
                                   Toast.makeText(Customer_details.this,""+anError.toString(),Toast.LENGTH_LONG).show();

                               }
                           });

               }
               else if(Sataus.getText().toString().equals( getResources().getString(R.string.check_in)))
               {
                   if (booking_item.getBookingTicketDetails().get(index).isTicketCheckedIn()){
                       Toast.makeText(Customer_details.this,"Ticket is already checked in ",Toast.LENGTH_LONG).show();
                   }
                   else
                   {
                       SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd H:m");
                       Date d2,d1;
                       long diffDays=2;
                       try {
                           d1 = inFormat.parse(booking_item.getActivityStart());
                           d2 = new Date();
                           //in milliseconds
                           long diff = d2.getTime() - d1.getTime();
                           diffDays = diff / (24 * 60 * 60 * 1000);
                       } catch (ParseException e) {

                           e.printStackTrace();
                       }
                       if (diffDays<1) {

                           AndroidNetworking.get(URLManger.getInstance().getCheckin_Ticket("" + booking_item.getBookingTicketDetails().get(index).getId()))
                                   .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(Customer_details.this).getToken())
                                   .build()
                                   .getAsJSONObject(new JSONObjectRequestListener() {
                                       @Override
                                       public void onResponse(JSONObject response) {
                                           booking_item.getBookingTicketDetails().get(index).setTicketCheckedIn(true);

                                           Toast.makeText(Customer_details.this, "done " + response.toString(), Toast.LENGTH_LONG).show();
                                       }

                                       @Override
                                       public void onError(ANError anError) {
                                           Toast.makeText(Customer_details.this, "done " + anError.toString(), Toast.LENGTH_LONG).show();

                                       }
                                   });
                       }
                       else {
                           Toast.makeText(Customer_details.this, "The Check-in option will be available 24 hours before the activity starts. ", Toast.LENGTH_SHORT).show();
                       }
                   }

               }
            }
        });

    }
}
