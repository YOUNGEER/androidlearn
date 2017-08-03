package com.wenyi.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.wenyi.ui.R;


/**
 * Created by Administrator on 2016/11/25.
 */

public class PopPhoto extends PopupWindow {


    public PopPhoto(Context context) {
        this( LayoutInflater.from(context).inflate(R.layout.pop_photo, null), ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT, true);

        init(context);
    }

    public PopPhoto(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }
    private void init(Context context){
//        setAnimationStyle(R.style.popup_anim);
        setFocusable(true);
//        popWindow.setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void setOnClickListen(View.OnClickListener listen){
        View view = getContentView();
        view.findViewById(R.id.btn_camera).setOnClickListener(listen);
        view.findViewById(R.id.btn_photo).setOnClickListener(listen);
        view.findViewById(R.id.btn_cancel).setOnClickListener(listen);
    }
}
