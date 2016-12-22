package org.thor.base.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by caihong on 2016/12/9.
 */

public class TextViewUtils {
    public static void setDrawableLeft(TextView view, int resId) {
        Drawable drawable = view.getResources().getDrawable(resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawable, drawables[1], drawables[2],
                    drawables[3]);
        }
    }

    public static void setDrawableTop(TextView view, int resId) {
        Drawable drawable = view.getResources().getDrawable(resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawable, drawables[2],
                    drawables[3]);
        }
    }

    public static void setDrawableRight(TextView view, int resId) {
        Drawable drawable = view.getResources().getDrawable(resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawable,
                    drawables[3]);
        }
    }

    public static void setDrawableBottom(TextView view, int resId) {
        Drawable drawable = view.getResources().getDrawable(resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2],
                    drawable);
        }
    }
}
