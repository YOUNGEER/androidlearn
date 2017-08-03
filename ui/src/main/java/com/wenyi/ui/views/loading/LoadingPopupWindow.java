package com.wenyi.ui.views.loading;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wenyi.ui.R;


/**
 * Created by Administrator on 2017/3/21.
 */

public class LoadingPopupWindow extends PopupWindow {


    private AnimationDrawable animationDrawable;

    public LoadingPopupWindow(Activity activity) {
        super(activity.getLayoutInflater().inflate(R.layout.layout_loading, null), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, false);


        init();
    }

    private void init() {
        setAnimationStyle(R.style.load_popup_anim);
        ColorDrawable cd = new ColorDrawable(Color.TRANSPARENT);
        ImageView iv_loadding = (ImageView) getContentView().findViewById(R.id.iv_loadding);
        animationDrawable = (AnimationDrawable) iv_loadding.getDrawable();
        animationDrawable.start();
        setBackgroundDrawable(cd);

//        setTouchable(false);
        setOutsideTouchable(false);
//        setTouchable(false);
        setOnDismissListener(()->hide());
    }






    public void hide(){
        if(!isShowing())return;
            dismiss();

        dismissAnim();
    }
    public void destory(){
        hide();
        dismissAnim();
        animationDrawable=null;
    }


    private void dismissAnim(){
        if(animationDrawable!=null&&animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }



}
