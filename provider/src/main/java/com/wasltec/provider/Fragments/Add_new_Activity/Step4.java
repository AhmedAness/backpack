package com.wasltec.provider.Fragments.Add_new_Activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.MapsActivity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import static android.app.Activity.RESULT_OK;
import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.activityDetails;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;

public class Step4 extends Fragment {

    private View view;
    private MapView mMap;
    private GoogleMap mMap1;
    private GoogleMap mgMap2;
    private MapView mMap2;
    private String MAP_VIEW_BUNDLE_KEY = "m";
    private TextView mLocationName;
    private TextView mLocationMeeting;
    Marker mActivity;
    Marker mMeeting;
    private ImageView mMapBtn;
    private ImageView mMeetingBtn;
    private CheckBox mSameLocation;
    private LinearLayout mSecondMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.location));
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.add_activity_step4, container, false);
        initView(view);
        Add_new_activity.dialog.hide();
        mMapBtn.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), MapsActivity.class), 0));
        mMeetingBtn.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), MapsActivity.class), 1));

        mMap.getMapAsync(googleMap -> {
//            LatLng sydney = new LatLng(object.getPlace().getLan(), object.getPlace().getLat());

            LatLng ny;

            if (mode == 2) {

                ny = new LatLng(activityDetails.getActivity_Lat(), activityDetails.getActivity_Lang());
            } else {
                ny = new LatLng(24.774265, 46.738586);
            }
            mMap1 = googleMap;
            MarkerOptions markerOptions = new MarkerOptions().position(ny).title("activity location");
            mActivity = googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny, 2));
            googleMap.getUiSettings().setAllGesturesEnabled(true);
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//
//            }
            mMap1.setOnMapClickListener(latLng -> {
//                mLocationName.setText(getCountryName(getContext(), latLng));
//                m.setPosition(latLng);
//                ny[0] =latLng;
//                m.setPosition(latLng);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny[0],4));
                startActivityForResult(new Intent(getActivity(), MapsActivity.class),0);

            });


        });
        mMap2.getMapAsync(googleMap -> {
            LatLng ny;

            if(mode==2)
            {

                ny = new LatLng(activityDetails.getActivity_Lat(), activityDetails.getActivity_Lang());
            }
            else
            {
                ny = new LatLng(24.774265, 46.738586);
            }
            MarkerOptions markerOptions = new MarkerOptions().position(ny).title("meeting location");
            mMeeting = googleMap.addMarker(markerOptions);
            googleMap.getUiSettings().setAllGesturesEnabled(true);

            mgMap2 = googleMap;
            mgMap2.setOnMapClickListener(latLng -> {
//                mLocationMeeting.setText(getCountryName(getContext(), latLng));
//                ny[0] =latLng;
//                m.setPosition(latLng);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny[0],4));
                startActivityForResult(new Intent(getActivity(), MapsActivity.class),1);

            });
        });

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mMap.onCreate(mapViewBundle);
        mMap2.onCreate(mapViewBundle);
        mSameLocation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                mSecondMap.setVisibility(View.GONE);
                mMeeting.setPosition(mMap1.getCameraPosition().target);
                mLocationMeeting.setText(getCountryName(getContext(), mMap1.getCameraPosition().target));
                mgMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(mMap1.getCameraPosition().target,3));
            }
            else{
                mSecondMap.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void initView(@NonNull final View itemView) {
        mMap = (MapView) itemView.findViewById(R.id.map);
        mMap2 = (MapView) itemView.findViewById(R.id.map2);
        mLocationName = view.findViewById(R.id.location_name);
        mLocationMeeting = view.findViewById(R.id.location_meeting);
        mMapBtn = view.findViewById(R.id.map_btn);
        mMeetingBtn = view.findViewById(R.id.meeting_btn);
        mSameLocation = view.findViewById(R.id.same_location);
        mSecondMap = view.findViewById(R.id.second_map);




    }

    public void valid() {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("activity_Location", getCountryName(getActivity(), mMap1.getCameraPosition().target));
            jsonObject.put("meeting_Location", getCountryName(getActivity(), mgMap2.getCameraPosition().target));

            jsonObject.put("activity_Lat", mMap1.getCameraPosition().target.latitude);
            jsonObject.put("activity_Lang", mMap1.getCameraPosition().target.longitude);

            jsonObject.put("meeting_Lat", mgMap2.getCameraPosition().target.latitude);
            jsonObject.put("meeting_Lang", mgMap2.getCameraPosition().target.longitude);

            jsonObject.put("LocationFlag", true);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URLManger.getInstance().setStepFour(ActivityID, mode))
                .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mode == 2) {
                            Add_new_activity.step_number = 1;
                            mode = 1;
                            getActivity().finish();
                        } else {
                            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step5));
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step5).commit();
                            Add_new_activity.step_number = 5;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Add_new_activity.dialog.hide();

                    }
                });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.location));
        mMap.onResume();
        mMap2.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMap.onStart();
        mMap2.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMap.onStop();
        mMap2.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0){
            if (resultCode==RESULT_OK){
                double lat=data.getDoubleExtra("lat",0.0);
                double lng=data.getDoubleExtra("lng",0.0);
                LatLng latLng=new LatLng(lat,lng);
                mActivity.setPosition(latLng);
                mLocationName.setText(getCountryName(getContext(), latLng));
                mMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,3));

                Toast.makeText(getActivity(),lat+" "+lng,Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                double lat=data.getDoubleExtra("lat",0.0);
                double lng=data.getDoubleExtra("lng",0.0);
                LatLng latLng=new LatLng(lat,lng);
                mMeeting.setPosition(latLng);
                mLocationMeeting.setText(getCountryName(getContext(), latLng));
                mgMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,3));
                Toast.makeText(getActivity(),lat+" "+lng,Toast.LENGTH_SHORT).show();

            }
        }
    }

    public static String getCountryName(Context context, LatLng lati) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lati.latitude, lati.longitude, 1);
            Address result;

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
            return null;
        } catch (IOException ignored) {
            //do something
        }
        return "";
    }

}