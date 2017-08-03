package com.wenyi.ui.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenyi.ui.R;
import com.wenyi.ui.utils.MyViewUtil;


/**
 * Created by Administrator on 2017/1/10.
 */

public class ErrorLayout extends LinearLayout {

    private ImageView iv;
    private TextView tvErrorTitle;

    private Button btn;

    public ErrorLayout(Context context) {
        this(context,null);
    }

    public ErrorLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ErrorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        iv = new ImageView(context);
//        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(iv, MyViewUtil.getWLayoutParams());
        tvErrorTitle = new TextView(context);
        tvErrorTitle.setTextSize(16);
        tvErrorTitle.setTextColor(MyViewUtil.getColor(R.color.title));
        addView(tvErrorTitle,MyViewUtil.getWLayoutParams());
        setMarginTop(context,tvErrorTitle);

        btn = new Button(context);
        btn.setTextColor(MyViewUtil.getColor(R.color.theme));
        btn.setBackgroundResource(R.drawable.sp_btn_stroke_bg);
        addView(btn,new ViewGroup.LayoutParams(MyViewUtil.dip2px(135),MyViewUtil.dip2px(40)));
        setMarginTop(context,btn);
    }

    private void setMarginTop(Context context, View view) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.topMargin= MyViewUtil.dip2px(8);
    }

    public void setTvErrorTitle(int title){
        if(tvErrorTitle!=null)tvErrorTitle.setText(title);
    }


    public void setErrorImage(int resId){
        if(iv!=null)iv.setImageResource(resId);
    }

    public void setBtnText(int text){
        if(btn!=null)btn.setText(text);
    }
    public void setBtnVisibility(int visible){
        if(btn!=null)btn.setVisibility(visible);
    }
    public void setBtnListen(OnClickListener listen){
        if(btn!=null)
            btn.setOnClickListener(listen);
    }



    public  void setErrorlayout(OnClickListener listener, @StringRes int title, @StringRes int btnId, @DrawableRes int imgId) {

        setTvErrorTitle(title);

        setErrorImage(imgId);
        if(listener==null){
           setBtnVisibility(View.GONE);
        }else {
          setBtnText(btnId);
           setBtnListen(listener);
        }

    }

}
