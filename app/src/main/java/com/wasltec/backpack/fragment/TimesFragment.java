package com.wasltec.backpack.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wasltec.backpack.Adapters.TimesAdapter;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.ActivityDetails.Avaliability;
import com.wasltec.backpack.models.ActivityTrip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.wasltec.backpack.Adapters.TimesAdapter.strings;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimesFragment extends DialogFragment {



    private RecyclerView mTimesList;
    private TimesFragment.Listener listener;
    private TextView mItemPrice;
    private TextView mCounter;
    private Button mSaveBtn;
    private float itemPrice;
    private Toolbar mToolbar;
    private TextView mDateTxt;
    private ActivityDetail object;
    DateFormat inputFormatter1;


    public TimesFragment() {
        inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_times, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mToolbar = view.findViewById(R.id.toolbar);

        mToolbar.setNavigationOnClickListener(v -> TimesFragment.this.dismiss());

        mTimesList = view.findViewById(R.id.timesList);
        object = DetailsActivity.object;
        mItemPrice = view.findViewById(R.id.item_price);
        mCounter = view.findViewById(R.id.counter);
        mSaveBtn = view.findViewById(R.id.save_btn);
        itemPrice = object.getFirstIndividualPrice();
        int sum = Cart.getInstance().getAdultCount() + Cart.getInstance().getChildCount();
        mCounter.setText(String.format("For %d individual", sum));
        mItemPrice.setText(String.format("%s SR", sum * itemPrice));
        mSaveBtn.setOnClickListener(v -> {
            if (TimesAdapter.selected != -1) {
                Cart.getInstance().setAvaliablityID(strings.get(TimesAdapter.selected));
                listener.onClose();
                dismiss();
            } else {
                Toast.makeText(getActivity(), "you must select valid Time", Toast.LENGTH_SHORT).show();
            }
        });
        List<Avaliability> avaliabilities=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        try {
            calendar.setTime(inputFormatter1.parse(Cart.getInstance().getAvaliablityID().getActivityStart()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Avaliability avaliability : DetailsActivity.object.getAvaliabilities()) {
            Calendar calendar1 = Calendar.getInstance();
            try {
                calendar1.setTime(inputFormatter1.parse(avaliability.getActivityStart()));
                boolean sameDay = calendar.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR);
                if (sameDay){
                    avaliabilities.add(avaliability);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        mTimesList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mTimesList.setAdapter(new TimesAdapter(avaliabilities));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM");
        mDateTxt = view.findViewById(R.id.date_txt);
        try {
            mDateTxt.setText(dateFormat.format(inputFormatter1.parse(object.getAvaliabilities().get(0).getActivityStart())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (TimesFragment.Listener) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);

    }

    public interface Listener {
        public void onClose();
    }
}
