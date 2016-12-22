package org.thor.base.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import org.thor.base.App;
import org.thor.base.R;
import org.thor.base.view.OWLoadingView;


public abstract class BaseActivity extends AppCompatActivity {
    private InputMethodManager inputMethodManager;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        App.application.addActivities(this);
        init();
        configView();
        initData();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected void init() {
        int layoutResId = getLayoutResId();
        if (layoutResId == 0) throw new NullPointerException("布局文件不能为空");
        setContentView(layoutResId);
    }


    protected abstract
    @LayoutRes
    int getLayoutResId();

    /**
     * 加载布局
     */
    protected abstract void configView();

    /**
     * 绑定数据
     */
    protected abstract void initData();



    @Override
    protected void onDestroy() {
        App.application.removeActivity(this);
        super.onDestroy();
    }

    public void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */

            return inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
    // dialog
    public Dialog getDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.loading_dialog);
            OWLoadingView view = new OWLoadingView(this);
            dialog.setContentView(view, new ViewGroup.LayoutParams(100, 100));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
