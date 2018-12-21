package com.wasltec.provider.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasltec.provider.R;


public class CounterBtn extends LinearLayout {
    View view;
    int counter = 0;
    Listener listener;
    private ImageButton mMinusBtn;
    private TextView mTitle;
    private ImageButton mAddBtn;

    public CounterBtn(Context getContext) {
        super(getContext);
        this.setOrientation(VERTICAL);
        view = LayoutInflater.from(getContext).inflate(R.layout.counterbtn_item, this, false);

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

        mMinusBtn = view.findViewById(R.id.minus_btn);
        mTitle = view.findViewById(R.id.title);
        mAddBtn = view.findViewById(R.id.add_btn);
        mTitle.setText(String.valueOf(counter));
        mMinusBtn.setOnClickListener(v -> {

            if (counter > 0) {
                counter--;
                mTitle.setText(String.valueOf(counter));
                if (listener != null) {
                    listener.onChange(CounterBtn.this, counter);
                }
            }
        });
        mAddBtn.setOnClickListener(v -> {

            counter++;
            mTitle.setText(String.valueOf(counter));
            if (listener != null) {
                listener.onChange(CounterBtn.this, counter);
            }

        });
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
        mTitle.setText(String.valueOf(counter));

    }

    public interface Listener {
        public void onChange(View view, int counter);
    }
}
