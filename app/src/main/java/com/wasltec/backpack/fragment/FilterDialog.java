package com.wasltec.backpack.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.utils.Checkbox2;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.SimpleDateFormat;
import java.util.List;

public class FilterDialog extends DialogFragment implements Checkbox2.Listener {

    private SearchView searchView;
    private Checkbox2 mFemaleCheck;
    private Checkbox2 mMaleCheck;
    private Checkbox2 mGroupCheck;
    private Checkbox2 mFemalesAndMaleCheck;
    private RangeSeekBar mPriceRange;
    private CalendarView mCalendarView;
    private Button mFilter;
    private Listener listener;
    private Toolbar mToolbar;
    private Checkbox2 mFamilyCheck;
    private Checkbox2 mDate;
    private TextView mStartMonth;
    private TextView mStartYear;
    private TextView mEndMonth;
    private TextView mEndYear;

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
        return inflater.inflate(R.layout.activity_filter, container, false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.dialog_theme);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarView = view.findViewById(R.id.calendar_view);
        mFemaleCheck = view.findViewById(R.id.femaleCheck);
        mMaleCheck = view.findViewById(R.id.maleCheck);
        mGroupCheck = view.findViewById(R.id.groupCheck);
        mFamilyCheck = view.findViewById(R.id.familyCheck);
        mFemalesAndMaleCheck = view.findViewById(R.id.females_and_maleCheck);
        mPriceRange = view.findViewById(R.id.price_range);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(v -> dismiss());
        DataManager dataManager = DataManager.getInstance();
        mDate = view.findViewById(R.id.date);
        mStartMonth = view.findViewById(R.id.start_month);
        mStartYear = view.findViewById(R.id.start_year);
        mEndMonth = view.findViewById(R.id.end_month);
        mEndYear = view.findViewById(R.id.end_year);

        mFemaleCheck.setChecked(dataManager.isFemales());
        mMaleCheck.setChecked(dataManager.isMale());
        mGroupCheck.setChecked(dataManager.isGroups());
        mFemalesAndMaleCheck.setChecked(dataManager.isMaleAndFemale());
        mFamilyCheck.setChecked(dataManager.isFamilies());


        mFemaleCheck.onCheckboxChange(this);
        mMaleCheck.onCheckboxChange(this);
        mGroupCheck.onCheckboxChange(this);
        mFemalesAndMaleCheck.onCheckboxChange(this);
        mFamilyCheck.onCheckboxChange(this::onChecked);


        mFilter = view.findViewById(R.id.filter);

        mFilter.setText(String.format(getString(R.string.fiter_btn_holder), DataManager.getInstance().getItems().size()));
        mStartMonth.setEnabled(false);
        mStartYear.setEnabled(false);
        mEndMonth.setEnabled(false);
        mEndYear.setEnabled(false);
        mCalendarView.setVisibility(View.GONE);
        mFilter.setOnClickListener(v -> {
            dismiss();
            listener.onResult(DataManager.getInstance().filter());
        });
        mPriceRange.setOnRangeSeekBarChangeListener((RangeSeekBar.OnRangeSeekBarChangeListener<Integer>) (bar, minValue, maxValue) -> {
            DataManager.getInstance().setMaxPrice(maxValue);
            DataManager.getInstance().setMinPrice(minValue);
            mFilter.setText(String.format(getString(R.string.fiter_btn_holder), DataManager.getInstance().filter().size()));
            Log.v("Filter", minValue + " " + maxValue);

        });
        mCalendarView.setSelectionManager(new RangeSelectionManager(() -> {
            Log.v("ddd", mCalendarView.getSelectedDates().size() + " ");
                if (mCalendarView.getSelectedDates() != null && mCalendarView.getSelectedDates().size() > 0) {

                    DataManager.getInstance().setStart(mCalendarView.getSelectedDates().get(0).getTime());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM");
                    mStartMonth.setText(dateFormat.format(mCalendarView.getSelectedDates().get(0).getTime()));
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy");
                    mStartYear.setText(dateFormat2.format(mCalendarView.getSelectedDates().get(0).getTime()));


                    if (mCalendarView.getSelectedDates().size() > 1) {
                        DataManager.getInstance().setEnd(mCalendarView.getSelectedDates().get(mCalendarView.getSelectedDays().size() - 1).getTime());
                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd MMM");
                        mEndMonth.setText(dateFormat.format(mCalendarView.getSelectedDates().get(mCalendarView.getSelectedDates().size() - 1).getTime()));
                        SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy");
                        mEndYear.setText(dateFormat2.format(mCalendarView.getSelectedDates().get(mCalendarView.getSelectedDates().size() - 1).getTime()));

                    }
                    {
                        mFilter.setText(String.format(getString(R.string.fiter_btn_holder), DataManager.getInstance().filter().size()));

                    }
                }

        }));
        mDate.onCheckboxChange((id, isChecked) -> {
            if (!isChecked) {
                mStartMonth.setEnabled(false);
                mStartYear.setEnabled(false);
                mEndMonth.setEnabled(false);
                mEndYear.setEnabled(false);
                mStartMonth.setText("");
                mStartYear.setText("");
                mEndMonth.setText("");
                mEndYear.setText("");
                mCalendarView.clearSelections();
                mCalendarView.setVisibility(View.GONE);
                DataManager.getInstance().setStart(null);
                mFilter.setText(String.format(getString(R.string.fiter_btn_holder), DataManager.getInstance().filter().size()));

            } else {

                mStartMonth.setEnabled(true);
                mStartYear.setEnabled(true);
                mEndMonth.setEnabled(true);
                mCalendarView.setEnabled(true);

                mCalendarView.setVisibility(View.VISIBLE);


            }
        });
    }



    @Override
    public void onChecked(int id, boolean isChecked) {
        if (id == R.id.femaleCheck) {
            DataManager.getInstance().setFemales(isChecked);
            Log.v(FilterDialog.class.getName(), "female Check");
        }
        if (id == R.id.maleCheck) {
            DataManager.getInstance().setMale(isChecked);

            Log.v(FilterDialog.class.getName(), "male Check");
        } else if (id == R.id.females_and_maleCheck) {
            DataManager.getInstance().setMaleAndFemale(isChecked);

            Log.v(FilterDialog.class.getName(), "female Male Check");
        } else if (id == R.id.groupCheck) {
            DataManager.getInstance().setGroups(isChecked);

            Log.v(FilterDialog.class.getName(), "Group Check");
        } else if (id == R.id.familyCheck) {
            DataManager.getInstance().setFamilies(isChecked);

            Log.v(FilterDialog.class.getName(), "Family Check");
        }


        mFilter.setText(String.format(getString(R.string.fiter_btn_holder), DataManager.getInstance().filter().size()));

    }

    public interface Listener {
        public void onResult(List<ActivityTrip> trips);
    }
}