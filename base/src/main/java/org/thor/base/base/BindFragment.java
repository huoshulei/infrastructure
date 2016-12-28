//package org.thor.base.base;
//
//
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link RxLifeFragment} subclass.
// */
//public abstract class BindFragment<T extends ViewDataBinding> extends RxLifeFragment {
//
//
//    private T binding;
//
//
//    /**
//     * 关联布局 并获取当前布局文件到databind对象
//     */
//    @Override
//    protected View init(LayoutInflater inflater, ViewGroup container, int layoutResId) {
//        View view = inflater.inflate(layoutResId, container, false);
//        binding = DataBindingUtil.bind(view);
//        return view;
//    }
//
////    /**
////     * 添加数据到布局文件 （与context无关到数据）
////     */
////    protected final <V extends BaseViewModule> void setData(V data) {
////        binding.setVariable(BR.data, data);
////    }
////
////    /**
////     * 添加点击事件到布局文件（与context相关的数据 页面导航 ）
////     *
////     */
////    protected final <V extends BindFragment> void setModule(V data) {
////        binding.setVariable(BR.module, data);
////    }
////
////    /**
////     * 添加其他数据
////     */
////    protected final void setVariable(int variableId, Object data) {
////        binding.setVariable(variableId, data);
////    }
//
//
//    public T getBinding() {
//        return binding;
//    }
//
//}
