//package com.shangxiao.ui.views;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//
//import com.shangxiao.ui.R;
//
//import java.util.List;
//
///**
// * Created by HanHailong on 15/10/19.
// */
//public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {
//
//    private final Context mContext;
//    private final List<T> mDataList;
//
//    public TagAdapter(Context context,List<T> list ) {
//        this.mContext = context;
//        mDataList =list;
//    }
//
//    @Override
//    public int getCount() {
//        if(mDataList==null)return 0;
//        return mDataList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        if(mDataList==null)return null;
//        return mDataList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = null;
//        if(convertView==null){
//            view= LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);
//
//        }else {
//            view=convertView;
//        }
//
//        TextView textView= (TextView) view.findViewById(R.id.tv_tag);
//        T t = mDataList.get(position);
//
//        setTagText(textView, t,position);
//        return view;
//    }
//
//    public void setTagText(TextView textView, T t,int position) {
//        if (t instanceof String) {
//            textView.setText((String) t);
//        }
//    }
//
//    public void setDatas(List<T> datas) {
//        mDataList.addAll(datas);
//        notifyDataSetChanged();
//    }
//
//    public void clearDatas(List<T> datas) {
//        mDataList.clear();
//        setDatas(datas);
//    }
//
//    @Override
//    public boolean isSelectedPosition(int position) {
//        if (position % 2 == 0) {
//            return true;
//        }
//        return false;
//    }
//}
