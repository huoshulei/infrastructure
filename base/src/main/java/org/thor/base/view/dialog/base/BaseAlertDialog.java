package org.thor.base.view.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.thor.base.view.dialog.listener.OnBtnClickL;


@SuppressWarnings("unchecked")
public abstract class BaseAlertDialog<T extends BaseAlertDialog<T>> extends BaseDialog<T> {
    /**
     * container
     */
    protected LinearLayout mLlContainer;
    //title
    /**
     * title
     */
    protected TextView mTvTitle;
    /**
     * title content(标题)
     */
    private String mTitle;
    /**
     * title textcolor(标题颜色)
     */
    protected int          mTitleTextColor;
    /**
     * title textsize(标题字体大小,单位sp)
     */
    protected float        mTitleTextSize;
    /**
     * enable title show(是否显示标题)
     */
    protected boolean mIsTitleShow = true;

    //content
    /**
     * content
     */
    protected TextView mTvContent;
    /**
     * content text
     */
    private String mContent;
    /**
     * show gravity of content(正文内容显示位置)
     */
    protected int mContentGravity = Gravity.CENTER_VERTICAL;
    /**
     * content textcolor(正文字体颜色)
     */
    protected int   mContentTextColor;
    /**
     * content textsize(正文字体大小)
     */
    protected float mContentTextSize;

    //btns
    /**
     * num of btns, [1,3]
     */
    protected int mBtnNum = 2;
    /**
     * btn container
     */
    protected LinearLayout mLlBtns;
    /**
     * btns
     */
    protected TextView mTvBtnLeft;
    protected TextView mTvBtnRight;
    protected TextView mTvBtnMiddle;
    /**
     * btn textcolor(按钮字体颜色)
     */
    protected int mLeftBtnTextColor;
    protected int mRightBtnTextColor;
    protected int mMiddleBtnTextColor;
    /**
     * btn press color(按钮点击颜色)
     */
    protected int   mBtnPressColor     = Color.parseColor("#E3E3E3");// #85D3EF,#ffcccccc,#E3E3E3
    /**
     * left btn click listener(左按钮接口)
     */
    private OnBtnClickL mOnBtnLeftClickL;
    /**
     * right btn click listener(右按钮接口)
     */
    private OnBtnClickL mOnBtnRightClickL;
    /**
     * middle btn click listener(右按钮接口)
     */
    private OnBtnClickL mOnBtnMiddleClickL;

    /**
     * corner radius,dp(圆角程度,单位dp)
     */
    protected float mCornerRadius = 3;
    /**
     * background color(背景颜色)
     */
    protected int   mBgColor      = Color.parseColor("#ffffff");

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     */
    public BaseAlertDialog(Context context) {
        super(context);
        widthScale(0.88f);

        mLlContainer = new LinearLayout(context);
        mLlContainer.setOrientation(LinearLayout.VERTICAL);

        /** title */
        mTvTitle = new TextView(context);

        /** content */
        mTvContent = new TextView(context);

        /**btns*/
        mLlBtns = new LinearLayout(context);
        mLlBtns.setOrientation(LinearLayout.HORIZONTAL);

        mTvBtnLeft = new TextView(context);
        mTvBtnLeft.setGravity(Gravity.CENTER);

        mTvBtnMiddle = new TextView(context);
        mTvBtnMiddle.setGravity(Gravity.CENTER);

        mTvBtnRight = new TextView(context);
        mTvBtnRight.setGravity(Gravity.CENTER);
    }

    @Override
    public void setUiBeforShow() {
        /** title */
        mTvTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);

        mTvTitle.setText(TextUtils.isEmpty(mTitle) ? "温馨提示" : mTitle);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);

        /** content */
        mTvContent.setGravity(mContentGravity);
        mTvContent.setText(mContent);
        mTvContent.setTextColor(mContentTextColor);
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mContentTextSize);
        mTvContent.setLineSpacing(0, 1.3f);

        /**btns*/
        /*
      btn text(按钮内容)
     */
        String mBtnLeftText = "取消";
        mTvBtnLeft.setText(mBtnLeftText);
        String mBtnRightText = "确定";
        mTvBtnRight.setText(mBtnRightText);
        String mBtnMiddleText = "继续";
        mTvBtnMiddle.setText(mBtnMiddleText);

        mTvBtnLeft.setTextColor(mLeftBtnTextColor);
        mTvBtnRight.setTextColor(mRightBtnTextColor);
        mTvBtnMiddle.setTextColor(mMiddleBtnTextColor);

        /*
      btn textsize(按钮字体大小)
     */
        float mLeftBtnTextSize = 15f;
        mTvBtnLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLeftBtnTextSize);
        float mRightBtnTextSize = 15f;
        mTvBtnRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, mRightBtnTextSize);
        float mMiddleBtnTextSize = 15f;
        mTvBtnMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMiddleBtnTextSize);

        if (mBtnNum == 1) {
            mTvBtnLeft.setVisibility(View.GONE);
            mTvBtnRight.setVisibility(View.GONE);
        } else if (mBtnNum == 2) {
            mTvBtnMiddle.setVisibility(View.GONE);
        }

        mTvBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnLeftClickL != null) {
                    mOnBtnLeftClickL.onBtnClick();
                } else {
                    BaseAlertDialog.this.dismiss();
                }
            }
        });

        mTvBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnRightClickL != null) {
                    mOnBtnRightClickL.onBtnClick();
                } else {
                    BaseAlertDialog.this.dismiss();
                }
            }
        });

        mTvBtnMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnMiddleClickL != null) {
                    mOnBtnMiddleClickL.onBtnClick();
                } else {
                    BaseAlertDialog.this.dismiss();
                }
            }
        });
    }

    /**
     * set title text(设置标题内容) @return Ma
     */
    public T title(String title) {
        mTitle = title;
        return (T) this;
    }

    /**
     * set title textcolor(设置标题字体颜色)
     */
    public T titleTextColor(int titleTextColor) {
        mTitleTextColor = titleTextColor;
        return (T) this;
    }

    /**
     * set title textsize(设置标题字体大小)
     */
    public T titleTextSize(float titleTextSize_SP) {
        mTitleTextSize = titleTextSize_SP;
        return (T) this;
    }


    /**
     * set content text(设置正文内容)
     */
    public T content(String content) {
        mContent = content;
        return (T) this;
    }


    /**
     * set btn click listener(设置按钮监听事件)
     * onBtnClickLs size 1, middle
     * onBtnClickLs size 2, left right
     * onBtnClickLs size 3, left right middle
     */
    public T setOnBtnClickL(OnBtnClickL... onBtnClickLs) {
        if (onBtnClickLs.length < 1 || onBtnClickLs.length > 3) {
            throw new IllegalStateException(" range of param onBtnClickLs length is [1,3]!");
        }

        if (onBtnClickLs.length == 1) {
            mOnBtnMiddleClickL = onBtnClickLs[0];
        } else if (onBtnClickLs.length == 2) {
            mOnBtnLeftClickL = onBtnClickLs[0];
            mOnBtnRightClickL = onBtnClickLs[1];
        } else if (onBtnClickLs.length == 3) {
            mOnBtnLeftClickL = onBtnClickLs[0];
            mOnBtnRightClickL = onBtnClickLs[1];
            mOnBtnMiddleClickL = onBtnClickLs[2];
        }
        return (T) this;
    }
}
