package com.wasltec.backpack.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasltec.backpack.Adapters.AddonesAdapter2;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityDetails.ActivityAddOn;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.BookingTicket;
import com.wasltec.backpack.models.BookingTicketAddonsModel;
import com.wasltec.backpack.utils.CounterBtn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupTripFragment extends Fragment {


    private Button mCountBtn;
    private static Button mDateBtn;
    private Button mTimeBtn;
    private TextView mItemPrice;
    int sum = 0;
    int total;
    private RecyclerView mFeatureList;
    private Button mProviderBtn;
    private ActivityDetail trip;
    private Button mpeaple_btn;
    private LinearLayout mContainer;
    private static TextInputEditText mBackpackerName, mBackpackerPhone, mBackpackerEmail, mbackpacker_group_name, mbackpacker_group_number;

    public GroupTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        total = DetailsActivity.object.getGroupPrice();
        Cart.getInstance().setTotal_price(total);

        return inflater.inflate(R.layout.fragment_group_trip, container, false);
    }

    public static boolean valid() {

        Context context = mBackpackerName.getContext();
        String s = context.getResources().getString(R.string.your_date_txt);
        if(mDateBtn.getText().toString().equals(s))
            mDateBtn.setError((context.getResources().getString(R.string.error_textview)));
        else if (mBackpackerName.getText().toString().equals("")) {
            mBackpackerName.setError(context.getResources().getString(R.string.error_textview));
        } else if (mBackpackerPhone.getText().toString().equals("")) {
            mBackpackerPhone.setError(context.getResources().getString(R.string.error_textview));

        } else if (mBackpackerEmail.getText().toString().equals("")) {
            mBackpackerEmail.setError(context.getResources().getString(R.string.error_textview));

        } else if (mbackpacker_group_name.getText().toString().equals("")) {
            mbackpacker_group_name.setError(context.getResources().getString(R.string.error_textview));

        } else if (mbackpacker_group_number.getText().toString().equals("")) {
            mbackpacker_group_number.setError(context.getResources().getString(R.string.error_textview));

        } else {
            List<BookingTicket> tickets = new ArrayList<>();
            BookingTicket ticket = new BookingTicket();
            ticket.setName(mBackpackerName.getText().toString());
            ticket.setMobile(mBackpackerPhone.getText().toString());
            ticket.setMail(mBackpackerEmail.getText().toString());
            ticket.setNameOfGroup(mbackpacker_group_name.getText().toString());
            ticket.setNumOfGroup(mbackpacker_group_number.getText().toString());

            List<BookingTicketAddonsModel> bookingTicketAddonsModels = new ArrayList<>();


            for (int key : Cart.getInstance().getGroup_addons().keySet()) {
                int value = Cart.getInstance().getGroup_addons().get(key);

                BookingTicketAddonsModel tmp = new BookingTicketAddonsModel();
                tmp.setAddonsId("" + key);
                tmp.setCount("" + value);
                bookingTicketAddonsModels.add(tmp);


            }


            ticket.setBookingTicketAddonsModel(bookingTicketAddonsModels);
            tickets.add(ticket);
            Cart.getInstance().setTickets(tickets);
            return true;
        }
        return false;
    }

    public void onClose(int sum, int price) {
        this.sum = sum;

//        mTicketsCount.setText(String.format(getString(R.string.tickets_details_holder), , counter2));
//        if (cart.getAdultCount() + cart.getChildCount() > 0)
//            mTicketsCount.setText(String.format(getString(R.string.tickets_details_holder), cart.getAdultCount(), cart.getChildCount()));
//        mTimeBtn.setText(String.format("%s", Cart.getInstance().getTotal_date()));
//        mTicketsRemain.setText(String.format(getString(R.string.Slots_remain_holder), (trip.getTotalCapacity() - trip.getAvaliabilities().get(0).getTotalTickets()) - sum));

    }

    public void onClose(Date counter)

    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");

        mDateBtn.setText(dateFormat.format(counter));


        onClose();
        if (Cart.getInstance().getAvaliablityID() == null || Cart.getInstance().getAvaliabilitiesCount() == 1)
            mTimeBtn.setEnabled(false);
        else
            mTimeBtn.setEnabled(true);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCountBtn = view.findViewById(R.id.count_btn);
        mDateBtn = view.findViewById(R.id.date_btn);
        mTimeBtn = view.findViewById(R.id.time_btn);
        mFeatureList = view.findViewById(R.id.feature_list);
        mProviderBtn = view.findViewById(R.id.provider_btn);
        mpeaple_btn = view.findViewById(R.id.peaple_btn);
        mContainer = view.findViewById(R.id.container);


        mBackpackerName = view.findViewById(R.id.backpacker_name);
        mBackpackerPhone = view.findViewById(R.id.backpacker_phone);
        mBackpackerEmail = view.findViewById(R.id.backpacker_email);
        mbackpacker_group_name = view.findViewById(R.id.backpacker_group_name);
        mbackpacker_group_number = view.findViewById(R.id.backpacker_group_number);

        mDateBtn.setOnClickListener(v -> {
            DateFragment ticketsCounterFragment = new DateFragment();
            ticketsCounterFragment.show(getFragmentManager(), "6");

        });


        Cart.getInstance().setIsgroub(true);

        trip = DetailsActivity.object;

        mTimeBtn.setText(String.format("%s", Cart.getInstance().getTotal_date() != null ? Cart.getInstance().getTotal_date() :
                trip.getAvaliabilities().get(0).getActivityStart() + " - " + trip.getAvaliabilities().get(0).getActivityEnd()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");


        mFeatureList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mFeatureList.setAdapter(new AddonesAdapter2(trip.getActivityAddOns()));
        mTimeBtn.setOnClickListener(v -> {
            TimesFragment timesFragment = new TimesFragment();
            timesFragment.show(getActivity().getSupportFragmentManager(), "f");
        });
//        mDateBtn.setText(dateFormat.format(Cart.getInstance().getStart()));


        mItemPrice = getActivity().findViewById(R.id.item_price);
        ///////////////////////////
        for (ActivityAddOn activityAddOn : trip.getActivityAddOns()) {
            CounterBtn counterBtn = new CounterBtn(getActivity());
            counterBtn.setId(View.generateViewId());
            counterBtn.setPrice((int) activityAddOn.getActivityAddonsId());
            counterBtn.setTitleText(activityAddOn.getName());

            counterBtn.setListener((view12, counter, oldCount, addon_id) -> {

                float price = 0;
                for (ActivityAddOn activityAddOn1 :DetailsActivity.object.getActivityAddOns())
                    if (activityAddOn1.getActivityAddonsId()==addon_id) {
                        price = activityAddOn1.getPrice();
                        break;
                    }

                if (counter < oldCount) {
                    sum -= 1;
                    total -= price;
                } else {
                    sum += 1;
                    total += price;
                }

                Cart.getInstance().setTotal_price(total);
                Cart.getInstance().getGroup_addons().put(counterBtn.getPrice(), counterBtn.getCounter());


            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button button = new Button(getContext());
            button.setId(View.generateViewId());
            mContainer.addView(counterBtn, layoutParams);

        }


        /////////////////////////////////
    }

    public void onClose() {
        mTimeBtn.setText(String.format("%s", Cart.getInstance().getTotal_date() != null ? Cart.getInstance().getTotal_date() :
                Cart.getInstance().getAvaliablityID().getActivityStart() + " - " + Cart.getInstance().getAvaliablityID().getActivityEnd()));





    }
}
