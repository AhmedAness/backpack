package com.wasltec.provider.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasltec.provider.R;


public class Checkbox2 extends LinearLayout {
    View view;
    private TextView mTitle;
    private TextView mSubtitle;

    String titleText;
    int valueTitleColor;
    String subtitleText;
    int subvalueTitleColor;

    TypedArray a;

    public Checkbox2(Context getContext) {
        super(getContext);
        this.setOrientation(VERTICAL);
        view = LayoutInflater.from(getContext).inflate(R.layout.checkbox_item, this, false);

    }

    public Checkbox2(Context getContext, AttributeSet attrs) {
        super(getContext, attrs);

        view = LayoutInflater.from(getContext).inflate(R.layout.checkbox_item, this, true);

        a = getContext.obtainStyledAttributes(attrs,
                R.styleable.Checkbox2, 0, 0);

        titleText = a.getString(R.styleable.Checkbox2_titleText);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            valueTitleColor = a.getColor(R.styleable.Checkbox2_titleColor,
                    getResources().getColor(android.R.color.background_dark, getContext().getTheme()));
        } else {
            valueTitleColor = a.getColor(R.styleable.Checkbox2_titleColor,
                    getResources().getColor(android.R.color.background_dark));

        }
        subtitleText = a.getString(R.styleable.Checkbox2_subtitleText);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            subvalueTitleColor = a.getColor(R.styleable.Checkbox2_subtitleColor,
                    getResources().getColor(android.R.color.background_dark, getContext().getTheme()));
        } else {
            subvalueTitleColor = a.getColor(R.styleable.Checkbox2_subtitleColor,
                    getResources().getColor(android.R.color.background_dark));

        }

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = this.findViewById(R.id.title);
        mSubtitle = this.findViewById(R.id.subtitle);


        mTitle.setText(titleText);
        mTitle.setTextColor(valueTitleColor);

        mSubtitle.setText(subtitleText);
        mSubtitle.setTextColor(subvalueTitleColor);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public Checkbox2(Context getContext, AttributeSet attrs, int defStyleAttr) {
        super(getContext, attrs, defStyleAttr);

    }

    public void setTitleText(String s) {
        mTitle.setText(s);
    }

    public void setSubTitleText(String s) {
        mSubtitle.setText(s);
    }


    public interface Listener {
        public void onChecked(int id, boolean isChecked);
    }
}
