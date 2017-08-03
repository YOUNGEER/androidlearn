package com.wenyi.ui.views;

import android.support.v7.widget.RecyclerView;


import com.wenyi.recycler.base.BaseSuperAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public interface ILoadLayout {
    void show();
    void hide();
    LoadSwipeRefreshLayout setAdapter(BaseSuperAdapter adapter);
    LoadSwipeRefreshLayout setLayoutManager(RecyclerView.LayoutManager layoutManager);
    void loadEnd(List data, boolean hasMore);
    void refreshEnd(List data, boolean hasMore);
}
