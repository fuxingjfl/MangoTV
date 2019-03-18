package com.xha.mangotv.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ysq on 2018/12/3.
 */

public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
        setThickness();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setThickness();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setThickness();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setThickness();
    }
    public void setThickness(){
        TextPaint paint = getPaint();
        paint.setFakeBoldText(true);
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        postInvalidate();
    }
}
