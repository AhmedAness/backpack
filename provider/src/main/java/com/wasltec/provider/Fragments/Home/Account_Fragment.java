package com.wasltec.provider.Fragments.Home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasltec.provider.Activities.Login_Register;
import com.wasltec.provider.Activities.Splash_Screen;
import com.wasltec.provider.Activities.WebViewActivity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Session;

import org.json.JSONException;

import static com.wasltec.provider.Activities.Home.actoinbar;
import static com.wasltec.provider.Activities.Home.toolbar;

public class Account_Fragment extends Fragment {

    private View view;
    private LinearLayout mProfile;
    private LinearLayout userandrole;
    private LinearLayout activities_settings;
    private LinearLayout mOrganizatoinProfile;
    private TextView mNotifications;
    private TextView mHelp;
    private TextView mFeedback;
    private TextView mlogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        actoinbar.setDisplayHomeAsUpEnabled(true);
        actoinbar.setDisplayShowHomeEnabled(true);
        toolbar.setTitle(Session.getInstance().getFirst_name()+" "+Session.getInstance().getLast_name());
        view =  inflater.inflate(R.layout.account_fragment_layout, container, false);
        return view;
    }

    private void clicks() {
        if (Session.getInstance().isProvider()){
            mProfile.setVisibility(View.GONE);
        }else
            mOrganizatoinProfile.setVisibility(View.GONE);
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_Fragment profile_fragment = new Profile_Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, profile_fragment).commit();

            }
        });
        mOrganizatoinProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_Fragment profile_fragment = new Profile_Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, profile_fragment).commit();
            }
        });

        mFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("sgin_up", URLManger.getInstance().getFeedback());
                startActivity(intent);
            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("sgin_up", URLManger.getInstance().getHelp());
                startActivity(intent);
            }
        });
        activities_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("sgin_up", URLManger.getInstance().getActivities_Settings());
                startActivity(intent);
            }
        });
        userandrole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("sgin_up", URLManger.getInstance().getUsers_and_Roles());
                startActivity(intent);
            }
        });
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Session.getInstance().logout(getActivity());

                    SharedPreferencesManager.getInstance(getActivity()).setToken("");

                Intent intent= new Intent(getActivity(),Splash_Screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();


            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_tab_menu, menu);
        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);
        menu.findItem(R.id.lang).setVisible(true);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(Session.getInstance().getUserName());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProfile = view.findViewById(R.id.profile);
        activities_settings = view.findViewById(R.id.activities_settings);
        userandrole = view.findViewById(R.id.userandrole);
        mOrganizatoinProfile = view.findViewById(R.id.organization_profile);
        mNotifications = view.findViewById(R.id.notifications);
        mHelp = view.findViewById(R.id.help);
        mFeedback = view.findViewById(R.id.feedback);
        mlogout = view.findViewById(R.id.logout);
        clicks();
    }
}