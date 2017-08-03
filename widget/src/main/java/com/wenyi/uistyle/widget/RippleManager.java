package com.wenyi.uistyle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wenyi.uistyle.widget.utils.ViewUtil;

import java.lang.ref.WeakReference;

public final class RippleManager implements View.OnClickListener{

	private View.OnClickListener mClickListener;
	private boolean mClickScheduled = false;
	private ClickRunnable mClickRunnable;
	private boolean isUnderlinerd;

	public RippleManager(){}

	/**
	 * Should be called in the construction method of view to create a RippleDrawable.
	 * @param v
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	public void onCreate(View v, Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
		if(v.isInEditMode())
			return;

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleView, defStyleAttr, defStyleRes);
		int rippleStyle = a.getResourceId(R.styleable.RippleView_rd_style, 0);
		isUnderlinerd = a.getBoolean(R.styleable.RippleView_rd_underlined, false);
		RippleDrawable drawable = null;

		if(rippleStyle != 0)
			drawable = new RippleDrawable.Builder(context, rippleStyle).backgroundDrawable(getBackground(v)).build();
		else{
			boolean rippleEnable = a.getBoolean(R.styleable.RippleView_rd_enable, false);
			if(rippleEnable)
				drawable = new RippleDrawable.Builder(context, attrs, defStyleAttr, defStyleRes).backgroundDrawable(getBackground(v)).build();
		}

		a.recycle();

		if(drawable != null)
			ViewUtil.setBackground(v, drawable);
	}

	private Drawable getBackground(View v){
		Drawable background = v.getBackground();
		if(background == null)
			return null;

		if(background instanceof RippleDrawable)
			return ((RippleDrawable)background).getBackgroundDrawable();

		return background;
	}

	public void setOnClickListener(View.OnClickListener l) {
		mClickListener = l;
	}

	public boolean onTouchEvent(View v, MotionEvent event){
		Drawable background = v.getBackground();
		return background != null && background instanceof RippleDrawable && ((RippleDrawable) background).onTouch(v, event);
	}

	@Override
	public void onClick(View v) {
		Drawable background = v.getBackground();
		long delay = 0;

		if(background != null) {
			if (background instanceof RippleDrawable)
				delay = ((RippleDrawable) background).getClickDelayTime();

		}

		if(delay > 0 && v.getHandler() != null && !mClickScheduled) {
			mClickScheduled = true;
			if(mClickRunnable ==null)
				mClickRunnable = new ClickRunnable(new WeakReference<RippleManager>(this),new WeakReference<View>(v));
			v.getHandler().postDelayed(mClickRunnable, delay);
		}
		else
			dispatchClickEvent(v);
	}

	private void dispatchClickEvent(View v){
		if(mClickListener != null)
			mClickListener.onClick(v);
	}
	public boolean isUnderlinerd(){
		return isUnderlinerd;
	}
	/**
	 * Cancel the ripple effect of this view and all of it's children.
	 * @param v
	 */
	public static void cancelRipple(View v){
		Drawable background = v.getBackground();
		if(background instanceof RippleDrawable)
			((RippleDrawable)background).cancel();


		if(v instanceof ViewGroup){
			ViewGroup vg = (ViewGroup)v;
			for(int i = 0, count = vg.getChildCount(); i < count; i++)
				RippleManager.cancelRipple(vg.getChildAt(i));
		}

	}

	static class  ClickRunnable implements Runnable{
		private  WeakReference<RippleManager> mWfRm;
		private WeakReference<View> mWrV;

		public ClickRunnable(WeakReference<RippleManager> wfRm,WeakReference<View> wrV){
			mWfRm = wfRm;
			mWrV = wrV;
		}

		@Override
		public void run() {
			if(mWfRm==null||mWrV==null||mWfRm.get()==null||mWrV.get()==null)return;
			mWfRm.get().mClickScheduled = false;
			mWfRm.get().dispatchClickEvent(mWrV.get());
		}
	}

}
