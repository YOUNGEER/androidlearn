package com.wenyi.ui.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenyi.recycler.LoadMoreListener;
import com.wenyi.recycler.LoadRecyclerView;
import com.wenyi.recycler.base.BaseSuperAdapter;
import com.wenyi.refresh.PullRefreshLayout;
import com.wenyi.ui.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyPullRefreshLayout extends PullRefreshLayout implements LoadMoreListener, PullRefreshLayout.OnRefreshListener {
    private LoadRecyclerView recycler;
    private BaseSuperAdapter mAdapter;
    private OnLoadListen mLoadListen;
    private int mPage;
    private final static int INIT_PAGE=1;
    public MyPullRefreshLayout(Context context) {
        super(context);
        init();
    }

    public MyPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
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
        setOnRefreshListener(this);
    }


    public void setRecyclerViewListen(){
        recycler.addOnScrollListener(new OnRecyclerPauseListen(true, true));
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        if(recycler!=null)
        recycler.setLayoutManager(layoutManager);
    }


    public void loadEnd(List data, boolean hasMore) {
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


    public void refreshEnd(List data,boolean hasMore) {
        setRefreshing(false);
        recycler.refreshComplete();
        recycler.setCanloadMore(hasMore);
        mAdapter.refresh(data);
    }






    public void onLoadMore() {
        if(mLoadListen!=null){
            mPage++;
            mLoadListen.onLoad(mPage);
        }

    }
    public void setRecyclerListen(RecyclerView.RecyclerListener listen){
        recycler.setRecyclerListener(listen);
    }







    public void setAdapter(BaseSuperAdapter adapter) {
        mAdapter = adapter;
        recycler.setAdapter(adapter);
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

    /**
     * 获取第一条展示的位置
     *
     * @return
     */
    public int getFirstVisiblePosition() {
        int position;
        if (recycler.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recycler.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) recycler.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recycler.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recycler.getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }


    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }


    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (recycler.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recycler.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) recycler.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recycler.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)recycler. getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = recycler.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }
    /**
     * 切换layoutManager
     *
     * 为了保证切换之后页面上还是停留在当前展示的位置，记录下切换之前的第一条展示位置，切换完成之后滚动到该位置
     * 另外切换之后必须要重新刷新下当前已经缓存的itemView，否则会出现布局错乱（俩种模式下的item布局不同），
     * RecyclerView提供了swapAdapter来进行切换adapter并清理老的itemView cache
     *
     * @param layoutManager
     */
    public void switchLayoutManager(RecyclerView.LayoutManager layoutManager) {
        int firstVisiblePosition = getFirstVisiblePosition();
//        getLayoutManager().removeAllViews();
        setLayoutManager(layoutManager);
        recycler.swapAdapter(mAdapter,true);
        recycler.getLayoutManager().scrollToPosition(firstVisiblePosition);
    }


    public void setLoadListen(OnLoadListen listen){
        mLoadListen = listen;
    }

    @Override
    public void onRefresh() {

    }


    public interface OnLoadListen{
        void onLoad(int page);
    }
}

