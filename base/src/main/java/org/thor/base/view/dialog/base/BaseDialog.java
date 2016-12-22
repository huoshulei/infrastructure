package org.thor.base.view.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import org.thor.base.view.dialog.animation.BaseAnimatorSet;
import org.thor.base.view.dialog.utils.StatusBarUtils;


public abstract class BaseDialog<T extends BaseDialog<T>> extends Dialog {
    /**
     * mContext(上下文)
     */
    protected Context mContext;
    /**
     * (DisplayMetrics)设备密度
     */
    private DisplayMetrics mDisplayMetrics;
    /**
     * enable dismiss outside dialog(设置点击对话框以外区域,是否dismiss)
     */
    private   boolean        mCancel;
    /**
     * dialog width scale(宽度比例)
     */
    private float mWidthScale = 1;
    /**
     * dialog height scale(高度比例)
     */
    private float mHeightScale;

    /**
     * top container(最上层容器)
     */
    LinearLayout mLlTop;
    /**
     * container to control dialog height(用于控制对话框高度)
     */
    LinearLayout mLlControlHeight;
    /**
     * is mShowAnim running(显示动画是否正在执行)
     */
    /**
     * is DismissAnim running(消失动画是否正在执行)
     */
    /**
     * max height(最大高度)
     */
    private float mMaxHeight;

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     */
    public BaseDialog(Context context) {
        super(context);
        setDialogTheme();
        mContext = context;
        setCanceledOnTouchOutside(true);
    }

    public T showDialog() {
        super.show();
        return (T) this;
    }

    /**
     * set dialog theme(设置对话框主题)
     */
    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
        getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
    }

    /**
     * inflate layout for dialog ui and return (填充对话框所需要的布局并返回)
     * <pre>
     * public View onCreateView() {
     *      View inflate = View.inflate(mContext, R.layout.dialog_share, null);
     *      return inflate;
     * }
     * </pre>
     */
    public abstract View onCreateView();

    private void onViewCreated(View inflate) {
    }

    /**
     * set Ui data or logic opreation before attatched window(在对话框显示之前,设置界面数据或者逻辑)
     */
    public abstract void setUiBeforShow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        mMaxHeight = mDisplayMetrics.heightPixels - StatusBarUtils.getHeight(mContext);
        // mMaxHeight = mDisplayMetrics.heightPixels;

        mLlTop = new LinearLayout(mContext);
        mLlTop.setGravity(Gravity.CENTER);

        mLlControlHeight = new LinearLayout(mContext);
        mLlControlHeight.setOrientation(LinearLayout.VERTICAL);

        /*
      the child of mLlControlHeight you create.(创建出来的mLlControlHeight的直接子View)
     */
        View mOnCreateView = onCreateView();
        mLlControlHeight.addView(mOnCreateView);
        mLlTop.addView(mLlControlHeight);
        onViewCreated(mOnCreateView);


        setContentView(mLlTop, new ViewGroup.LayoutParams(mDisplayMetrics.widthPixels, (int) mMaxHeight));


        mLlTop.setOnClickListener(v -> {
            if (mCancel) {
                dismiss();
            }
        });

        mOnCreateView.setClickable(true);
    }


    /**
     * when dailog attached to window,set dialog width and height and show anim
     * (当dailog依附在window上,设置对话框宽高以及显示动画)
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setUiBeforShow();

        int width;
        if (mWidthScale == 0) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            width = (int) (mDisplayMetrics.widthPixels * mWidthScale);
        }

        int height;
        if (mHeightScale == 0) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (mHeightScale == 1) {
//            height = ViewGroup.LayoutParams.MATCH_PARENT;
            height = (int) mMaxHeight;
        } else {
            height = (int) (mMaxHeight * mHeightScale);
        }

        mLlControlHeight.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        BaseAnimatorSet.reset(mLlControlHeight);

    }


    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        this.mCancel = cancel;
        super.setCanceledOnTouchOutside(cancel);
    }


    /**
     * dialog anim by styles(动画弹出对话框,style动画资源)
     */
    public void show(int animStyle) {
        Window window = getWindow();
        assert window != null;
        window.setWindowAnimations(animStyle);
        showDialog();
    }


    /**
     * set dialog width scale:0-1(设置对话框宽度,占屏幕宽的比例0-1)
     */
    protected T widthScale(float widthScale) {
        this.mWidthScale = widthScale;
        return (T) this;
    }

    /**
     * set dialog height scale:0-1(设置对话框高度,占屏幕宽的比例0-1)
     */
    public T heightScale(float heightScale) {
        mHeightScale = heightScale;
        return (T) this;
    }

    /**
     * dp to px
     */
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
