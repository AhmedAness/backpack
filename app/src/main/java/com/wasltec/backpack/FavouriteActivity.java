package com.wasltec.backpack;

import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.wasltec.backpack.Adapters.FavItemAdapter;
import com.wasltec.backpack.Adapters.FavouriteAdapter;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.CustomerFavoriteActivitiesModel;
import com.wasltec.backpack.models.FavListItem;
import com.wasltec.backpack.utils.SharedPreferencesManager;
import com.wasltec.backpack.utils.URLManager;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

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

            View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

            RecyclerView list = rootView.findViewById(R.id.list_favourite);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));

            if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 0) {
                AndroidNetworking.get(URLManager.getInstance().getListCustomerFavoriteActivities(""+Session.getInstance(getActivity()).getUser().getId()))
                        .addHeaders("Authorization", "bearer "+ Session.getInstance(getActivity()).getToken())
                        .build()
                        .getAsObjectList(CustomerFavoriteActivitiesModel.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        List<CustomerFavoriteActivitiesModel> f = (List<CustomerFavoriteActivitiesModel>) response;
                        list.setAdapter(new FavouriteAdapter(f));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("network", "onError: "+anError.getMessage());

                    }
                });
//                List<ActivityTrip> trips = DataManager.getInstance().getActivities().subList(0, 2);
//                list.setAdapter(new FavouriteAdapter(trips));
            } else {
//            list.setAdapter(new FavItemAdapter());
                List<FavListItem> items = new ArrayList<>();
                items.add(new FavListItem("To do with family", 3));
                items.add(new FavListItem("with friends", 5));
                items.add(new FavListItem("for Riyadh Trip", 5));

                list.setAdapter(new FavItemAdapter(items));
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        PlaceholderFragment favourites = PlaceholderFragment.newInstance(0);
        PlaceholderFragment list = PlaceholderFragment.newInstance(1);

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? favourites : list;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
