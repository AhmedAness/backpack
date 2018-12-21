package com.wasltec.backpack.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wasltec.backpack.R;

public class CounterBtn extends RelativeLayout {
    View view;
    int counter = 0;
    Listener listener;
    private ImageButton mMinusBtn;
    private TextView mTitle;
    int oldCount=0;
    private ImageButton mAddBtn;
    private TextView mText;
    private TextView mS;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CounterBtn(Context getContext) {
        super(getContext);
        view = LayoutInflater.from(getContext).inflate(R.layout.counterbtn_item, this, true);
        mText = view.findViewById(R.id.text);
        mS = view.findViewById(R.id.desc);
        mMinusBtn = view.findViewById(R.id.minus_btn);
        mTitle = view.findViewById(R.id.title);
        mAddBtn = view.findViewById(R.id.add_btn);
        mTitle.setText(String.valueOf(counter));

        mMinusBtn.setOnClickListener(v -> {

            if (counter > 0) {
                oldCount=counter--;
                mTitle.setText(String.valueOf(counter));
                if (listener != null) {
                    listener.onChange(CounterBtn.this, counter,oldCount,price);
                }
            }
        });

        mAddBtn.setOnClickListener(v -> {

            oldCount=counter++;
            mTitle.setText(String.valueOf(counter));
            if (listener != null) {
                listener.onChange(CounterBtn.this, counter,oldCount, price);
            }

        });


    }

    public CounterBtn(Context getContext, AttributeSet attrs) {
        super(getContext, attrs);

        view = LayoutInflater.from(getContext).inflate(R.layout.counterbtn_item, this, true);


    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
        mTitle.setText(String.valueOf(counter));

    }
    public void setTitleText(String title){
        mText.setText(title);
    }
    public void setSubText(String text){
        mS.setText(text);
    }


    public interface Listener {
        public void onChange(View view, int counter,int oldCount,int price);
    }

}
