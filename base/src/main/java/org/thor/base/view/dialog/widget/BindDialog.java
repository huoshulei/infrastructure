package org.thor.base.view.dialog.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import org.thor.base.BR;
import org.thor.base.view.dialog.base.BottomBaseDialog;


/**
 * 项目名称:  shba
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/11/10 11:04
 * 修改人:    ICOGN
 * 修改时间:  2016/11/10 11:04
 * 备注:
 * 版本:
 */

public class BindDialog extends BottomBaseDialog<BindDialog> {
    private ViewDataBinding binding;
    private final View view;

    public BindDialog(Context context, @LayoutRes int resId) {
        super(context);
        view = LayoutInflater.from(context).inflate(resId, null);
        binding = DataBindingUtil.bind(view);
    }

    @Override
    public View onCreateView() {
        return view;
    }

    /*数据绑定*/
    public BindDialog setData(Object data) {
        binding.setVariable(BR.data, data);
        binding.executePendingBindings();
        return this;
    }

    /*事件处理*/
    public BindDialog setHandler(Object handler) {
        binding.setVariable(BR.handler, handler);
        return this;
    }

    @Override
    public void setUiBeforShow() {

    }

}
