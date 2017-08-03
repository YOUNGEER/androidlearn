package com.wenyi.banner.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Administrator on 2017/1/9.
 */

public abstract class ImageBannerAdapter<T> implements BaseBannerAdapter<T>{
    protected ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void destroyView(View view) {

    }
}
