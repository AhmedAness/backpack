package com.wasltec.backpack.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.Adapters.OurActivityAdapter;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.CustomerActivitiesModel;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MyActivites extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    public static class PlaceholderFragment extends Fragment {

        List<CustomerActivitiesModel> past_items = new ArrayList<>();
        List<CustomerActivitiesModel> future_items = new ArrayList<>();
        List<CustomerActivitiesModel> items = new ArrayList<>();

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private RecyclerView mItemsList;
        private LinearLayout mNotes;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_activites, container, false);

            return rootView;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mItemsList = view.findViewById(R.id.items_list);
            mNotes = view.findViewById(R.id.notes);

            int choice = getArguments().getInt(ARG_SECTION_NUMBER, 0);

            //get my activities
            AndroidNetworking.get(URLManager.getInstance().getCustomerActivities())
                    .addHeaders("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiI1IiwiZXhwIjoxNTM2MjM1MDQwLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjYzOTM5LyIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NjM5MzkvIn0.7EbPFC3aM3LJyEtQjtIySXcDHGp7rmz8v8xF9o5dQpw")
//                    .addHeaders("Authorization", "bearer "+ Session.getInstance(getActivity()).getToken())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Gson gson = new Gson();

                                JSONArray array = response.getJSONArray("futureActivities");
                                Type type = new TypeToken<List<CustomerActivitiesModel>>() {}.getType();
                                future_items = gson.fromJson(String.valueOf(array), type);

                                array = response.getJSONArray("pastActivities");
                                type = new TypeToken<List<CustomerActivitiesModel>>() {}.getType();
                                past_items = gson.fromJson(String.valueOf(array), type);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (choice != 1) {

                                items = past_items;
                            } else {
                                items = future_items;
                            }
                            if (items.size() == 0) {
                                mNotes.setVisibility(View.VISIBLE);
                                mItemsList.setVisibility(View.GONE);
                            } else {
                                mNotes.setVisibility(View.GONE);
                                mItemsList.setVisibility(View.VISIBLE);
                                OurActivityAdapter ourActivityAdapter = new OurActivityAdapter(items);
                                mItemsList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                mItemsList.setAdapter(ourActivityAdapter);
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("network", "onError: " + anError.getMessage());
                        }
                    });


        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Your Future";
            } else
                return "Your Past";

        }
    }
}
