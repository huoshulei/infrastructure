package org.thor.base.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/9/20 15:54
 * 修改人:    ICOGN
 * 修改时间:  2016/9/20 15:54
 * 备注:
 * 版本:
 */

public class RoundImageView extends ImageView {
    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setImageDrawable(createCircleImage(getDrawable()));
        super.onDraw(canvas);
    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param drawable
     * @return
     */
    private Drawable createCircleImage(Drawable drawable) {
        Bitmap      source = ((BitmapDrawable) drawable).getBitmap();
        final Paint paint  = new Paint();
        paint.setAntiAlias(true);
        int    width  = source.getWidth();
        int    height = source.getHeight();
        int    min    = Math.min(width, height);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */Canvas canvas = new Canvas(target);

        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        drawable = new BitmapDrawable(getResources(), target);
        return drawable;

    }
}
