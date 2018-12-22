package com.wasltec.backpack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.wasltec.backpack.AccountActivity.convert;

public class IDVerifcaionActivity extends AppCompatActivity {

    private EditText mBirthDateTxt;
    private Spinner mGender;
    private Spinner mNationality;
    private Spinner mIdentityType;
    private EditText mIdNumber;
    private EditText mExpireDate;
    private TextView mloadphoto;
    private ImageView id_image ;
    private Button mConfirmBtn;

    private Calendar myCalendar;
    private Bitmap img=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_idverifcaion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBirthDateTxt = findViewById(R.id.date_txt);
        mBirthDateTxt.setOnClickListener(v -> {
            createTimePickerDialogue(mBirthDateTxt);
        });

        mGender = findViewById(R.id.gender);

        mNationality = findViewById(R.id.nationality);
        List<String> nationalities = new ArrayList<>();
        AndroidNetworking.get(URLManager.getInstance().getLookUp("Nationality"))
                .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.d("DisplayLanguage", Locale.getDefault().getDisplayLanguage());
                                String s = "en";
                                if (Locale.getDefault().getDisplayLanguage().equals("ar"))
                                    s = "ar";
                                nationalities.add(jsonObject.getString("nationality_name_" + s));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mNationality.setAdapter(new ArrayAdapter<>(IDVerifcaionActivity.this, android.R.layout.simple_spinner_item, nationalities));
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


        mIdentityType = findViewById(R.id.identity_type);

        mIdNumber = findViewById(R.id.id_number);

        mExpireDate = findViewById(R.id.expire_date);
        mloadphoto  = findViewById(R.id.photo);
        id_image  = findViewById(R.id.id_image);
        mloadphoto.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");

            startActivityForResult(i, 1000);
        });
    mExpireDate.setOnClickListener(v -> {
            createTimePickerDialogue(mExpireDate);
        });

        mConfirmBtn = findViewById(R.id.Confirm_btn);
        mConfirmBtn.setOnClickListener(v -> {
            if (isValidData()) {
                JSONObject object = new JSONObject();
                try {
                    object.put("userId", Session.getInstance(this).getUser().getId());
                    object.put("nationality", mNationality.getSelectedItem().toString());
                    object.put("identification_type", mIdentityType.getSelectedItem().toString());
                    object.put("identification_number", mIdNumber.getText().toString());
                    object.put("expiry_date", mExpireDate);
                    object.put("id_copy", convert(img));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(URLManager.getInstance().getVerificationId())
                        .addHeaders("Authorization", "bearer " + Session.getInstance(this).getToken())
                        .addJSONObjectBody(object)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(IDVerifcaionActivity.this, "verification data sent", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(IDVerifcaionActivity.this, "failed to send verification data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void createTimePickerDialogue(EditText editText) {
        myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(IDVerifcaionActivity.this, (view, sYear, sMonth, sDayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, sYear);
            myCalendar.set(Calendar.MONTH, sMonth);
            myCalendar.set(Calendar.DAY_OF_MONTH, sDayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);

            editText.setText(sdf.format(myCalendar.getTime()));
        }, year, month, day);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    private boolean isValidData() {
        if (mBirthDateTxt.getText().toString().equals(""))
            mBirthDateTxt.setError("fiald is required");
        else if (mIdNumber.getText().toString().equals(""))
            mIdNumber.setError("fiald is required");
        else if (mExpireDate.getText().toString().equals(""))
            mExpireDate.setError("fiald is required");
        else if(img==null)
            mloadphoto.setError("fiald is required");
        else
            return true;
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                 img = BitmapFactory.decodeStream(imageStream);
                id_image.setImageBitmap(img);

//                JSONObject jsonObject= new JSONObject();
//                jsonObject.put("id",Session.getInstance(this).getUser().getId());
//                jsonObject.put("customerphoto64",convert(img));
//
//                AndroidNetworking.post(URLManager.getInstance().getEditProfile())
//                        .addHeaders("Authorization", "bearer "+ Session.getInstance(this).getToken())
//                        .addJSONObjectBody(jsonObject)
//                        .build()
//                        .getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Toast.makeText(AccountActivity.this,"image updated ",Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Toast.makeText(AccountActivity.this,"image don't updated try again ",Toast.LENGTH_SHORT).show();
//                            }
//                        });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
