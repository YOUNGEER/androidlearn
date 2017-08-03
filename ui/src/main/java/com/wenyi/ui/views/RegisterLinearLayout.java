package com.wenyi.ui.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenyi.ui.R;
import com.wenyi.ui.utils.MyViewUtil;
import com.wenyi.ui.views.text.ClearEditText;


/**
 * Created by Administrator on 2016/11/8.
 */

public class RegisterLinearLayout extends LinearLayout {

    private final boolean isRequiredField;
    private final boolean isSingle;
    private String mTitle;
    private String mHintText;
    private TextView tvText;
    private ClearEditText etText;
    private boolean isPassword;
    private int mType;
    private final static int Type_1=0;
    private final static int type_2=1;
    private boolean isDilider;
    private int mMaxLength;
    private int mBg;
    private boolean isNumber;

    public RegisterLinearLayout(Context context) {
        this(context,null);
    }

    public RegisterLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RegisterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray  a= context.obtainStyledAttributes(attrs, R.styleable.RegisterLayout);
        mTitle = a.getString(R.styleable.RegisterLayout_register_title);
        mHintText = a.getString(R.styleable.RegisterLayout_register_hint);
        isPassword = a.getBoolean(R.styleable.RegisterLayout_register_password, false);
        mType = a.getInteger(R.styleable.RegisterLayout_register_type, type_2);
        isDilider = a.getBoolean(R.styleable.RegisterLayout_is_bottom_dilider, false);
        mMaxLength = a.getInteger(R.styleable.RegisterLayout_register_maxlength, 0);
        isRequiredField = a.getBoolean(R.styleable.RegisterLayout_is_required_field, false);
        isNumber = a.getBoolean(R.styleable.RegisterLayout_is_number, false);
        isSingle = a.getBoolean(R.styleable.RegisterLayout_is_single, true);
        mBg = a.getColor(R.styleable.RegisterLayout_bg, Color.WHITE);
        a.recycle();
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        init();
    }


    private void init(){
        setBackgroundColor(mBg);
        if(mType==Type_1)
        inflate(getContext(),R.layout.layout_register_et,this);
        else
            inflate(getContext(),R.layout.layout_register_et2,this);
        tvText = (TextView) findViewById(R.id.tv_text);
        etText = (ClearEditText)findViewById(R.id.et_text);
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if(charSequence.equals(" "))return "";
                return null;
            }
        };
        InputFilter[] filters;
        if(mMaxLength!=0){
            filters=new InputFilter[]{filter, new InputFilter.LengthFilter(mMaxLength)};
        }else {
            filters=new InputFilter[]{filter};
        }

        etText.setFilters(filters);
        if(mTitle!=null)
        tvText.setText(mTitle);
        if(mHintText !=null)etText.setHint(mHintText);


        if(isPassword){
            etText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});//20
            etText.post(()-> etText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD));
        }
        if(isDilider&&mType==type_2)etText.setDividerv(true);
        if(isRequiredField){
            Drawable drawable = MyViewUtil.getDrawable2(R.mipmap.star);
            tvText.setCompoundDrawables(null,null,drawable,null);
        }

        if(isSingle){
            etText.setSingleLine(true);
        }

        if(isNumber){

            etText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
    }

    public void setTitle(String title){
        mTitle=title;
        tvText.setText(title);
    }
    public String getEtText(){
        return etText.getText().toString();
    }
    public void setEtText(String text){
        etText.setText(text);
    }

    public void addTextChangedListener(TextWatcher textWatcher){
        etText.addTextChangedListener(textWatcher);
    }
    public void removeTextChangedListener(TextWatcher textWatcher){
        etText.removeTextChangedListener(textWatcher);
    }


    public ClearEditText getEditText(){
        return etText;
    }

    public TextView getLeftText(){
        return tvText;
    }

}
