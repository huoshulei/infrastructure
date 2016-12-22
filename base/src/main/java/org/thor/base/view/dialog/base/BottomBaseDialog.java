package org.thor.base.view.dialog.base;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import org.thor.base.view.dialog.animation.BaseAnimatorSet;


public abstract class BottomBaseDialog<T extends BottomBaseDialog<T>> extends BaseDialog<T> {
    private View mAnimateView;
    private BaseAnimatorSet mWindowInAs;
    private BaseAnimatorSet mWindowOutAs;
    private Animation mInnerShowAnim;
    private Animation mInnerDismissAnim;
    private long mInnerAnimDuration = 350;
    private boolean mIsInnerShowAnim;
    private boolean mIsInnerDismissAnim;
    private int     mLeft, mTop, mRight, mBottom;

    public BottomBaseDialog(Context context) {
        this(context, null);
    }

    protected BottomBaseDialog(Context context, View animateView) {
        super(context);
        mAnimateView = animateView;

        mInnerShowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);

        mInnerDismissAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
    }
    @SuppressWarnings("unchecked")
    public T padding(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
        return (T) this;
    }

    /**
     * s(设置dialog和animateView显示动画)
     */
    private void showWithAnim() {
        if (mInnerShowAnim != null) {
            mInnerShowAnim.setDuration(mInnerAnimDuration);
            mInnerShowAnim.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mIsInnerShowAnim = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mIsInnerShowAnim = false;
                }
            });
            mLlControlHeight.startAnimation(mInnerShowAnim);
        }

        if (mAnimateView != null) {
            if (getWindowInAs() != null) {
                mWindowInAs = getWindowInAs();
            }
            mWindowInAs.duration(mInnerAnimDuration).playOn(mAnimateView);
        }
    }

    /**
     * (设置dialog和animateView消失动画)
     */
    private void dismissWithAnim() {
        if (mInnerDismissAnim != null) {
            mInnerDismissAnim.setDuration(mInnerAnimDuration);
            mInnerDismissAnim.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mIsInnerDismissAnim = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mIsInnerDismissAnim = false;
                    BottomBaseDialog.super.dismiss();
                }
            });

            mLlControlHeight.startAnimation(mInnerDismissAnim);
        } else {
            super.dismiss();
        }

        if (mAnimateView != null) {
            if (getWindowOutAs() != null) {
                mWindowOutAs = getWindowOutAs();
            }
            mWindowOutAs.duration(mInnerAnimDuration).playOn(mAnimateView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        return mIsInnerDismissAnim || mIsInnerShowAnim || super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (mIsInnerDismissAnim || mIsInnerShowAnim) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mLlTop.setGravity(Gravity.BOTTOM);
        getWindow().setGravity(Gravity.BOTTOM);
        mLlTop.setPadding(mLeft, mTop, mRight, mBottom);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showWithAnim();
    }

    @Override
    public void dismiss() {
        dismissWithAnim();
    }

    private BaseAnimatorSet getWindowInAs() {
        if (mWindowInAs == null) {
            mWindowInAs = new WindowInAs();
        }
        return mWindowInAs;
    }


    private BaseAnimatorSet getWindowOutAs() {
        if (mWindowOutAs == null) {
            mWindowOutAs = new WindowOutAs();
        }
        return mWindowOutAs;
    }


    private class WindowInAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.9f);
            ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.9f);
            animatorSet.playTogether(oa1, oa2);
        }
    }

    private class WindowOutAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1f);
            ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1f);
            animatorSet.playTogether(oa1, oa2);
        }
    }
}
