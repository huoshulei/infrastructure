package org.thor.base.view.dialog.animation;

import android.animation.AnimatorSet;
import android.view.View;

public abstract class BaseAnimatorSet {
    /**
     * 动画时长,系统默认250
     */
    private   long        duration    = 500;
    protected AnimatorSet animatorSet = new AnimatorSet();

    public abstract void setAnimation(View view);

    protected void start(final View view) {
        /** 设置动画中心点:pivotX--->X轴方向动画中心点,pivotY--->Y轴方向动画中心点 */
        reset(view);
        setAnimation(view);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    public static void reset(View view) {
        view.setAlpha(1);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setTranslationX(0);
        view.setTranslationY(0);
        view.setRotation(0);
        view.setRotationY(0);
        view.setRotationX(0);
    }

    /**
     * 设置动画时长
     */
    public BaseAnimatorSet duration(long duration) {
        this.duration = duration;
        return this;
    }


    /**
     * 在View上执行动画
     */
    public void playOn(View view) {
        start(view);
    }
}
