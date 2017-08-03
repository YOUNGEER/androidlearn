package com.wenyi.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenyi.ui.R;
import com.wenyi.ui.utils.MyViewUtil;


/**
 * Created by Administrator on 2016/11/18.
 */

public class SelLinearLayout extends LinearLayout {
    private final String mTitle;
    private final boolean isRequiredField;
    private final boolean isSingle;
    private final String mHint;

    private TextView tv_text;
    private boolean isShowArrow;

    public SelLinearLayout(Context context) {
        this(context,null);
    }

    public SelLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a= context.obtainStyledAttributes(attrs, R.styleable.RegisterLayout);
        mTitle = a.getString(R.styleable.RegisterLayout_register_title);

        mHint = a.getString(R.styleable.RegisterLayout_register_hint);
        isShowArrow = a.getBoolean(R.styleable.RegisterLayout_is_show_arrow, true);
        isRequiredField = a.getBoolean(R.styleable.RegisterLayout_is_required_field, false);

        isSingle = a.getBoolean(R.styleable.RegisterLayout_is_single, true);

        a.recycle();
        init(context);
    }
    private void init(Context context){
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.list_item_selector);
        inflate(getContext(), R.layout.layout_sel_item,this);

        TextView tv_title=  (TextView) findViewById(R.id.tv_title);
        tv_title.setText(mTitle);
        tv_text = (TextView) findViewById(R.id.tv_text);
        if(isShowArrow){
            Drawable drawable = MyViewUtil.getDrawable2(R.mipmap.right_arrow);
            tv_text.setCompoundDrawables(null,null,drawable,null);
        }
        if(isRequiredField){
            Drawable drawable = MyViewUtil.getDrawable2(R.mipmap.star);
            tv_title.setCompoundDrawables(null,null,drawable,null);
        }

        if(isSingle){
            tv_text.setSingleLine(true);
            tv_text.setEllipsize(TextUtils.TruncateAt.END);
        }
        tv_text.setHint(mHint);

    }

    public void setMyText(String str){
        tv_text.setText(str);
    }

    public String getMyText(){
       return tv_text.getText().toString();
    }
    public TextView getMyTextView(){
       return tv_text;
    }
    public void setMyTextHint(String str){
        tv_text.setHint(str);
    }
}
