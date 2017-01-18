package org.thor.base.base;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.thor.base.R;
import org.thor.base.view.OWLoadingView;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
    private   View     view;
    protected Activity activity;
    private   Dialog   dialog;
    private boolean isFirstVisible = true;

    protected abstract
    @LayoutRes
    int getLayoutResId();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            int layoutResId = getLayoutResId();
            if (layoutResId == 0) throw new NullPointerException("布局文件不能为空");
            view = inflater.inflate(layoutResId, container, false);
            configView(view);
            initEvent();
            initData();
        }
        return view;
    }

    /**
     * 初始化布局组件
     */
    protected void configView(View view) {

    }

    /**
     * 触控事件
     */
    protected abstract void initEvent();

    /*数据初始化*/
    protected void initData() {

    }

    /**
     * 首次可见加载网络数据
     */
    protected void onFirstVisible() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstVisible();
            }
        }
    }

    // dialog
    private Dialog getDialog() {
        if (dialog == null) {
            dialog = new Dialog(activity, R.style.loading_dialog);
            dialog.setContentView(new OWLoadingView(activity), new ViewGroup.LayoutParams(100, 100));
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
