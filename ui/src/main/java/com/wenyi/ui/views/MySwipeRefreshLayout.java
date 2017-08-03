package com.wenyi.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.wenyi.refresh.PullRefreshLayout;
import com.wenyi.ui.utils.MyViewUtil;


/**
 * Created by Administrator on 2016/11/18.
 */

public class MySwipeRefreshLayout extends PullRefreshLayout implements Runnable, PullRefreshLayout.OnRefreshListener {
    private boolean isShow;
    protected int mPage;
    protected OnLoadListen mLoadListen;
    private final static int INIT_PAGE=1;
    public MySwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }
    private void init(){
//        MyViewUtil.setRefreshView(this);
//        setOnRefreshListener(this);
        setOnRefreshListener(this);
    }

    public void show()
    {
        if (MyViewUtil.isVisible(this))
        {
            setRefreshing(true);
            return;
        }
        isShow=true;
        postDelayed(this, 300L);
    }

    @Override
    public void run() {
        if(isShow)onRefresh();
        setRefreshing(isShow);
    }


    public void hide(){
        isShow=false;
        postDelayed(this, 500L);
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setRefreshing(false);
        removeCallbacks(this);
    }
    @Override
    public void onRefresh() {

        mPage=INIT_PAGE;
        if(mLoadListen!=null){
            mLoadListen.onLoad(mPage);
        }
    }

    public MySwipeRefreshLayout setLoadListen(OnLoadListen listen){
        mLoadListen = listen;
        return this;
    }


    public interface OnLoadListen{
        void onLoad(int page);
    }
}
