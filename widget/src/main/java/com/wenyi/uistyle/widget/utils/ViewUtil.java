package com.wenyi.uistyle.widget.utils;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;


import com.wenyi.uistyle.widget.R;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtil {
	
	public static final long FRAME_DURATION = 1000 / 60;

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @SuppressLint("NewApi")
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue))
                    return result;
            }
        }
        else
            return View.generateViewId();
    }
    
    public static boolean hasState(int[] states, int state){
		if(states == null)
			return false;

        for (int state1 : states)
            if (state1 == state)
                return true;
		
		return false;
	}

    public static void setBackground(View v, Drawable drawable){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            v.setBackground(drawable);
        else
            v.setBackgroundDrawable(drawable);
    }


    /**
     * Apply any View style attributes to a view.
     * @param v The view is applied.
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public static void applyStyle(View v, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        TypedArray a = v.getContext().obtainStyledAttributes(attrs, R.styleable.View, defStyleAttr, defStyleRes);

        int leftPadding = -1;
        int topPadding = -1;
        int rightPadding = -1;
        int bottomPadding = -1;
        int startPadding = Integer.MIN_VALUE;
        int endPadding = Integer.MIN_VALUE;
        int padding = -1;

        boolean startPaddingDefined = false;
        boolean endPaddingDefined = false;
        boolean leftPaddingDefined = false;
        boolean rightPaddingDefined = false;



        if (padding >= 0)
            v.setPadding(padding, padding, padding, padding);
//        else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
//            if(startPaddingDefined)
//                leftPadding = startPadding;
//            if(endPaddingDefined)
//                rightPadding = endPadding;
//
//            v.setPadding(leftPadding >= 0 ? leftPadding : v.getPaddingLeft(),
//                    topPadding >= 0 ? topPadding : v.getPaddingTop(),
//                    rightPadding >= 0 ? rightPadding : v.getPaddingRight(),
//                    bottomPadding >= 0 ? bottomPadding : v.getPaddingBottom());
//        }
//        else{
//            if(leftPaddingDefined || rightPaddingDefined)
//                v.setPadding(leftPaddingDefined ? leftPadding : v.getPaddingLeft(),
//                        topPadding >= 0 ? topPadding : v.getPaddingTop(),
//                        rightPaddingDefined ? rightPadding : v.getPaddingRight(),
//                        bottomPadding >= 0 ? bottomPadding : v.getPaddingBottom());
//
//            if(startPaddingDefined || endPaddingDefined)
//                v.setPaddingRelative(startPaddingDefined ? startPadding : v.getPaddingStart(),
//                        topPadding >= 0 ? topPadding : v.getPaddingTop(),
//                        endPaddingDefined ? endPadding : v.getPaddingEnd(),
//                        bottomPadding >= 0 ? bottomPadding : v.getPaddingBottom());
//        }

        a.recycle();
    }

    public static void applyFont(TextView v, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        TypedArray a = v.getContext().obtainStyledAttributes(attrs, new int[]{R.attr.tv_fontFamily}, defStyleAttr, defStyleRes);
        String fontFamily = a.getString(0);

        if(fontFamily != null){
            Typeface typeface = TypefaceUtil.load(v.getContext(), fontFamily, 0);
            v.setTypeface(typeface);
        }

        a.recycle();
    }

    public static void applyTextAppearance(TextView v, int resId){
        if(resId == 0)
            return;

        String fontFamily = null;
        int typefaceIndex = -1;
        int styleIndex = -1;
        int shadowColor = 0;
        float dx = 0, dy = 0, r = 0;

        TypedArray appearance = v.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
        if (appearance != null) {
            int n = appearance.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = appearance.getIndex(i);

                if (attr == R.styleable.TextAppearance_android_textColor) {
                    v.setTextColor(appearance.getColorStateList(attr));

                } else if (attr == R.styleable.TextAppearance_android_textSize) {
                    v.setTextSize(TypedValue.COMPLEX_UNIT_PX, appearance.getDimensionPixelSize(attr, 0));

                } else if (attr == R.styleable.TextAppearance_android_typeface) {
                    typefaceIndex = appearance.getInt(attr, -1);

                } else if (attr == R.styleable.TextAppearance_android_textStyle) {
                    styleIndex = appearance.getInt(attr, -1);

                }else if (attr == R.styleable.TextAppearance_android_shadowColor) {
                    shadowColor = appearance.getInt(attr, 0);

                } else if (attr == R.styleable.TextAppearance_android_shadowDx) {
                    dx = appearance.getFloat(attr, 0);

                } else if (attr == R.styleable.TextAppearance_android_shadowDy) {
                    dy = appearance.getFloat(attr, 0);

                } else if (attr == R.styleable.TextAppearance_android_shadowRadius) {
                    r = appearance.getFloat(attr, 0);

                }
            }

            appearance.recycle();
        }

        if (shadowColor != 0)
            v.setShadowLayer(r, dx, dy, shadowColor);

        Typeface tf = null;
        if (fontFamily != null) {
            tf = TypefaceUtil.load(v.getContext(), fontFamily, styleIndex);
            if (tf != null)
                v.setTypeface(tf);
        }
        if(tf != null) {
            switch (typefaceIndex) {
                case 1:
                    tf = Typeface.SANS_SERIF;
                    break;
                case 2:
                    tf = Typeface.SERIF;
                    break;
                case 3:
                    tf = Typeface.MONOSPACE;
                    break;
            }
            v.setTypeface(tf, styleIndex);
        }
    }




}
