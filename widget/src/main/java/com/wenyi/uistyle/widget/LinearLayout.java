package com.wenyi.uistyle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by Administrator on 2016/12/2.
 */

public class LinearLayout extends android.widget.LinearLayout {
    private RippleManager mRippleManager;
    private Paint mDiliderPaint;

    public LinearLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }


    protected void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        getRippleManager().onCreate(this, context, attrs, defStyleAttr, defStyleRes);
    }



    private void initPaint() {
        if(mRippleManager.isUnderlinerd()){
            if(mDiliderPaint==null)
                mDiliderPaint = new Paint();
            mDiliderPaint.setColor(Color.parseColor("#f2f2f2"));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        RippleManager.cancelRipple(this);
    }

    @Override
    public void setBackground(Drawable drawable) {
        Drawable background = getBackground();
        if(background instanceof RippleDrawable && !(drawable instanceof RippleDrawable))
            ((RippleDrawable) background).setBackgroundDrawable(drawable);
        else
            super.setBackground(drawable);
    }

    protected RippleManager getRippleManager(){
        if(mRippleManager == null){
            synchronized (RippleManager.class){
                if(mRippleManager == null)
                    mRippleManager = new RippleManager();
            }
        }

        return mRippleManager;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        RippleManager rippleManager = getRippleManager();
        if (l == rippleManager)
            super.setOnClickListener(l);
        else {
            rippleManager.setOnClickListener(l);
            setOnClickListener(rippleManager);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        return  getRippleManager().onTouchEvent(this, event) || result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mRippleManager.isUnderlinerd()){
            int height = getMeasuredHeight() - dip2px(0.5f);
            canvas.drawLine(getPaddingLeft(),height,getMeasuredWidth(),height, mDiliderPaint);
        }

    }
    public  int dip2px(float dps) {
//        final float scale = getScreenDensity(context);
        return Math.round( getResources().getDisplayMetrics().density * dps);
    }

}
