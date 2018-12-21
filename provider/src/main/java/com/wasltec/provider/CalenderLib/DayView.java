package com.wasltec.provider.CalenderLib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.loopeer.shadow.ShadowView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.wasltec.provider.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DayView extends LinearLayout {
    View view;

    private List<DayDecorator> decorators;
    private Date date;

    public TextView mTitle;
    public TextView mSubtitle;
    public TextView mSubtitle2;
    public RelativeLayout preview;
    public ImageView closed ;

    String titleText;
    int valueTitleColor;
    String subtitleText;
    int subvalueTitleColor;

    TypedArray a;

    public DayView(Context getContext) {
        super(getContext);
        this.setOrientation(VERTICAL);
        view = LayoutInflater.from(getContext).inflate(R.layout.day_view_layout, this, false);

    }

    public DayView(Context getContext, AttributeSet attrs) {
        super(getContext, attrs);

        view = LayoutInflater.from(getContext).inflate(R.layout.day_view_layout, this, true);

        a = getContext.obtainStyledAttributes(attrs,
                R.styleable.Checkbox2, 0, 0);

        titleText = a.getString(R.styleable.Checkbox2_titleText);

            valueTitleColor = a.getColor(R.styleable.Checkbox2_titleColor,
                    getResources().getColor(android.R.color.background_dark));


        subtitleText = a.getString(R.styleable.Checkbox2_subtitleText);


            subvalueTitleColor = a.getColor(R.styleable.Checkbox2_subtitleColor,
                    getResources().getColor(android.R.color.background_dark));



        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = (TextView) this.findViewById(R.id.title);
        mSubtitle = (TextView) this.findViewById(R.id.subtitle);
        mSubtitle2 = (TextView) this.findViewById(R.id.subtitle2);
        closed = (ImageView) this.findViewById(R.id.closed);
        preview = (RelativeLayout) this.findViewById(R.id.preview);


        mTitle.setText(titleText);
        mTitle.setTextColor(valueTitleColor);

        mSubtitle.setText(subtitleText);
        mSubtitle2.setText(subtitleText);
        mSubtitle.setTextColor(subvalueTitleColor);
        mSubtitle2.setTextColor(subvalueTitleColor);

        closed.setVisibility(GONE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public DayView(Context getContext, AttributeSet attrs, int defStyleAttr) {
        super(getContext, attrs, defStyleAttr);

    }

    public void setTitleText(String s) {
        mTitle.setText(s);
    }

    public void setSubTitleText(String s) {
        mSubtitle.setText(s);
    }
  public void setSubTitle2Text(String s) {
        mSubtitle2.setText(s);
    }


    public void decorate() {
        //Set custom decorators
        if (null != decorators) {
            for (DayDecorator decorator : decorators) {
                decorator.decorate(this);
            }
        }
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.date = date;
        this.decorators = decorators;

        final SimpleDateFormat df = new SimpleDateFormat("d");
        int day = Integer.parseInt(df.format(date));
        mTitle.setText(String.valueOf(day));


    }
    public void bind(Date date, List<DayDecorator> decorators, String  TmSubtitle, String  TmSubtitle2) {
        this.date = date;
        this.decorators = decorators;

        final SimpleDateFormat df = new SimpleDateFormat("d");
        int day = Integer.parseInt(df.format(date));
        mTitle.setText(String.valueOf(day));
        mSubtitle.setText(TmSubtitle);
        mSubtitle2.setText(TmSubtitle2);

    }
    public Date getDate() {
        return date;
    }



}
