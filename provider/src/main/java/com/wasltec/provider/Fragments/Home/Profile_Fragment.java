package com.wasltec.provider.Fragments.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.v4.app.Fragment;

import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Price;
import com.wasltec.provider.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.wasltec.provider.Activities.Home.actoinbar;
import static com.wasltec.provider.Activities.Home.is_account_fragment;
import static com.wasltec.provider.Activities.Home.toolbar;

public class Profile_Fragment  extends Fragment {

    private View view;





    @BindView(R.id.progress)
    ImageView progress;
    @BindView(R.id.profile_img)
    CircleImageView profile_img;

    int preset = 3;
    PopupWindow popupWindow;

    private ImageView mProgressDialogDismiss;
    private TextView mProgressDialodTitle;
    private TextView mProgressDialogDesc;
    private ImageView mProgress1;

    private int RESULT_LOAD_IMAGE = 5;
    private Toolbar mToolbar;
    private TextView mEditUserName;
    private TextView mEditUserPhone;
    private TextView mEditEmail;
    private CircleImageView mProfileImg;
    private ImageButton mEditImage;
    private ImageView mProgress;
    private EditText mUserName;
    private EditText mUserPhone;
    private EditText mUserEmail;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        toolbar.setTitle("pfofile");

//        toolbar.setBackgroundColor(R.color.colorAccent);
//        toolbar.setBackgroundColor(R.color.background_transparent);

        view = inflater.inflate(R.layout.fragment_profile_account, container, false);
        is_account_fragment=true;
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar = view.findViewById(R.id.toolbar);
        mEditUserName = view.findViewById(R.id.edit_user_name);
        mEditUserPhone = view.findViewById(R.id.edit_user_phone);
        mEditEmail = view.findViewById(R.id.edit_email);
        mProfileImg = view.findViewById(R.id.profile_img);
        mEditImage = view.findViewById(R.id.edit_image);
        mProgress = view.findViewById(R.id.progress);

        mUserEmail = view.findViewById(R.id.user_email);
        mUserName = view.findViewById(R.id.user_name);
        mUserPhone = view.findViewById(R.id.user_phone);


        setdata();
        clicks();

        return view;
    }

    private void setdata() {
        mUserPhone.setText(Session.getInstance().getPhone());
        mUserName.setText(Session.getInstance().getUserName());
        mUserEmail.setText(Session.getInstance().getEmail());

        Glide.with(getActivity()).load(Session.getInstance().getUserPhoto_Url())
                .apply(RequestOptions.circleCropTransform()
                .placeholder(new ColorDrawable(Color.GRAY)))
                .into(mProfileImg);




//        Picasso.get().load("/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20181029-WA0004.jpg")
//                .placeholder((new ColorDrawable(Color.GRAY)))
//                .error((new ColorDrawable(Color.RED)));



    }

    private void clicks() {
        mEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        mEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("id",Session.getInstance().getId());
                    jsonObject.put("mail",mUserEmail.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(URLManger.getInstance().getEditProfile())
                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Session.getInstance().setEmail(mUserEmail.getText().toString());
                                Session.getInstance().saveToFile(getActivity());
                                Toast.makeText(getActivity(),"mail updated ",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getActivity(),"mail don't updated try again ",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        mEditUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject= new JSONObject();
                try {
//                    jsonObject.put("id",Session.getInstance().getId());
                    jsonObject.put("user_name",mUserName.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(URLManger.getInstance().getEditProfile())
                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Session.getInstance().setUserName(mUserName.getText().toString());
                                Session.getInstance().saveToFile(getActivity());
                                Toast.makeText(getActivity(),"user_name updated ",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getActivity(),"user_name don't updated try again ",Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
        mEditUserPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("id",Session.getInstance().getId());
                    jsonObject.put("mobile",mUserPhone.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(URLManger.getInstance().getEditProfile())
                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Session.getInstance().setPhone(mUserPhone.getText().toString());
                                Session.getInstance().saveToFile(getActivity());
                                Toast.makeText(getActivity(),"mobile updated ",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getActivity(),"mobile don't updated try again ",Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }

    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("pfofile");


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap img = BitmapFactory.decodeStream(imageStream);
                mProfileImg.setImageBitmap(img);
                String s = getPath(getContext(),imageUri);

                JSONObject jsonObject= new JSONObject();
                jsonObject.put("customerphoto64",convert(img));
                jsonObject.put("id",Session.getInstance().getId());

                AndroidNetworking.post(URLManger.getInstance().getEditProfile())
                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                        .addJSONObjectBody(jsonObject)

                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

//                                    Session.getInstance().setUserPhoto_Url(response.getString(s));
//                                    Session.getInstance().saveToFile(getActivity());
                                    Log.d("photo_res", "onResponse: "+response.toString());
                                    Toast.makeText(getActivity(), "response ok "+ response.toString() , Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("photo_res", "onResponse: "+anError.getMessage());

                                Toast.makeText(getActivity(), "response erroe "+ anError.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
//                        .getAsOkHttpResponse(new OkHttpResponseListener() {
//                            @Override
//                            public void onResponse(Response response) {
//                                Session.getInstance().setUserPhoto_Url(String.valueOf(imageUri));
//                                response.message();
//                                Session.getInstance().saveToFile(getActivity());
//                                Log.d("photo_res", "onResponse: "+response.toString());
//                                Toast.makeText(getActivity(), "response ok "+ response.toString() , Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Log.d("photo_res", "onResponse: "+anError.getMessage());
//
//                                Toast.makeText(getActivity(), "response erroe "+ anError.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 20, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }


}

