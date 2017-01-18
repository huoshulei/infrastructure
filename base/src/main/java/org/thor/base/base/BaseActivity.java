package org.thor.base.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.thor.base.App;
import org.thor.base.R;
import org.thor.base.view.OWLoadingView;


public abstract class BaseActivity extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        App.application.addActivities(this);
        int layoutResId = getLayoutResId();
        if (layoutResId == 0) throw new NullPointerException("布局文件不能为空");
        setContentView(layoutResId);
        configView();
        initData();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
                v.clearFocus();
//                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    // 获取InputMethodManager，隐藏软键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // dialog
    private Dialog getDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.loading_dialog);
            OWLoadingView view = new OWLoadingView(this);
            dialog.setContentView(view, new ViewGroup.LayoutParams(100, 100));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        return dialog;
    }

    protected void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    protected void showDialog() {
        getDialog().show();
    }

    protected void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
