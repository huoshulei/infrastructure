package org.thor.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import org.thor.base.R;


/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/10/11 10:00
 * 修改人:    ICOGN
 * 修改时间:  2016/10/11 10:00
 * 备注:
 * 版本:
 */

public class MyToolbar extends Toolbar {
    private TextView mTitleTextView;
    private int      mTitleTextSize;
    private int      mTitleTextColor;

    public MyToolbar(Context context) {
        this(context, null);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyToolbar, defStyleAttr, 0);
        mTitleTextColor = a.getColor(R.styleable.MyToolbar_titleColor, Color.BLACK);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.MyToolbar_titleTextSize, 20);
        CharSequence title = a.getText(R.styleable.MyToolbar_titleText);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        } else setTitle("");
    }

    public void setTitleText(CharSequence title) {
        setTitle(title);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle("");
        if (mTitleTextView == null) {
            mTitleTextView = new AppCompatTextView(getContext());
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

            mTitleTextView.setTextSize(mTitleTextSize);
            mTitleTextView.setTextColor(mTitleTextColor);
            addView(mTitleTextView, params);
        }
        if (mTitleTextView != null)
            mTitleTextView.setText(title);
    }


    public void setmTitleTextSize(int mTitleTextSize) {
        this.mTitleTextSize = mTitleTextSize;
    }

    public void setmTitleTextColor(int mTitleTextColor) {
        this.mTitleTextColor = mTitleTextColor;
    }

}
