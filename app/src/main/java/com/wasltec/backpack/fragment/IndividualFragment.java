package com.wasltec.backpack.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualFragment extends Fragment {
    private Button mDateBtn;
    private Button mTimeBtn;
    private TextView mTextView;
    private TextView mTicketsRemain;
    private TextView mTicketsCount;
    private RelativeLayout mTicketsBtn;
    private Button mProviderBtn;
    private ActivityDetail trip;
    static boolean init = false;
    private int sum;
    TicketsCounterFragment ticketsCounterFragment;

    public IndividualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (cart.getAdultCount() + cart.getChildCount() > 0)
//            mTicketsCount.setText(String.format(getString(R.string.tickets_details_holder), cart.getAdultCount(), cart.getChildCount()));
        try {
            mTimeBtn.setText(String.format(getString(R.string.Time)+" %s", Cart.getInstance().getAvaliablityID() != null ? new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(Cart.getInstance().getAvaliablityID().getActivityStart())) :
                    getString(R.string.No_Time_Selected)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static boolean valid() {

        if (Cart.getInstance().getTickets() == null)
            return false;

        return init;
    }

    public void onClose(int sum, int price) {
        this.sum = sum;

//        mTicketsCount.setText(String.format(getString(R.string.tickets_details_holder), , counter2));
//        if (cart.getAdultCount() + cart.getChildCount() > 0)
//            mTicketsCount.setText(String.format(getString(R.string.tickets_details_holder), cart.getAdultCount(), cart.getChildCount()));
//        mTimeBtn.setText(String.format("%s", Cart.getInstance().getTotal_date()));
        mTicketsRemain.setText(String.format(getString(R.string.Slots_remain_holder), (trip.getTotalCapacity() - trip.getAvaliabilities().get(0).getTotalTickets()) - sum));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDateBtn = view.findViewById(R.id.date_btn);
        mTimeBtn = view.findViewById(R.id.time_btn);
        mTextView = view.findViewById(R.id.textView);
        mTicketsRemain = view.findViewById(R.id.tickets_remain);
        mTicketsCount = view.findViewById(R.id.tickets_count);
        mTicketsBtn = view.findViewById(R.id.tickets_btn);
        mProviderBtn = view.findViewById(R.id.provider_btn);

        Cart.getInstance().setIsgroub(false);
        trip = DetailsActivity.object;
        if (Cart.getInstance().getAvaliablityID() == null || Cart.getInstance().getAvaliabilitiesCount() == 1)
            mTimeBtn.setEnabled(false);
        if (Cart.getInstance().getAvaliablityID() == null)
            mTicketsBtn.setEnabled(false);

        mTicketsRemain.setText(String.format(getString(R.string.Slots_remain_holder), (trip.getTotalCapacity() - trip.getAvaliabilities().get(0).getTotalTickets()) - sum));
        mTicketsBtn.setOnClickListener(v -> {
            ticketsCounterFragment = new TicketsCounterFragment();
            ticketsCounterFragment.show(getFragmentManager(), "5");


        });
        mDateBtn.setOnClickListener(v -> {
            DateFragment ticketsCounterFragment = new DateFragment();
            ticketsCounterFragment.setRetainInstance(true);
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                bundle.putInt("object", getArguments().getInt("object", 0));
            }
            ticketsCounterFragment.setArguments(bundle);

            ticketsCounterFragment.show(getFragmentManager(), "6");

            init = true;
        });
        mTimeBtn.setOnClickListener(v -> {
            TimesFragment timesFragment = new TimesFragment();
            timesFragment.show(getActivity().getSupportFragmentManager(), "f");
        });
    }

    public void onClose(Date counter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");
        mDateBtn.setText(dateFormat.format(counter));
        onClose();
        if (Cart.getInstance().getAvaliablityID() == null || Cart.getInstance().getAvaliabilitiesCount() == 1)
            mTimeBtn.setEnabled(false);
        else
            mTimeBtn.setEnabled(true);

        if (Cart.getInstance().getAvaliablityID() == null)
            mTicketsBtn.setEnabled(false);
        else
            mTicketsBtn.setEnabled(true);

    }

    public void onClose() {
        if (Cart.getInstance().getAvaliablityID() == null || Cart.getInstance().getAvaliabilitiesCount() == 1)
            mTimeBtn.setEnabled(false);
        else
            mTimeBtn.setEnabled(true);

        if (Cart.getInstance().getAvaliablityID() == null)
            mTicketsBtn.setEnabled(false);
        else
            mTicketsBtn.setEnabled(true);

        try {
            mTimeBtn.setText(String.format("Time: %s", Cart.getInstance().getAvaliablityID() != null ?
                    new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(Cart.getInstance().getAvaliablityID().getActivityStart())) +
                    " - " + new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(Cart.getInstance().getAvaliablityID().getActivityEnd())) :
                    "No Date Selected"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
