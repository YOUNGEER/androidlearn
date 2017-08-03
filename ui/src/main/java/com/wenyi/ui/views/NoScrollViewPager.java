package com.wenyi.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Found at http://stackoverflow.com/questions/7814017/is-it-possible-to-disable-scrolling-on-a-viewpager.
 * Convenient way to temporarily disable ViewPager navigation while interacting with ImageView.
 * 
 * Julia Zudikova
 */

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * <p/>
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * <p/>
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 *
 * @author Chris Banes
 */
public class NoScrollViewPager extends ViewPager {

	private boolean isScrollable = false;
	
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	public void setScrollable(boolean scrollable) {
		isScrollable = scrollable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isScrollable)
			return super.onTouchEvent(arg0);
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isScrollable)
			return super.onInterceptTouchEvent(arg0);
		return false;
	}
}
