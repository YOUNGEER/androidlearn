//package com.shangxiao.ui.views;
//
//import android.content.Context;
//import android.widget.TextView;
//
//import com.shangxiao.ui.R;
//
//
///**
// * Custom implementation of the MarkerView.
// *
// * @author Philipp Jahoda
// */
//public class MyMarkerView extends MarkerView {
//
//    private TextView tvContent;
//
//    public MyMarkerView(Context context, int layoutResource) {
//        super(context, layoutResource);
//
//        tvContent = (TextView) findViewById(R.id.tvContent);
//    }
//
//    // callbacks everytime the MarkerView is redrawn, can be used to update the
//    // content (user-interface)
//    @Override
//    public void refreshContent(Entry e, Highlight highlight) {
//
//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;
//
//            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
//        } else {
//
//            tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
//        }
//
//        super.refreshContent(e, highlight);
//    }
//
//    @Override
//    public MPPointF getOffset() {
//        return new MPPointF(-(getWidth() / 2), -getHeight());
//    }
//}
