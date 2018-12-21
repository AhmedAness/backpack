package com.wasltec.provider.Fragments.Add_new_Activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.Activity_details;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.Adopters.ActivityPhoto_adobter;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.ActivityDetailsReturnObj;
import com.wasltec.provider.model.ActivityPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static com.wasltec.provider.Activities.Add_new_activity.ActivityID;
import static com.wasltec.provider.Activities.Add_new_activity.add_new_toolbar;
import static com.wasltec.provider.Activities.Add_new_activity.mode;

public class Step2 extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_MULTIPLE = 5;
    private View view;
    private FloatingActionButton mFloatingActionButton;
    ArrayList<Uri> mArrayUri;
    private RecyclerView mImages;
    private ArrayList<Uri> imagesUriArrayList;
    ArrayList<ActivityPhoto> photos;
    ActivityPhoto_adobter img_adopter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.photos));
        imagesUriArrayList = new ArrayList<>();
        setHasOptionsMenu(true);
        Add_new_activity.dialog.hide();
        mArrayUri = new ArrayList<>();
        photos = new ArrayList<>();

        view = inflater.inflate(R.layout.add_activity_step2, container, false);
        initView(view);
        if (mode==1){
            photos= new ArrayList<>();
            img_adopter = new ActivityPhoto_adobter(getContext(), photos, mode);
            mImages.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mImages.setAdapter(img_adopter);

        }else {
            Add_new_activity.dialog.show();


            AndroidNetworking.get(URLManger.getInstance().getGetActivityDetails(String.valueOf(ActivityID)))
                    .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Add_new_activity.dialog.hide();
                            Gson gson = new Gson();
                            Type listType = new TypeToken<ActivityDetailsReturnObj>() {
                            }.getType();
                            ActivityDetailsReturnObj activityDetails;
                            try {
                                activityDetails = gson.fromJson(response.get(0).toString(), listType);




                                photos= activityDetails.getActivityPhotos();
                                img_adopter = new ActivityPhoto_adobter(getContext(), photos, mode);
                                mImages.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                                mImages.setAdapter(img_adopter);

                            } catch (JSONException e) {
                                activityDetails = new ActivityDetailsReturnObj();
                                e.printStackTrace();
                            } catch (Exception e) {
                                activityDetails = new ActivityDetailsReturnObj();
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            Add_new_activity.dialog.hide();
                            Log.d(TAG, "onError: " + anError.getMessage());
                        }
                    });





        }

        return view;
    }

    private void initView(@NonNull final View itemView) {
        mFloatingActionButton = itemView.findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(this);
        mImages = itemView.findViewById(R.id.images);
    }

    public void valid() {


        ArrayList<ActivityPhoto> tmp = ((ActivityPhoto_adobter) mImages.getAdapter()).getActivityPhotos();


        boolean hascover=false;
        for (ActivityPhoto activityPhoto : tmp) {
            if (activityPhoto.getCoverPhoto()) {
                hascover = true;
            }
        }

        if (tmp.size()<1){
//            Toast.makeText(getActivity(),"you must load at least one image",Toast.LENGTH_LONG).show();
            Add_new_activity.showDialog( R.string.photos_less_warning,R.string.warning);
            Add_new_activity.dialog.hide();
        }else if (tmp.size()>6){
            Add_new_activity.showDialog( R.string.photos_more_warning,R.string.warning);
            Add_new_activity.dialog.hide();

        }else if(!hascover)
        {
            Add_new_activity.showDialog( R.string.no_cover_photos_warning,R.string.warning);
            Add_new_activity.dialog.hide();
        }
        else {
            Add_new_activity.dialog.show();
            JSONObject jsonObject = new JSONObject();
            try {
                JSONArray jsonElements = new JSONArray();





                for (ActivityPhoto activityPhoto : tmp) {
                    JSONObject jsonObject1 = new JSONObject();
                    if (activityPhoto.getUri()!=null)
                        jsonObject1.put("base64Img", convert(activityPhoto.getUri(), getActivity()));
                    else
                        jsonObject1.put("base64Img",getbase64(activityPhoto.getBitmap()));

                    jsonObject1.put("cover_photo", activityPhoto.getCoverPhoto());
                    jsonElements.put(jsonObject1);


                }
                jsonObject.put("Activity_Photos", jsonElements);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            AndroidNetworking.post(URLManger.getInstance().setStepTwo(ActivityID, mode))
                    .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(getActivity()).getToken())
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Add_new_activity.dialog.hide();
                            if (mode == 2) {
                                Add_new_activity.step_number = 1;
                                mode = 1;
                                getActivity().finish();
                            } else {
                                if (getActivity() != null) {
                                    Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step3));
                                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, Add_new_activity.step3).commit();
                                    Add_new_activity.step_number = 3;

                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                            Add_new_activity.dialog.hide();

                            Toast.makeText(getContext(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    });


        }



    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                Intent intent = new Intent();
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_MULTIPLE) {
                ClipData clipData = data.getClipData();

                if (clipData == null) {
                    Uri uri2 = data.getData();
                    imagesUriArrayList.clear();
                    imagesUriArrayList.add(uri2);
                    for (Uri uri : imagesUriArrayList) {
                        ActivityPhoto activityPhoto = new ActivityPhoto();
                        activityPhoto.setUrl(uri.toString());
                        activityPhoto.setUri(uri);
                        photos.add(activityPhoto);

                    }
                    img_adopter = new ActivityPhoto_adobter(getContext(), photos, mode);
                    mImages.setAdapter(img_adopter);

                } else {
                    Log.e("++data", "" + data.getClipData().getItemCount());// Get count of image here.

                    Log.e("++count", "" + data.getClipData().getItemCount());


                    imagesUriArrayList.clear();

                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        imagesUriArrayList.add(data.getClipData().getItemAt(i).getUri());
                    }
                    Log.e("SIZE", imagesUriArrayList.size() + "");
                    for (Uri uri : imagesUriArrayList) {
                        ActivityPhoto activityPhoto = new ActivityPhoto();
                        activityPhoto.setUrl(uri.toString());
                        activityPhoto.setUri(uri);
                        photos.add(activityPhoto);

                    }
                    img_adopter = new ActivityPhoto_adobter(getContext(), photos, mode);

                    mImages.setAdapter(img_adopter);

                }
            }

        }
    }

    public static String convert(Uri imagePath, Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Drawable yourDrawable = Drawable.createFromStream(inputStream, imagePath.toString());
        BitmapDrawable drawable = (BitmapDrawable) yourDrawable;
        Bitmap bitmap = drawable.getBitmap();
        return encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
    }

    private static String getbase64(Bitmap bitmap) {
        return encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
    }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }






    @Override
    public void onResume() {
        super.onResume();
        add_new_toolbar.setTitle(getActivity().getResources().getString(R.string.photos));

    }
}
