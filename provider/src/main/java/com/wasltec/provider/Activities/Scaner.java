package com.wasltec.provider.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.BookingTicketDetail;
import com.wasltec.provider.model.Reservation;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

public class Scaner extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            Log.d("tag", "BarcodeResult ");
            barcodeView.setStatusText(result.getText());
            ticketNum(result.getText());


        }


        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
            Log.d("tag", "possibleResultPoints ");
        }
    };
    private EditText mTicketNumber;
    private ImageView mSearchTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaner);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 12);

        barcodeView = findViewById(R.id.barcode_scanner);
        barcodeView.setStatusText("");


//        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.CODE_128,BarcodeFormat.CODE_93,BarcodeFormat.UPC_A,BarcodeFormat.UPC_E,BarcodeFormat.EAN_13,BarcodeFormat.EAN_8,BarcodeFormat.ITF,BarcodeFormat.CODE_39,BarcodeFormat.CODABAR);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory());
        barcodeView.decodeContinuous(callback);
        Log.d("tag", " barcodeView.decodeContinuous(callback);");

        mTicketNumber = findViewById(R.id.ticket_number);
        mSearchTicket = findViewById(R.id.search_ticket);
        mSearchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTicketNumber.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), R.string.error_empty_field, Toast.LENGTH_SHORT).show();
                } else
                    ticketNum(mTicketNumber.getText().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {

        barcodeView.pause();

        super.onPause();


    }
    
    // the action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void ticketNum(String ticketNum) {
        for (Reservation reservation : BookingActivity.reservations) {
            for (BookingTicketDetail bookingTicketDetail : reservation.getBookingTicketDetails()) {
                if (ticketNum.equals(bookingTicketDetail.getTicketNumber())) {

                    if (!bookingTicketDetail.ticketCheckedIn)
                        openDialog2(reservation ,bookingTicketDetail, reservation.isIsPaid());
                    else if(!bookingTicketDetail.userverified)
                        openDialog(reservation ,bookingTicketDetail, reservation.isIsPaid());




                }
            }
        }
        Toast.makeText(getApplicationContext(),"Invalid ticket Number",Toast.LENGTH_SHORT).show();
        mTicketNumber.setText("");
    }

    public void openDialog(Reservation reservation, BookingTicketDetail bookingTicketDetail, boolean isPaid) {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_verify_ticket, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView mName = dialog.findViewById(R.id.name);
        TextView mTicketNumber = dialog.findViewById(R.id.ticket_number);
        CheckBox mPrice = dialog.findViewById(R.id.price);
        Button mButton = dialog.findViewById(R.id.button);
        TextView mAddAddonsBtn = dialog.findViewById(R.id.Add_addons_btn);
        mName.setText(bookingTicketDetail.getName());
        mTicketNumber.setText(String.format("%s%d", getString(R.string.ticket_no_holder), bookingTicketDetail.getTicketNumber()));
        mPrice.setText(getString(R.string.currency) + reservation.getBookingAmount() + getString(R.string.price_holder2));
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Payment.class);
            intent.putExtra("booking_id",reservation.getId());
            startActivity(intent);
        });
        mAddAddonsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AndroidNetworking.get(URLManger.getInstance().getCheckin_Ticket(""+bookingTicketDetail.getTicketNumber()))
                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(Scaner.this).getToken())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                dialog.dismiss();
                                openDialog2(reservation,bookingTicketDetail, isPaid);
                               Toast.makeText(Scaner.this,"done "+response.toString(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(Scaner.this,"done "+anError.toString(),Toast.LENGTH_LONG).show();

                            }
                        });


            }
        });

        dialog.show();
    }

    public void openDialog2(Reservation reservation, BookingTicketDetail bookingTicketDetail, boolean isPaid) {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_checkin_ticket, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView mName = dialog.findViewById(R.id.name);
        TextView mTicketNumber = dialog.findViewById(R.id.ticket_number);
        CheckBox mCheck = dialog.findViewById(R.id.check);
        TextView mButton = dialog.findViewById(R.id.button);
        TextView mPaidFlag = dialog.findViewById(R.id.paid_flag);
        TextView mview_ticket = dialog.findViewById(R.id.view_ticket);
        TextView mAddAddonsBtn = dialog.findViewById(R.id.Add_addons_btn);
        mName.setText(bookingTicketDetail.getName());
        Reservation f;
        mTicketNumber.setText(String.format("%s%s", getString(R.string.ticket_no_holder), bookingTicketDetail.getTicketNumber()));
        mButton.setText(getString(R.string.currency) + reservation.getBookingAmount());
        if (isPaid)
            mPaidFlag.setText(R.string.paid);
        else
            mPaidFlag.setText(R.string.not_paid);

        mview_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scaner.this, Customer_details.class);
                intent.putExtra("t", Parcels.wrap(reservation));
                startActivity(intent);
            }
        });
        mAddAddonsBtn.setOnClickListener(v -> {

            AndroidNetworking.get(URLManger.getInstance().getUser_Verified_Ticket(""+bookingTicketDetail.getId()))
                    .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(Scaner.this).getToken())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Scaner.this.finish();
                            dialog.dismiss();

                            Toast.makeText(Scaner.this,"done "+response.toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Scaner.this,"done "+anError.toString(),Toast.LENGTH_LONG).show();

                        }
                    });

        });

        dialog.show();

    }


    public void openDialogverify(Reservation reservation, BookingTicketDetail bookingTicketDetail, boolean isPaid) {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_checkin_ticket, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView mName = dialog.findViewById(R.id.name);
        TextView mTicketNumber = dialog.findViewById(R.id.ticket_number);
        CheckBox mCheck = dialog.findViewById(R.id.check);
        TextView mButton = dialog.findViewById(R.id.button);
        TextView mPaidFlag = dialog.findViewById(R.id.paid_flag);
        TextView mview_ticket = dialog.findViewById(R.id.view_ticket);
        TextView mAddAddonsBtn = dialog.findViewById(R.id.Add_addons_btn);
        mName.setText(bookingTicketDetail.getName());
        mAddAddonsBtn.setText(getResources().getString(R.string.verify));
        Reservation f;
        mTicketNumber.setText(String.format("%s%d", getString(R.string.ticket_no_holder), bookingTicketDetail.getTicketNumber()));
        mButton.setText(getString(R.string.currency) + reservation.getBookingAmount());
        if (isPaid)
            mPaidFlag.setText(R.string.paid);
        else
            mPaidFlag.setText(R.string.not_paid);

        mview_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scaner.this, Customer_details.class);
                intent.putExtra("t", Parcels.wrap(reservation));
                startActivity(intent);
            }
        });
        mAddAddonsBtn.setOnClickListener(v -> {

            AndroidNetworking.get(URLManger.getInstance().getUser_Verified_Ticket(""+bookingTicketDetail.getId()))
                    .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(Scaner.this).getToken())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Scaner.this.finish();
                            dialog.dismiss();

                            Toast.makeText(Scaner.this,"done "+response.toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Scaner.this,"done "+anError.toString(),Toast.LENGTH_LONG).show();

                        }
                    });

        });

        dialog.show();

    }

}
