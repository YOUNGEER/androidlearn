package com.wenyi.ui;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/24.
 */

public class UIApp {

    public static Context sContext;

    public final static void init(Context application){
        sContext = application;
    }
}
