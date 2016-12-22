package org.thor.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/10/13 9:39
 * 修改人:    ICOGN
 * 修改时间:  2016/10/13 9:39
 * 备注:
 * 版本:
 */

public class MyViewPager extends ViewPager {
    private boolean isScroll;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll && super.onInterceptTouchEvent(ev);
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

}
