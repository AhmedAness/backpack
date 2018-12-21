package com.wasltec.backpack.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteria;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteriaType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityDetails.Avaliability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends DialogFragment {


    Listener listener;
    private Toolbar mToolbar;
    private TextView mCurrentDate;
    private CalendarView mCalendarView;
    private TextView mItemPrice;
    private TextView mCounter;
    private double itemPrice;
    private Button mSaveBtn;
    SimpleDateFormat inputFormatter1;

    public DateFragment() {
        inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    }
    // Required empty public constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(v -> DateFragment.this.dismiss());

        mCurrentDate = view.findViewById(R.id.currentDate);
        mCalendarView = view.findViewById(R.id.calendar_view);
        mItemPrice = view.findViewById(R.id.item_price);
        mCounter = view.findViewById(R.id.counter);
        mSaveBtn = view.findViewById(R.id.save_btn);

        itemPrice = DetailsActivity.object.getFirstIndividualPrice();
        DisabledDaysCriteria criteria = new DisabledDaysCriteria(Calendar.SATURDAY, Calendar.FRIDAY, DisabledDaysCriteriaType.DAYS_OF_WEEK);
        Set<Long> disabledDaysSet = new HashSet<>();

        for (Integer integer : criteria.getDays()) {
            disabledDaysSet.add(new Long(integer));
        }
        for (Avaliability avaliability : DetailsActivity.object.getAvaliabilities()) {
            SimpleDateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(inputFormatter1.parse(avaliability.getActivityStart()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            disabledDaysSet.remove(calendar.getTimeInMillis());

        }
        Log.i("DATE", "onViewCreated: " + disabledDaysSet.size());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");
        mCalendarView.setDisabledDays(disabledDaysSet);
        mCalendarView.setSelectionManager(new SingleSelectionManager(() -> {

            mCurrentDate.setText(dateFormat.format(mCalendarView.getSelectedDates().get(0).getTime()));
        }));
        if (Cart.getInstance().getStart() != null) {
            mCurrentDate.setText(dateFormat.format(Cart.getInstance().getStart()));
        }
        int sum = Cart.getInstance().getAdultCount() + Cart.getInstance().getChildCount();
        mCounter.setText(String.format("For %d individual", sum));
        mItemPrice.setText(String.format("%s SR", sum * itemPrice));
        mSaveBtn.setOnClickListener(v -> {
            Calendar calendar = mCalendarView.getSelectedDates().get(0);

            Cart.getInstance().setStart(mCalendarView.getSelectedDates().get(0).getTime());
            int count=0;
            List<Avaliability> avaliabilities=new ArrayList<>();
            for (Avaliability avaliability : DetailsActivity.object.getAvaliabilities()) {
                Calendar calendar1 = Calendar.getInstance();
                try {
                    calendar1.setTime(inputFormatter1.parse(avaliability.getActivityStart()));
                    boolean sameDay = calendar.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR);
                    if (sameDay){
                        count++;
                    avaliabilities.add(avaliability);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            if (count==0) {
                Toast.makeText(getActivity(), "Date is not in Avalibalites", Toast.LENGTH_SHORT).show();
            return;
            } else if (count==1)
                Cart.getInstance().setAvaliablityID(avaliabilities.get(0));
             else
                 Cart.getInstance().setAvaliablityID(avaliabilities.get(0));
                 Cart.getInstance().setAvaliabilitiesCount(count);

            listener.onClose(avaliabilities,count);
            dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);

    }


    public interface Listener {
        public void onClose(List<Avaliability> dates,int count);
    }
}

