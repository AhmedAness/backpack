package com.wasltec.backpack.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.activities.LoginActivity;
import com.wasltec.backpack.activities.VerifyActivity;
import com.wasltec.backpack.models.User;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class UserDataFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private TextView mRegCode;
    private Button mSaveBtn;
    private Button mFbBtn;
    private Button mGoogleBtn;
    private Bitmap img;
    String img2 = null;
private static String TAG=UserDataFragment.class.getName();
    private CircleImageView mProfileImg;
    private int RESULT_LOAD_IMAGE = 5;

    public UserDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        return inflater.inflate(R.layout.fragment_user_data, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirstName = view.findViewById(R.id.first_name);
        mLastName = view.findViewById(R.id.last_name);
        mEmail = view.findViewById(R.id.email);
        mRegCode = view.findViewById(R.id.reg_code);
        mSaveBtn = view.findViewById(R.id.save_btn);
        mFbBtn = view.findViewById(R.id.fb_btn);
        mGoogleBtn = view.findViewById(R.id.google_btn);
        mProfileImg = view.findViewById(R.id.profile_img);

        mSaveBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mFirstName.getText().toString())) {
                mFirstName.setError(getString(R.string.error_textview));
                mFirstName.requestFocus();
            } else if (TextUtils.isEmpty(mLastName.getText().toString())) {
                mLastName.setError(getString(R.string.error_textview));
                mLastName.requestFocus();

            } else if (TextUtils.isEmpty(mEmail.getText().toString())) {
                mEmail.setError(getString(R.string.error_textview));
                mEmail.requestFocus();

            } else {
                Session session = Session.getInstance(getActivity());
                mLastName.setEnabled(false);
                mFirstName.setEnabled(false);
                mEmail.setEnabled(false);
                mProfileImg.setEnabled(false);
                mSaveBtn.setEnabled(false);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("first_name", mFirstName.getText().toString());
                    jsonObject.put("last_name", mLastName.getText().toString());
                    jsonObject.put("mail", mEmail.getText().toString());
                    jsonObject.put("mobile", session.getUser().getMobile());
                    if (img2!=null)
                    jsonObject.put("customerphoto64", img2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(URLManager.getInstance().getCustomerRegister())
                        .addJSONObjectBody(jsonObject)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Mobile", session.getUser().getMobile());
                            jsonObject.put("LoginType", 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AndroidNetworking.post(URLManager.getInstance().getLogin())
                                .addJSONObjectBody(jsonObject)
                                .build().getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, "onResponse: ");

                                try {
                                        User user=new Gson().fromJson(response.getJSONObject("user").toString(),User.class);
                                        Log.i(TAG, "onResponse: "+response.getJSONObject("user").toString());
                                        Session session= Session.getInstance(getActivity());
                                        session.setUser(user);
                                        session.setToken(response.getString("token"));
                                        mListener.onFragmentInteraction();
                                        dismiss();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.i(TAG, "onError: " + anError.getErrorDetail());

                            }
                        });
                    }

                    @Override
                    public void onError(ANError anError) {
                        int s=3;
                        Toast.makeText(getActivity(), "Error "+anError.getErrorBody() ,Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onError: " + anError.getErrorDetail());
                        mSaveBtn.setEnabled(true);

                    }
                });

            }

        });
        mProfileImg.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                final InputStream imageStream;
                if (imageUri != null&&getActivity()!=null) {
                    imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                    img = BitmapFactory.decodeStream(imageStream);
                    mProfileImg.setImageBitmap(img);
                    img2 = convert(imageUri, getActivity());


                }

            } catch (Exception e) {
                e.printStackTrace();
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

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
