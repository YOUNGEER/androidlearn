package com.wenyi.ui.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

/**
 * Created by 95 on 2016/1/5.
 */
public class OnRecyclerPauseListen extends RecyclerView.OnScrollListener {
    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final RecyclerView.OnScrollListener externalListener;

    public OnRecyclerPauseListen( boolean pauseOnScroll, boolean pauseOnFling) {
        this(pauseOnScroll, pauseOnFling, (RecyclerView.OnScrollListener)null);
    }

    public OnRecyclerPauseListen( boolean pauseOnScroll, boolean pauseOnFling, RecyclerView.OnScrollListener customListener) {
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = customListener;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        Context context = recyclerView.getContext();
        if(context==null)return;
        if(context instanceof Activity){
            if(((Activity) context).isFinishing())return;
        }
        switch(newState) {
            case 0:
                Glide.with(context).resumeRequests();
                break;
            case 1:
                if(this.pauseOnScroll) {
                    Glide.with(context).pauseRequests();
                }
                break;
            case 2:
                if(this.pauseOnFling) {
                    Glide.with(context).pauseRequests();
                }
                break;
        }

        if(this.externalListener != null) {
            this.externalListener.onScrollStateChanged(recyclerView, newState);
        }

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(this.externalListener != null) {
            this.externalListener.onScrolled(recyclerView,dx,dy);
        }

    }


}
