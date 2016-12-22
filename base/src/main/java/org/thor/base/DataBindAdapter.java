package org.thor.base;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.io.File;


/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/10/26 11:21
 * 修改人:    ICOGN
 * 修改时间:  2016/10/26 11:21
 * 备注:
 * 版本:
 */
@BindingMethods({
        @BindingMethod(type = NestedScrollView.class, attribute = "android:scrollY", method = "setOnScrollChangeListener"),
        @BindingMethod(type = ImageView.class, attribute = "android:src", method = "setImageResource")
})
public class DataBindAdapter {


    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String uriString) {
        ImageLoader.getInstance().displayImage(uriString, view);
    }

    @BindingAdapter("android:src")
    public static void setImageFile(ImageView view, File file) {
        String url = null;
        if (file != null) url = Uri.fromFile(file).toString();
        ImageLoader.getInstance().displayImage(url, view, circleOptions);
    }

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, Uri uri) {
        String url = null;
        if (uri != null) url = uri.toString();
        ImageLoader.getInstance().displayImage(url, view, circleOptions);
    }


    @BindingAdapter("android:drawableRight")
    public static void setDrawableRight(TextView view, int resId) {
        Drawable drawable = view.getResources().getDrawable(resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawable,
                    drawables[3]);
        }
    }

    @BindingAdapter("android:drawableTop")
    public static void setDrawableTop(TextView view, int resId) {
        Drawable drawable = view.getResources().getDrawable(resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawable, drawables[2],
                    drawables[3]);
        }
    }

    private static DisplayImageOptions circleOptions = new DisplayImageOptions.Builder()
            //默认图片 加载中图片 错误图片 圆角
            .showImageForEmptyUri(R.drawable.user_icon)
            .showImageOnLoading(R.drawable.user_icon)
            .showImageOnFail(R.drawable.user_icon)
            .displayer(new CircleBitmapDisplayer())
            .resetViewBeforeLoading(true)// 在ImageView加载前清除它上面之前的图片
            .build();
}
