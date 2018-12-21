package com.wasltec.backpack.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.Adapters.AddonesAdapter;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.ActivityDetails.ActivityOption;
import com.wasltec.backpack.models.ActivityDetails.CustomerActivitiesDetailsModel;
import com.wasltec.backpack.models.ActivityPhoto;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    private TextView mItemName;
    private TextView mItemPrice;
    private RatingBar mItemRating;
    private TextView mRatingCount;
    private LinearLayout mT;
    private CircleImageView mImageView;
    private TextView mLocation;
    private TextView mLocationCountry;
    private TextView mActivityOwner;
    private GridLayout mTypesContainer;
    private ReadMoreTextView mItemDescription;
    private RecyclerView mActivityFeature;
    private TextView mActivityPeriod;
    private MapView mMap;
    private CircleImageView mReviewProfilePic;
    private TextView mReviewName;
    private TextView mReviewDate;
    private RatingBar mReviewRating;
    private TextView mReviewDesc;
    private TextView mViewReviews;
    private TextView mTripRules;
    private TextView mImportantNotes;
    private TextView mAddOnes;
    private TextView mSimilarActivitiesNone;
    private RecyclerView mSimilarActivities;
    private TextView mItemPrice2;
    private RatingBar mItemRating2;
    private TextView mRatingCount2;
    private LinearLayout mT2;
    private TextView mCheckOut;
    public static ActivityDetail object;
    private ImageView mActivityImg;
    private String MAP_VIEW_BUNDLE_KEY = "m";
    Toolbar toolbar;
    Bundle tmp_savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tmp_savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_details);
        toolbar = findViewById(R.id.toolbar);
        mMap = findViewById(R.id.map);

        mMap.getMapAsync(googleMap -> {
//            LatLng sydney = new LatLng(object.getPlace().getLan(), object.getPlace().getLat());
//            LatLng ny = new LatLng(object.getActivityLang(), object.getActivityLat());
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
            //Move the camera to the user's location and zoom in!
            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(new LatLng(34,34));
//            markerOptions.title(object.getTitle());

//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( object.getActivityLat(), object.getActivityLang()), 10.0f));
//            googleMap.addMarker(markerOptions);
        });
        Bundle mapViewBundle = null;
        if (tmp_savedInstanceState != null) {
            mapViewBundle = tmp_savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mMap.onCreate(mapViewBundle);

        get_data();



//        object = DataManager.getInstance().getItem(getIntent().getIntExtra("object", 0));


    }

    private void setdata() {

        toolbar.setTitle(object.getTitle());

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItemName = findViewById(R.id.item_name);
        mItemPrice = findViewById(R.id.item_price);
        mItemRating = findViewById(R.id.item_rating);
        mRatingCount = findViewById(R.id.rating_count);
        mT = findViewById(R.id.t);
        mImageView = findViewById(R.id.orgination_img);
        mLocation = findViewById(R.id.location);
        mLocationCountry = findViewById(R.id.location_country);
        mActivityOwner = findViewById(R.id.activity_owner);
        mTypesContainer = findViewById(R.id.types_container);
        mItemDescription = findViewById(R.id.item_description);
        mActivityFeature = findViewById(R.id.activity_feature);
        mActivityPeriod = findViewById(R.id.activity_period);
        mReviewProfilePic = findViewById(R.id.review_profile_pic);
        mReviewName = findViewById(R.id.review_name);
        mReviewDate = findViewById(R.id.review_date);
        mReviewRating = findViewById(R.id.review_rating);
        mReviewDesc = findViewById(R.id.review_desc);
        mViewReviews = findViewById(R.id.view_reviews);
        mTripRules = findViewById(R.id.trip_rules);
        mImportantNotes = findViewById(R.id.important_notes);
        mAddOnes = findViewById(R.id.add_ones);
        mSimilarActivitiesNone = findViewById(R.id.Similar_activities_none);
        mSimilarActivities = findViewById(R.id.Similar_activities);
        mItemPrice2 = findViewById(R.id.item_price2);
        mItemRating2 = findViewById(R.id.item_rating2);
        mRatingCount2 = findViewById(R.id.rating_count2);
        mT2 = findViewById(R.id.t2);
        mCheckOut = findViewById(R.id.check_out);

        mTripRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ReviewRulesActivity.class);

                v.getContext().startActivity(i);

            }
        });
        mImportantNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DetailsActivity.this); // Context, this, etc.
                View view = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.msg_dialog, null, false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textView = view.findViewById(R.id.msg);
                textView.setText(object.getRequirements());
                view.findViewById(R.id.dismiss).setOnClickListener(v2 -> {
                    dialog.dismiss();
                });
                dialog.show();
            }
        });
        mCheckOut.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), Details2Activity.class);
            i.putExtra("object", getIntent().getIntExtra("object", 0));
            v.getContext().startActivity(i);

        });

        mActivityImg = findViewById(R.id.activity_img);

        for (ActivityPhoto activityPhoto : object.getActivityPhotos()) {
            if (activityPhoto.getCoverPhoto())
                Glide.with(this).load(activityPhoto.getUrl()).into(mActivityImg);

        }
//        Glide.with(this).load(object.getCover()).into(mActivityImg);
        mItemName.setText(object.getTitle());
        mItemPrice.setText(String.format(getString(R.string.price_holder), object.getFirstIndividualPrice()));
        mItemPrice2.setText(String.format(getString(R.string.price_holder), object.getFirstIndividualPrice()));
        mRatingCount.setText(String.format(getString(R.string.rating_num_holder), object.getRate()));
        mRatingCount2.setText(String.format(getString(R.string.rating_num_holder), object.getRate()));
        if (object.getReviews().size()>0) {
            mReviewDesc.setText(object.getReviews().get(0).getReview());
            mReviewDate.setText(object.getReviews().get(0).getDate_trim(object.getReviews().get(0).getDate()));
            mReviewRating.setRating(object.getReviews().get(0).getRate());
            Glide.with(this).load(object.getReviews().get(0).getUserPhotoUrl()).apply(new RequestOptions()
                    .placeholder(new ColorDrawable(Color.GRAY))).into(mReviewProfilePic);

            mReviewName.setText(object.getReviews().get(0).getUserName());
        }
        mItemDescription.setText(object.getDescription());

        mActivityPeriod.setText(String.format(getString(R.string.activity_period_holder), "" + object.getActivityLang()));
        Glide.with(this).load(object.getProvider().getUserPhotoUrl()).apply(new RequestOptions()
                .placeholder(new ColorDrawable(Color.GRAY))).into(mImageView);
        mItemRating.setRating(object.getRate());

        mItemRating2.setRating(object.getRate());
        mLocation.setText(object.getActivityLocation());
//        mLocationCountry.setText(object.getLocationCountry());
        mActivityOwner.setText(object.getProvider().getUserName());
        mActivityFeature.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mActivityFeature.setAdapter(new AddonesAdapter(object.getActivityAddOns()));

        mViewReviews.setText(String.format(getString(R.string.view_all_reviews_holder), object.getReviews().size()));

//        Log.v("Detail", object.getDate().toString());
        mSimilarActivities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        mSimilarActivities.setAdapter(new SimilarAdapter(object.getSimilarAcitivity()));


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (ActivityOption activityOption : object.getActivityOption()) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = Glide
                                .with(DetailsActivity.this)
                                .asBitmap()
                                .load(activityOption.getIcon())
                                .submit()
                                .get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    TextView textView = new TextView(DetailsActivity.this, null,
                            android.R.style.TextAppearance_Large);
                    textView.setText(activityOption.getName());
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }
                    textView.setCompoundDrawables(drawable, null, null, null);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.setGravity(Gravity.FILL);
                    layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp),
                            (int) getResources().getDimension(R.dimen._5sdp));
                    textView.setLayoutParams(layoutParams);

                    GridLayout.spec(GridLayout.UNDEFINED);
                    textView.setId(View.generateViewId());
                    textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));

                    mTypesContainer.addView(textView);
                }

            }
        });

//        if (object.isFamily()) {
//            TextView textView = new TextView(this, null, android.R.style.TextAppearance_Large);
//            textView.setText(R.string.family_frendly_txt);
//
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.users);
//            if (drawable != null) {
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            }
//
//            textView.setCompoundDrawables(drawable, null, null, null);
//            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
//            layoutParams.setGravity(Gravity.FILL);
//            layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
//            textView.setLayoutParams(layoutParams);
//
//            GridLayout.spec(GridLayout.UNDEFINED);
//            textView.setId(View.generateViewId());
//            textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
//
//            mTypesContainer.addView(textView);
//        }
//        if (object.isGroup()) {
//            TextView textView = new TextView(this, null, android.R.style.TextAppearance_Large);
//            textView.setText(R.string.group_activity);
//            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
//            layoutParams.setGravity(Gravity.FILL);
//            GridLayout.spec(GridLayout.UNDEFINED);
//            layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
//            textView.setLayoutParams(layoutParams);
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.group3);
//            if (drawable != null) {
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            }
//
//            textView.setCompoundDrawables(drawable, null, null, null);
//            textView.setId(View.generateViewId());
//            textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
//
//            mTypesContainer.addView(textView);
//        }
//        if (object.isMaleFemale()) {
//            TextView textView = new TextView(this, null, android.R.style.TextAppearance_Large);
//            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
//            layoutParams.setGravity(Gravity.FILL);
//            textView.setText(R.string.males_and_females_txt);
//
//            textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.user);
//            if (drawable != null) {
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            }
//            layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
//            textView.setLayoutParams(layoutParams);
//
//            textView.setCompoundDrawables(drawable, null, null, null);
//            textView.setId(View.generateViewId());
//
//            mTypesContainer.addView(textView);
//        }
//        if (object.isMaleFemale()) {
//            TextView textView = new TextView(this, null, android.R.style.TextAppearance_Large);
//            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
//            layoutParams.setGravity(Gravity.FILL);
//            textView.setText(R.string.males_and_females_txt);
//
//            textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.user);
//            if (drawable != null) {
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            }
//            layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
//            textView.setLayoutParams(layoutParams);
//
//            textView.setCompoundDrawables(drawable, null, null, null);
//            textView.setId(View.generateViewId());
//
//            mTypesContainer.addView(textView);
//        }
        LayerDrawable stars = (LayerDrawable) mItemRating2.getProgressDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stars.setTint(Color.WHITE);
        } else
            stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mItemRating2.setProgressDrawable(stars);

    }

    private void get_data() {
//        CustomerActivitiesDetailsModel

        AndroidNetworking.get(URLManager.getInstance().
                getCustomerActivitiesDetails("" + getIntent().getIntExtra("object", 0)))
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        Type type = new TypeToken<CustomerActivitiesDetailsModel>() {
                        }.getType();

                        CustomerActivitiesDetailsModel customerActivitiesDetailsModel = gson.fromJson(String.valueOf(response), type);
                        if (customerActivitiesDetailsModel.getActivityDetails().size() > 0)
                            object = customerActivitiesDetailsModel.getActivityDetails().get(0);
                        DataManager.getInstance().setTrip(object);
                        setdata();

                        SetMap();

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    private void SetMap() {
        mMap.getMapAsync(googleMap -> {
//            LatLng sydney = new LatLng(object.getPlace().getLan(), object.getPlace().getLat());
//            LatLng ny = new LatLng(object.getActivityLang(), object.getActivityLat());
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
            //Move the camera to the user's location and zoom in!
            // Creating a marker
            try {
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(new LatLng(object.getActivityLat(), object.getActivityLang()));
                markerOptions.title(object.getTitle());

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( object.getActivityLat(), object.getActivityLang()), 10.0f));
                googleMap.addMarker(markerOptions);

            }catch (Exception e){
//                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
//                markerOptions.position(new LatLng(34,34));
//                markerOptions.title(object.getTitle());

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( 34,34), 2.0f));
//                googleMap.addMarker(markerOptions);
            }

        });
        Bundle mapViewBundle = null;
        if (tmp_savedInstanceState != null) {
            mapViewBundle = tmp_savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mMap.onCreate(mapViewBundle);
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
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMap.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favourite) {
            Toast.makeText(this, "Added to favourite", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.share) {

            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        } else
            super.onBackPressed();

        return true;
    }
}
