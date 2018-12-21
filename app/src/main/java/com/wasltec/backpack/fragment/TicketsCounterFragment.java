package com.wasltec.backpack.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wasltec.backpack.Adapters.AddonesAdapter2;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityDetails.ActivityAddOn;
import com.wasltec.backpack.models.ActivityDetails.IndividualCategory;
import com.wasltec.backpack.models.BookingTicket;
import com.wasltec.backpack.models.BookingTicketAddonsModel;
import com.wasltec.backpack.utils.CounterBtn;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketsCounterFragment extends DialogFragment {


    Listener listener;
    double itemPrice;
    private Toolbar mToolbar;
    private TextView mCounter, mItemPrice;
    private CounterBtn mAdultCounter;

    int sum;
    private LinearLayout mUserInfo;
    private LinearLayout mContainer;
    private Button mSave;
    int total = 0;


    public TicketsCounterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
        return inflater.inflate(R.layout.fragment_my_dialog, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar);
        mContainer = view.findViewById(R.id.container);
        mCounter = view.findViewById(R.id.counter);
        mItemPrice = view.findViewById(R.id.item_price);
        mSave = view.findViewById(R.id.save_btn);
        mToolbar.setNavigationOnClickListener(v -> TicketsCounterFragment.this.dismiss());

        Log.w("FF", "onViewCreated: " + DetailsActivity.object.getIndividualCategories().size());
        for (IndividualCategory individualCategory : DetailsActivity.object.getIndividualCategories()) {
            CounterBtn counterBtn = new CounterBtn(getActivity());
            counterBtn.setId(View.generateViewId());
            counterBtn.setPrice(individualCategory.getPrice());
            counterBtn.setTitleText(individualCategory.getName());
            counterBtn.setListener((view12, counter, oldCount, price) -> {
                if (counter < oldCount) {
                    mUserInfo.removeViewAt(sum - 1);
                    sum -= 1;
                } else {
                    addView(sum + 1, individualCategory.getId());
                    sum += 1;
                }

                mCounter.setText(String.format("For %d individual", sum));
                total -= oldCount * price;
                total += counter * price;
                mItemPrice.setText(String.format("%s SR", total));
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button button = new Button(getContext());
            button.setId(View.generateViewId());
            mContainer.addView(counterBtn, layoutParams);

        }


        mSave.setOnClickListener(v -> {
            if(valid()) {
                ArrayList<BookingTicket> bookingTickets = new ArrayList<>();
                for (int i = 0; i < mUserInfo.getChildCount(); i++) {
                    View view1 = mUserInfo.getChildAt(i);
                    if (i == 0) {
                        Session session = Session.getInstance(v.getContext());
                        BookingTicket bookingTicket = new BookingTicket();
                        bookingTicket.setName(session.getUser().getName());
                        bookingTicket.setMail(session.getUser().getMail());
                        bookingTicket.setMobile(session.getUser().getMobile());
                        TextView category_id = view1.findViewById(R.id.category_id);
                        RecyclerView mAddOnes = view1.findViewById(R.id.add_ones);

                        bookingTicket.setCategoryId(Integer.parseInt(category_id.getText().toString()));
                        AddonesAdapter2 m = (AddonesAdapter2) mAddOnes.getAdapter();
                        List<BookingTicketAddonsModel> bookingTicketAddonsModels = new ArrayList<>();
                        for (ActivityAddOn activityAddOn : m.getCheckedItems()) {
                            BookingTicketAddonsModel bookingTicketAddonsModel = new BookingTicketAddonsModel();
                            total += activityAddOn.getPrice();
                            bookingTicketAddonsModel.setAddonsId(String.valueOf(activityAddOn.getActivityAddonsId()));
                            bookingTicketAddonsModels.add(bookingTicketAddonsModel);
                        }
                        bookingTicket.setBookingTicketAddonsModel(bookingTicketAddonsModels);


                        bookingTickets.add(bookingTicket);

                    } else {


                        TextInputLayout mBackpackerName = view1.findViewById(R.id.backpacker_name);
                        TextInputLayout mBackpackerPhone = view1.findViewById(R.id.backpacker_phone);
                        TextInputEditText mBackpackerEmail = view1.findViewById(R.id.backpacker_email);
                        TextView category_id = view1.findViewById(R.id.category_id);

                        RecyclerView mAddOnes = view1.findViewById(R.id.add_ones);
                        AddonesAdapter2 m = (AddonesAdapter2) mAddOnes.getAdapter();
                        BookingTicket bookingTicket = new BookingTicket();
                        bookingTicket.setName(mBackpackerName.getEditText().getText().toString());
                        bookingTicket.setMail(mBackpackerEmail.getText().toString());
                        bookingTicket.setMobile(mBackpackerPhone.getEditText().getText().toString());
                        bookingTicket.setCategoryId(Integer.parseInt(category_id.getText().toString()));
                        List<BookingTicketAddonsModel> bookingTicketAddonsModels = new ArrayList<>();
                        for (ActivityAddOn activityAddOn : m.getCheckedItems()) {
                            BookingTicketAddonsModel bookingTicketAddonsModel = new BookingTicketAddonsModel();
                            total += activityAddOn.getPrice();
                            bookingTicketAddonsModel.setAddonsId(String.valueOf(activityAddOn.getAddonsId()));
                            bookingTicketAddonsModels.add(bookingTicketAddonsModel);
                        }
                        bookingTicket.setBookingTicketAddonsModel(bookingTicketAddonsModels);
                        bookingTickets.add(bookingTicket);
                    }

                }
                Cart.getInstance().setTickets(bookingTickets);
                listener.onClose(total, sum);
                dismiss();

            }
        });

        mCounter.setText(String.format(getString(R.string.counter_holder), sum));
        mItemPrice.setText(String.format("%s SR", sum * itemPrice));
        final int[] count = {0};
        mUserInfo = view.findViewById(R.id.user_info);

    }


    boolean valid() {

        for(int i=0; i<mContainer.getChildCount(); i++) {
            // TODO: 11/4/2018 complete validation
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public interface Listener {
        public void onClose(int price, int count);
    }

    public void addView(int count, Integer id) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_info_item, null, false);
        LinearLayout.LayoutParams layoutInflater = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setMargins(0, 10, 0, 0);
        view.setLayoutParams(layoutInflater);
        CheckBox mCheckBox = view.findViewById(R.id.checkBox);
        RelativeLayout mOwner = view.findViewById(R.id.owner);
        TextView mBackpackerNumber = view.findViewById(R.id.backpacker_number);
        TextInputLayout mBackpackerName = view.findViewById(R.id.backpacker_name);
        TextInputLayout mBackpackerPhone = view.findViewById(R.id.backpacker_phone);
        TextInputEditText mBackpackerEmail = view.findViewById(R.id.backpacker_email);
        TextView category_id = view.findViewById(R.id.category_id);
        category_id.setText("" + id);
        RecyclerView mAddOnes = view.findViewById(R.id.add_ones);
        mBackpackerNumber.setText("BackPacker No." + count);
        mAddOnes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAddOnes.setAdapter(new AddonesAdapter2(DetailsActivity.object.getActivityAddOns()));
        if (count == 1) {
            mOwner.setVisibility(View.VISIBLE);
            mBackpackerEmail.setVisibility(View.GONE);
            mBackpackerPhone.setVisibility(View.GONE);
            mBackpackerName.setVisibility(View.GONE);
        }
        mUserInfo.addView(view);

    }

}
