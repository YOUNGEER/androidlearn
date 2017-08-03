package com.wenyi.selectimg.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wenyi.selectimg.R;


/**
 * Created by Administrator on 2016/11/10.
 */

public class Util {

    public final static int getScreenW(Context context){
        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static void displayImage(Context context,String url, ImageView imageView){
        if(!TextUtils.isEmpty(url)){
            Glide.with(context).load(url).placeholder(R.mipmap.bg).error(R.mipmap.bg).into(imageView);
        }else {
            imageView.setImageResource(R.mipmap.bg);
        }

    }

    public static void clear(View view) {
        Glide.clear(view);
    }
}
