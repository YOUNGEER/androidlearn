package com.wenyi.refresh;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by baoyz on 14/11/2.
 */
class BitmapDrawable extends RefreshDrawable {

	private boolean isRunning;

	private int mWidth;
	private int mHeight;
	private int maxheight;
	private int mOffsetTop;

	private int[] mColorSchemeColors;
	private Handler mHandler = new Handler();
	private int index;

	private int resids[] = { R.drawable.loading0,
		R.drawable.loading1,  R.drawable.loading2, R.drawable.loading3, R.drawable.loading4, R.drawable.loading5, R.drawable.loading6,R.drawable.loading7 };
	private final int MAX_LEVEL = resids.length;
	private final int mWidthScreen;

	BitmapDrawable(Context context, PullRefreshLayout layout) {
		super(context, layout);

		WindowManager wm = (WindowManager) getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mWidthScreen = outMetrics.widthPixels;
	}

	@Override
	public void setPercent(float percent) {

	}


	@Override
	public void setColorSchemeColors(int[] colorSchemeColors) {
		mColorSchemeColors = colorSchemeColors;
	}

	@Override
	public void offsetTopAndBottom(int offset) {
		// mTop += offset;
		mOffsetTop += offset;

		invalidateSelf();
	}

	@Override
	public void start() {
		index = 0;
		isRunning = true;
		mHandler.post(mAnimationTask);
	}

	private Runnable mAnimationTask = new Runnable() {
		@Override
		public void run() {
			if (isRunning()) {
				index++;
				if (index >= MAX_LEVEL)
					index = 0;
				invalidateSelf();
				mHandler.postDelayed(this, 100);
			}
		}
	};

	@Override
	public void stop() {
		isRunning = false;
		mHandler.removeCallbacks(mAnimationTask);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		mWidth = getRefreshLayout().getFinalOffset();
		mHeight = mWidth;
		maxheight =mHeight;

	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		drawDrawable(canvas);
		canvas.restore();
	}

	private void drawDrawable(Canvas canvas) {
		if (isRunning) {
			Drawable drawablepeople = getDrawable(
					resids[index]);
			float scanl = mOffsetTop * 1.0f / maxheight;
			if (scanl > 1) {
				scanl = 1;
			}
			float peopleheight = maxheight * scanl;
			float peoplewidth = peopleheight
					* drawablepeople.getIntrinsicWidth()
					/ drawablepeople.getIntrinsicHeight();

			drawablepeople.setBounds((int) (mWidthScreen-peoplewidth)/2,
					(int) (mHeight-peopleheight)/2,
					(int) (mWidthScreen+peoplewidth)/2,
					(int) ( peopleheight*scanl));
			drawablepeople.draw(canvas);
		} else {

			Drawable drawablepeople = getDrawable(
					R.drawable.loading0);
			float scanl = mOffsetTop * 1.0f / maxheight;
			if (scanl > 1) {
				scanl = 1;
			}
			float peopleheight = maxheight * scanl;
			float peoplewidth = peopleheight
					* drawablepeople.getIntrinsicWidth()
					/ drawablepeople.getIntrinsicHeight();
			float drawscanl=0.5f + 0.5f*scanl;
			drawablepeople.setBounds(
					(int) (mWidthScreen-peoplewidth)/2,
					(int) ((mHeight/2)*drawscanl  - peopleheight / 2),
					(int) (mWidthScreen+peoplewidth)/2,
					(int) ( peopleheight*scanl));
			drawablepeople.draw(canvas);

		}
	}

	private final Drawable getDrawable(@DrawableRes int resID){
		Resources resources = getContext().getResources();
		if (Build.VERSION.SDK_INT >= 21) {
			return resources.getDrawable(resID, null);
		}
		return resources.getDrawable(resID);
	}

//	private int dp2px(int dp) {
//		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//				getContext().getResources().getDisplayMetrics());
//	}

//	private int evaluate(float fraction, int startValue, int endValue) {
//		int startInt = startValue;
//		int startA = (startInt >> 24) & 0xff;
//		int startR = (startInt >> 16) & 0xff;
//		int startG = (startInt >> 8) & 0xff;
//		int startB = startInt & 0xff;
//
//		int endInt = endValue;
//		int endA = (endInt >> 24) & 0xff;
//		int endR = (endInt >> 16) & 0xff;
//		int endG = (endInt >> 8) & 0xff;
//		int endB = endInt & 0xff;
//
//		return ((startA + (int) (fraction * (endA - startA))) << 24)
//				| ((startR + (int) (fraction * (endR - startR))) << 16)
//				| ((startG + (int) (fraction * (endG - startG))) << 8)
//				| ((startB + (int) (fraction * (endB - startB))));
//	}

}
