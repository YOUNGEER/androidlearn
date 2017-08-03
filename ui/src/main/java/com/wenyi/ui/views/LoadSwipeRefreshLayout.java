package com.wenyi.ui.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wenyi.recycler.LoadMoreListener;
import com.wenyi.recycler.LoadRecyclerView;
import com.wenyi.recycler.base.BaseSuperAdapter;
import com.wenyi.ui.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public class LoadSwipeRefreshLayout extends MySwipeRefreshLayout implements Runnable, LoadMoreListener,ILoadLayout{

    private LoadRecyclerView recycler;
    private BaseSuperAdapter mAdapter;

    public LoadSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public LoadSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
//        setProgressViewEndTarget(true,200);
//        setDistanceToTriggerSync(200);
        recycler = new LoadRecyclerView(getContext());
        addView(recycler,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //设置自定义加载中和到底了效
        TextView tv = new TextView(recycler.getContext());
        tv.setText(R.string.loading);
        recycler.setFootLoadingView(tv);
        recycler.setHasFixedSize(true);
        TextView textView = new TextView(recycler.getContext());
        textView.setText(R.string.load_end);
        recycler.setFootEndView(textView);
        recycler.setLoadMoreListener(this);

    }
    public LoadSwipeRefreshLayout setRecyclerViewListen(){
        recycler.addOnScrollListener(new OnRecyclerPauseListen(true, true));
        return this;
    }

    public LoadSwipeRefreshLayout setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recycler.setLayoutManager(layoutManager);
        return this;
    }

    @Override
    public void loadEnd(List data,boolean hasMore) {
        if(hasMore){
            recycler.loadMoreComplete();
            mAdapter.addAllAotify(data);
//            recycler.notifyFoot();
        }else {
            if(data!=null&&data.size()!=0)
            mAdapter.addAllAotify(data);
            loadMoreEnd();
        }

    }

    @Override
    public void refreshEnd(List data,boolean hasMore) {
        hide();
        recycler.refreshComplete();
        recycler.setCanloadMore(hasMore);
        mAdapter.refresh(data);
    }






    @Override
    public void onLoadMore() {
        if(mLoadListen!=null){
            mPage++;
            mLoadListen.onLoad(mPage);
        }

    }
    public LoadSwipeRefreshLayout setRecyclerListen(RecyclerView.RecyclerListener listen){
        recycler.setRecyclerListener(listen);
        return this;
    }






    @Override
    public LoadSwipeRefreshLayout setAdapter(BaseSuperAdapter adapter) {
        mAdapter = adapter;
        recycler.setAdapter(adapter);
        return this;
    }

    public void clearLoad(){
        setRefreshing(false);
        recycler.loadMoreComplete();
    }

    public void setLoadMoreEnabled(boolean flag){
       recycler.setCanloadMore(flag);
    }


    public void loadMoreEnd() {
        recycler.setCanloadMore(false);
        recycler.loadMoreEnd();
    }

    public void smoothScrollToPosition(int i) {
        recycler.smoothScrollToPosition(0);
    }
    public LoadRecyclerView getRecyclerView(){
        return recycler;
    }




}
