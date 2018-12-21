package com.wasltec.backpack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wasltec.backpack.models.User;
import com.wasltec.backpack.utils.SharedPreferencesManager;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.edit_user_name)
    TextView edit_user_name;
    @BindView(R.id.user_phone)
    EditText user_phone;
    @BindView(R.id.edit_user_phone)
    TextView edit_user_phone;
    @BindView(R.id.user_email)
    EditText user_email;
    @BindView(R.id.edit_email)
    TextView edit_email;
    @BindView(R.id.checck_id_verify)
    ImageView checck_id_verify;
    @BindView(R.id.check_deceleration_verify)
    ImageView check_deceleration_verify;
    @BindView(R.id.check_license_verify)
    ImageView check_license_verify;
    @BindView(R.id.progress)
    ImageView progress;
    @BindView(R.id.profile_img)
    CircleImageView profile_img;
    @BindView(R.id.edit_image)
    CircleImageView edit_image;
    View view;
    int preset = 3;
    PopupWindow popupWindow;
    @BindView(R.id.id_verify_txt)
    TextView id_verify_txt;
    @BindView(R.id.id_verify_desc)
    TextView id_verify_desc;
    private ImageView mProgressDialogDismiss;
    private TextView mProgressDialodTitle;
    private TextView mProgressDialogDesc;
    private ImageView mProgress1;
    private int RESULT_LOAD_IMAGE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
//        setdata();
        setCheckBoxs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_account, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        view = LayoutInflater.from(this).inflate(R.layout.fragment_check_progress_dialog, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(view, width, height, false);
        User user=Session.getInstance(this).getUser();
        user_name.setText(user.getName());
        user_email.setText(user.getMail());
        user_phone.setText(user.getMobile());

        Glide.with(this).load(user.getUserPhotoUrl()).apply(new RequestOptions().error(R.drawable.backpack_icon_gray_watermark)).into(profile_img);

        preset = Session.getInstance(AccountActivity.this).getCompleteInt();


        showPopup();

    }

    private void showPopup() {
        if (preset != 5)
            new Handler().postDelayed(() -> popupWindow.showAtLocation(view, Gravity.TOP, 0, (int) getResources().getDimension(R.dimen._60sdp)), 1000);

        // dismiss the popup window when touched

        mProgressDialogDismiss = view.findViewById(R.id.progress_dialog_dismiss);
        mProgressDialodTitle = view.findViewById(R.id.progress_dialod_title);
        mProgressDialogDesc = view.findViewById(R.id.progress_dialog_desc);
        mProgress1 = view.findViewById(R.id.progress);
        mProgressDialogDismiss.setOnClickListener(v -> popupWindow.dismiss());
        if (preset == 1) {
            mProgress1.setImageResource(R.drawable.profile_progress_10);
            mProgressDialodTitle.setText(String.format(getString(R.string.account_present_holder), 10));
            progress.setImageResource(R.drawable.profile_progress_10);

        } else if (preset == 2) {

            mProgress1.setImageResource(R.drawable.profile_progress_25);
            mProgressDialodTitle.setText(String.format(getString(R.string.account_present_holder), 25 * preset - 1));
            progress.setImageResource(R.drawable.profile_progress_25);

        } else if (preset == 3) {
            mProgress1.setImageResource(R.drawable.profile_progress_50);
            mProgressDialodTitle.setText(String.format(getString(R.string.account_present_holder), 25 * preset - 1));
            progress.setImageResource(R.drawable.profile_progress_50);

        } else if (preset == 4) {
            mProgress1.setImageResource(R.drawable.profile_progress_75);
            progress.setImageResource(R.drawable.profile_progress_75);

            mProgressDialodTitle.setText(String.format(getString(R.string.account_present_holder), 25 * preset - 1));
        } else if (preset == 5) {
            mProgress1.setImageResource(R.drawable.profile_progress_100);
            progress.setImageResource(R.drawable.profile_progress_100);

            mProgressDialodTitle.setText(String.format(getString(R.string.account_present_holder), 25 * preset - 1));
        }
    }

    @OnClick({R.id.edit_user_name, R.id.edit_user_phone, R.id.edit_email, R.id.edit_image})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.edit_user_name) {
            JSONObject jsonObject= new JSONObject();
            try {
//                    jsonObject.put("id",Session.getInstance().getId());
                jsonObject.put("user_name", user_name.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post(URLManager.getInstance().getEditProfile())
                    .addHeaders("Authorization", "bearer "+ Session.getInstance(this).getToken())
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(AccountActivity.this,"user_name updated ",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(AccountActivity.this,"user_name don't updated try again ",Toast.LENGTH_SHORT).show();
                        }
                    });

        } else if (id == R.id.edit_user_phone) {
            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("id",Session.getInstance(this).getUser().getId());
                jsonObject.put("mobile",user_phone.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post(URLManager.getInstance().getEditProfile())
                    .addHeaders("Authorization", "bearer "+ Session.getInstance(this).getToken())
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(AccountActivity.this,"mobile updated ",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(AccountActivity.this,"mobile don't updated try again ",Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (id == R.id.edit_email) {
            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("id",Session.getInstance(this).getUser().getId());
                jsonObject.put("mail", user_email.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post(URLManager.getInstance().getEditProfile())
                    .addHeaders("Authorization", "bearer "+ Session.getInstance(this).getToken())
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(AccountActivity.this,"mail updated ",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(AccountActivity.this,"mail don't updated try again ",Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (id == R.id.edit_image) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");

            startActivityForResult(i, RESULT_LOAD_IMAGE);

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
//            popupWindow.showAtLocation(view, Gravity.TOP, 0, );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap img = BitmapFactory.decodeStream(imageStream);
                profile_img.setImageBitmap(img);

                JSONObject jsonObject= new JSONObject();
                jsonObject.put("id",Session.getInstance(this).getUser().getId());
                jsonObject.put("customerphoto64",convert(img));

                AndroidNetworking.post(URLManager.getInstance().getEditProfile())
                        .addHeaders("Authorization", "bearer "+ Session.getInstance(this).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(AccountActivity.this,"image updated ",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(AccountActivity.this,"image don't updated try again ",Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @OnClick({R.id.id_verify_txt, R.id.id_deceleration_txt, R.id.id_license_txt})
    public void onViewClicked2(View view) {
        int id = view.getId();
        if (id == R.id.id_verify_txt) {

            startActivity(new Intent(this, IDVerifcaionActivity.class));

        } else if (id == R.id.id_deceleration_txt) {
            startActivity(new Intent(this, DecelrationActivity.class));
        } else if (id == R.id.id_license_txt) {

            startActivity(new Intent(this, LicensesActivity.class));
        }
    }

    private void setdata() {
        user_phone.setText(Session.getInstance(this).getUser().getMobile());
        user_name.setText(Session.getInstance(this).getUser().getName());
        user_email.setText(Session.getInstance(this).getUser().getMail());
        Glide.with(this).load(Session.getInstance(this).getUser().getUserPhotoUrl()).apply(new RequestOptions()
                .placeholder(new ColorDrawable(Color.GRAY))).into(profile_img);

    }

    private void setCheckBoxs() {

        AndroidNetworking.get(URLManager.getInstance().getCheckVerificationId())
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals("1"))
                                checck_id_verify.setImageResource(R.drawable.check_mark);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });

        AndroidNetworking.get(URLManager.getInstance().getCheckUserDeclaration())
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals("1"))
                                check_deceleration_verify.setImageResource(R.drawable.check_mark);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });

        // TODO: 10/25/2018 check_license_verify
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
