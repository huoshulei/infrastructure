//package org.thor.base.base;
//
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//
//
//public abstract class BindActivity<T extends ViewDataBinding> extends RxLifeActivity {
//
//    private T binding;
//
//
//
//    @Override
//    protected void init() {
//        int layoutResId = getLayoutResId();
//        if (layoutResId == 0) throw new NullPointerException("布局文件不能为空");
//        binding = DataBindingUtil.setContentView(this, layoutResId);
//    }
//
////    protected final <V extends BaseViewModule>void setData(V data) {
////        binding.setVariable(BR.data, data);
////    }
////
////    protected final <V extends BindActivity> void setModule(V data) {
////        binding.setVariable(BR.module, data);
////    }
////
////
////    protected final void setVariable(int variableId, Object data) {
////        binding.setVariable(variableId, data);
////    }
//
//
//    public T getBinding() {
//        return binding;
//    }
//}
