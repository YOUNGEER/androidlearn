package com.wenyi.recycle.meizucontact.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

/**
 * 作者：young on 2017/8/2 16:23
 */

public class MeizuContactItemDecoration extends RecyclerView.ItemDecoration{

    private Paint mPaint;
    private List<ContactBean> mBeans;
    private static final int dividerHeight = 80;
    private Context mContext;
    private final Rect mBounds = new Rect();
    private String tagsStr;

    /**
     * 设置数据
     * @param mBeans
     * @param tagsStr
     */
    public void setDatas(List<ContactBean> mBeans, String tagsStr) {
        this.mBeans = mBeans;
        this.tagsStr = tagsStr;
    }

    /**
     * 构造方法
     * @param mContext
     */
    public MeizuContactItemDecoration(Context mContext) {
        this.mContext = mContext;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 重写方法偏移量设置
     * @param outRect
     * @param view   单个itemview
     * @param parent  recycleview
     * @param state recycleview的一些状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);//获取当前view的位置
        //简单判断
        if (mBeans == null || mBeans.size() == 0 || mBeans.size() <= position || position < 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        if (position == 0) {
            //第一条数据有bar
            outRect.set(0, dividerHeight, 0, 0);//表示只有top偏移
        } else if (position > 0) {
            if (TextUtils.isEmpty(mBeans.get(position).getIndexTag())) return;
            //与上一条数据中的tag不同时，该显示bar了
            if (!mBeans.get(position).getIndexTag().equals(mBeans.get(position - 1).getIndexTag())) {
                outRect.set(0, dividerHeight, 0, 0);
            }
        }
    }

    /**
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        c.save();

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
//            final View child=parent.findViewHolderForLayoutPosition(i).itemView; //该方法会报空指针异常
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//            int position = params.getViewLayoutPosition();
            int position = params.getViewAdapterPosition();
            if (mBeans == null || mBeans.size() == 0 || mBeans.size() <= position || position < 0) {
                continue;
            }
            if (position == 0) {
                //第一条数据有bar
                drawTitleBar(c, parent, child, mBeans.get(position), tagsStr.indexOf(mBeans.get(position).getIndexTag()));
            } else if (position > 0) {
                if (TextUtils.isEmpty(mBeans.get(position).getIndexTag())) continue;
                //与上一条数据中的tag不同时，该显示bar了
                if (!mBeans.get(position).getIndexTag().equals(mBeans.get(position - 1).getIndexTag())) {
                    drawTitleBar(c, parent, child, mBeans.get(position), tagsStr.indexOf(mBeans.get(position).getIndexTag()));
                }
            }
        }
        c.restore();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        //用来绘制悬浮框
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        View child=parent.findViewHolderForLayoutPosition(position).itemView;
//        View child=parent.getChildAt(position);//会报空指针异常
        if (mBeans == null || mBeans.size() == 0 || mBeans.size() <= position || position < 0) {
            return;
        }
        final int bottom = parent.getPaddingTop() + dividerHeight;

        boolean flag = false;//定义一个flag，Canvas是否位移过的标志
        String tag=mBeans.get(position).getIndexTag();
        if(position+1<mBeans.size()){
            if(tag!=null&&!tag.equals(mBeans.get(position+1).getIndexTag())){
                if(child.getHeight()+child.getTop()<dividerHeight){
                    c.save();
                    flag=true;
                    c.translate(0, child.getHeight() + child.getTop() - dividerHeight);
                }
            }
        }

        mPaint.setColor(Color.WHITE);
        //绘制在顶部高divideHeight位置
        c.drawRect(parent.getLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + dividerHeight, mPaint);
        //绘制不同的颜色
        ColorUtil.setPaintColor(mPaint, tagsStr.indexOf(mBeans.get(position).getIndexTag()));
        mPaint.setTextSize(40);
        //绘制圆
        c.drawCircle(DpUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 2, 35, mPaint);
        mPaint.setColor(Color.WHITE);
        //绘制不同的文字
        c.drawText(mBeans.get(position).getIndexTag(), DpUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 3, mPaint);
        if(flag){
            c.restore();
        }
    }

    /**
     * 绘制Titlebar
     *
     * @param canvas Canvas
     * @param parent RecyclerView
     * @param child  ItemView
     */
    private void drawTitleBar(Canvas canvas, RecyclerView parent, View child, ContactBean bean, int position) {
        final int left = child.getLeft()-parent.getPaddingLeft();
        final int right = child.getRight()+parent.getPaddingRight();
        //返回一个包含Decoration和Margin在内的Rect
        parent.getDecoratedBoundsWithMargins(child, mBounds);
        final int top = mBounds.top;
        final int bottom = mBounds.top + Math.round(ViewCompat.getTranslationY(child)) + dividerHeight;
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(left, top, right, bottom, mPaint);
        //根据位置不断变换Paint的颜色
        ColorUtil.setPaintColor(mPaint, position);
        mPaint.setTextSize(40);
        canvas.drawCircle(DpUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 2, 35, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(bean.getIndexTag(), DpUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 3, mPaint);
    }
}
