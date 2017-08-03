package com.wenyi.ui.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wenyi.recycler.LoadRecyclerView;
import com.wenyi.ui.R;
import com.wenyi.ui.UIApp;
import com.wenyi.ui.views.OnRecyclerPauseListen;

import java.lang.ref.WeakReference;


public class MyViewUtil {
//	private static String PACKAGE="com.kinbong.jbcclient";
	public static void removeView(View view){
		if (view != null) {
			WeakReference<View> w = new WeakReference<>(view);
			View v = w.get();
			if(v==null) return;
			ViewParent parent = v.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				ViewGroup group = (ViewGroup) parent;
				group.removeView(v);
			}
		}
	}

	public static void setViewEnabled(TextView view, boolean isEnable) {
		if (isEnable) view.setTextColor(getColor(R.color.white));
		else view.setTextColor(getColor(R.color.gary_text));
		view.setEnabled(isEnable);
		view.setBackgroundResource(isEnable?R.drawable.sp_btn_bg:R.drawable.sp_btn_gray_bg);
	}

	public static void setViewGone(View v) {
		if(v!=null&&v.getVisibility()== View.VISIBLE){
			v.setVisibility(View.GONE);
		}
	}
	public static Drawable getDrawable2(int resID) {
		Drawable drawable = getDrawable(resID);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
		return drawable;
	}
	public static void setViewVisiable(View v) {
		if(v!=null&&v.getVisibility()==View.GONE)v.setVisibility(View.VISIBLE);
	}

//	public static void setWebView(WebView view){
//		//在高版本的Android系统中，需要关闭硬件加速才能让背景透明
//		view.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//
//		//详细内容，显示的是html的内容；
//
//		WebSettings ws = view.getSettings();
////去除登录时浏览器记住密码对话框
//		if (Build.VERSION.SDK_INT <= 18) {
//			ws.setSavePassword(false);
//		}
//
//		ws.setJavaScriptEnabled(true);
//		ws.setJavaScriptCanOpenWindowsAutomatically(true);//js支持x
//
//		ws.setAllowFileAccess(true);
//		ws.setBuiltInZoomControls(false);
//		ws.setSupportZoom(false);
//		//启用数据库
//		ws.setDatabaseEnabled(true);
//		String dir = UIApp.sContext.getDir("database", Context.MODE_PRIVATE).getPath();
//		//设置定位的数据库路径
//		ws.setGeolocationDatabasePath(dir);
//
////启用地理定位
//		ws.setGeolocationEnabled(true);
//		ws.setDomStorageEnabled(true);
//
//
////        ws.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
//		/**
//		 *  设置网页布局类型：
//		 *  1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
//		 *  2、LayoutAlgorithm.SINGLE_COLUMN: 适应屏幕，内容将自动缩放
//		 *
//		 */
//		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//		ws.setDefaultTextEncodingName("utf-8"); //设置文本编码
//		ws.setAppCacheEnabled(true);
//		ws.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式</span>
//		String saveDirPath = CacheUtil.getSaveDirPath();
//
//		if(!TextUtils.isEmpty(saveDirPath)){
//			ws.setAppCachePath(saveDirPath);
//		}
//	}

	public static void setRefreshView(SwipeRefreshLayout refresh){
		WeakReference<SwipeRefreshLayout> w = new WeakReference<>(refresh);
		SwipeRefreshLayout s = w.get();
		if(s==null){
			return;
		}
		s.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		s. setProgressBackgroundColorSchemeResource(R.color.refresh_bg);
	}

	public static void setLoadMoreRcycler(LoadRecyclerView recycler){
		recycler.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(recycler.getContext().getApplicationContext());
		recycler.setLayoutManager(layoutManager);
		recycler.addOnScrollListener(new OnRecyclerPauseListen(true, true));
		//设置自定义加载中和到底了效
		TextView tv = new TextView(recycler.getContext());
		tv.setText("加载中...");
		recycler.setFootLoadingView(tv);
		TextView textView = new TextView(recycler.getContext());
		textView.setText("已经到底啦~");
		recycler.setFootEndView(textView);
	}
	public static void setRecyclerView(RecyclerView recycler, boolean isStarLayoutAnim, boolean isLoadImage, RecyclerView.LayoutManager layoutManager ){
		setRecyclerView(recycler,isStarLayoutAnim,isLoadImage,layoutManager,0);
	}
	public static void setRecyclerView(RecyclerView recycler, boolean isStarLayoutAnim, boolean isLoadImage, RecyclerView.LayoutManager layoutManager, final int viewId){
		if(viewId>0){
//			recycler.setRecyclerListener(new RecyclerView.RecyclerListener() {
//				@Override
//				public void onViewRecycled(RecyclerView.ViewHolder holder) {
//					if(holder instanceof BaseViewHolder){
//						View view = ((BaseViewHolder) holder).getView(viewId);
//						ImageLoadUtil.clear(view);
//					}
//				}
//			});
		}
		recycler.setHasFixedSize(true);
		recycler.setLayoutManager(layoutManager);
		if(isStarLayoutAnim){
			TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					0f, Animation.RELATIVE_TO_SELF, 6f, Animation.RELATIVE_TO_SELF, 0);
			animation.setInterpolator(new DecelerateInterpolator());
			animation.setDuration(350);
			animation.setStartOffset(150);
			LayoutAnimationController lac = new LayoutAnimationController(animation, 0.12f);
			lac.setInterpolator(new DecelerateInterpolator());
			recycler.setLayoutAnimation(lac);
		}
		if(isLoadImage){
			recycler.addOnScrollListener(new OnRecyclerPauseListen( true, true));
		}
	}
	public static void setRecyclerView(RecyclerView recycler, boolean isStarLayoutAnim, boolean isLoadImage){
		setRecyclerView(recycler,isStarLayoutAnim,isLoadImage,0);
	}

	public static void setRecyclerView(RecyclerView recycler, boolean isStarLayoutAnim, boolean isLoadImage, int viewId){
		LinearLayoutManager layoutManager = new LinearLayoutManager(recycler.getContext());
		setRecyclerView(recycler,isStarLayoutAnim,isLoadImage,layoutManager,viewId);
	}



	public final static Drawable getDrawable(@DrawableRes int resID){


		return ContextCompat.getDrawable(UIApp.sContext,resID);
	}
	public final static int getColor(@ColorRes int resID){

		return ContextCompat.getColor(UIApp.sContext,resID);
	}

	public final static Resources getRes(){
		return UIApp.sContext.getResources();
	}

	/**
	 * dip转px像素
	 *
	 * @return
	 */
	public static int dip2px(float dps) {
//        final float scale = getScreenDensity(context);
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dps,Resources.getSystem().getDisplayMetrics());
//		return Math.round( getRes().getDisplayMetrics().density * dps);
	}

//
//
//
//	public static String getStr(int resId){
//		return getRes().getString(resId);
//	}

	public static ViewGroup.LayoutParams getWLayoutParams() {
		return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

//	public static ListPopupWindow getListPopupWindow(Context a,View view){
//
//		ListPopupWindow listPopupWindow = new ListPopupWindow(a);
//		listPopupWindow.setAnchorView(view);
//		listPopupWindow.setWidth(dip2px(100));
//		listPopupWindow.setAdapter(new TitleMenuAdapter());
//		listPopupWindow.setOnItemClickListener((AdapterView<?> adapterView, View v, int i, long l) ->{
//			listPopupWindow.dismiss();
//			switch (i){
//				case 0:
//					Intent intent = new Intent();
//					intent.setClassName(PACKAGE,"com.kinbong.jbcclient.main.MainActivity");
//					a.startActivity(intent);
//					break;
//				case 1:
//					break;
//				case 2:
//					Intent intent2 = new Intent();
//					intent2.setClassName(PACKAGE,"com.kinbong.jbcclient.main.MainActivity");
//					intent2.putExtra("page",3);
//					a.startActivity(intent2);
//					break;
//				case 3:
//					break;
//				case 4:
//					break;
//				default:break;
//			}
//
//		});
//		return listPopupWindow;
//	}

	public static void setBackground(View view, Drawable gd) {
		ViewCompat.setBackground(view,gd);
	}

	public static ViewGroup.LayoutParams getMLayoutParams() {
		return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	}

	public static View getVsView(View rootView,@IdRes int vsId) {
		ViewStub vs = (ViewStub) rootView.findViewById(vsId);
		return vs.inflate();
	}

	public static void setEtLength(EditText et, int i) {
		et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
	}

    public static  <T> T getWeakReference(WeakReference<T> wr) {
		if(wr==null)return null;
		return wr.get();
    }

    public static String[] getArrayRes(@ArrayRes int resId) {
        return getRes().getStringArray(resId);
    }


    public static class LoadingPopupWindow extends PopupWindow {
		public LoadingPopupWindow(Activity activity) {
			super(activity.getLayoutInflater().inflate(R.layout.loading, null), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, false);
		}

	}
	/**
	 * 隐藏对话框
	 *
	 * @param popupWindow
	 */
	public static void hidePop(final PopupWindow popupWindow) {
		if (popupWindow != null) {
			popupWindow.getContentView().post(()-> {
					if (popupWindow != null && popupWindow.isShowing())
						try {
							popupWindow.dismiss();
						} catch (Exception e) {
						}

			});
		}
	}

	/**
	 * 初始化进度条dialog
	 *
	 * @param activity
	 * @return
	 */
	public static LoadingPopupWindow initPop(Activity activity, PopupWindow.OnDismissListener onDismissListener) {
		if (activity == null || activity.isFinishing()) {
			return null;
		}

		// 获得背景（6个图片形成的动画）
		//AnimationDrawable animDance = (AnimationDrawable) imgDance.getBackground();

		//final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		final LoadingPopupWindow popupWindow = new LoadingPopupWindow(activity);
		ColorDrawable cd = new ColorDrawable(Color.TRANSPARENT);
		popupWindow.setBackgroundDrawable(cd);
		popupWindow.setTouchable(true);
		popupWindow.setOnDismissListener(onDismissListener);

		popupWindow.setFocusable(true);
		//animDance.start();
		return popupWindow;
	}


	public final static boolean isVisible(View paramView)
	{
		return (paramView != null) && (paramView.getMeasuredHeight() > 0) && (paramView.getMeasuredWidth() > 0);
	}

	public final static PopupWindow getPopwindow(View view){
		PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		ColorDrawable cd = new ColorDrawable(Color.TRANSPARENT);
		popupWindow.setBackgroundDrawable(cd);
		popupWindow.update();
		return popupWindow;
	}

	public static String getTextStr(TextView tv){
		if(tv==null)return null;
		return tv.getText().toString();
	}

}
