package com.wasltec.backpack.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.wasltec.backpack.Cart;
import com.wasltec.backpack.R;
import com.wasltec.backpack.models.BookingDetails.BookingDetailsModel;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class Slider_Adopter extends PagerAdapter {

    private List<BookingDetailsModel> data;
    private LayoutInflater inflater;
    private Context context;
    private ImageView mImage;
    private TextView mTicketNumber;
    private TextView mTripName;
    private TextView mTripProvider;
    private TextView mDate;
    private TextView mTime;
    private TextView mTextView11;

    public Slider_Adopter(Context context, List<BookingDetailsModel> images) {
        this.context = context;
        this.data = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);

        initView(myImageLayout);
        setdata(position);


        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    private void setdata(int position) {
        try {
            if (!Cart.getInstance().isIsgroub()) {
                mImage.setImageBitmap(encodeAsBitmap(data.get(0).getBookingTicketDetails().get(position).getTicketNumber() + ""));
                mTicketNumber.setText(mTicketNumber.getText().toString() + data.get(0).getBookingTicketDetails().get(position).getTicketNumber());
                mTripName.setText(data.get(0).getActivityName());

                for (int i=0; i<data.get(0).getBookingTicketDetails().get(position).getBookingTicketAddonsDetails().size(); i++) {
                    if(i == 0)
                        mTripProvider.setText("By ");
                    else
                        mTripProvider.append(" - ");
                    mTripProvider.append(data.get(0).getBookingTicketDetails().get(position).getBookingTicketAddonsDetails().get(i).getProviderUsername());
                }
            } else {
                mImage.setImageBitmap(encodeAsBitmap(data.get(0).getBookingTicketDetailsGroups().get(0).getTicketNumber() + ""));
                mTicketNumber.setText(mTicketNumber.getText().toString() + data.get(0).getBookingTicketDetailsGroups().get(0).getTicketNumber());
                mTripName.setText(data.get(0).getActivityName());
                mTripProvider.setText("");
            }
        } catch (Exception e) {
            mTripProvider.setText("");
            e.printStackTrace();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM");
        try {

            mDate.setText(dateFormat.format(data.get(0).getActivityStart().replace("T", "")));
        } catch (Exception e) {

        }


        mTime.setText(data.get(0).getActivityEnd());


    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 500, 500, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    private void initView(View view) {
        mImage = view.findViewById(R.id.image);
        mTicketNumber = view.findViewById(R.id.ticket_number);
        mTripName = view.findViewById(R.id.trip_name);
        mTripProvider = view.findViewById(R.id.trip_provider);
        mDate = view.findViewById(R.id.Date);
        mDate = view.findViewById(R.id.date);
        mTime = view.findViewById(R.id.Time);

    }
}