package org.thor.base.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.thor.base.R;
import org.thor.base.BR;

/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/11/1 9:29
 * 修改人:    ICOGN
 * 修改时间:  2016/11/1 9:29
 * 备注:
 * 版本:
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;
    private RecyclerView recyclerView;

    private BaseViewHolder(View loadView) {
        super(loadView);
        binding = null;
    }

    private BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public BaseViewHolder setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);
        return this;
    }

    public BaseViewHolder setLayoutManger(RecyclerView.LayoutManager layoutManger) {
        if (recyclerView == null) return this;
        recyclerView.setLayoutManager(layoutManger);
        return this;
    }

    public BaseViewHolder setAdapter(BaseAdapter adapter) {
        if (recyclerView == null) return this;
        recyclerView.setAdapter(adapter);
        return this;
    }

    public BaseViewHolder setAdapter(BaseAdapterT adapter) {
        if (recyclerView == null) return this;
        recyclerView.setAdapter(adapter);
        return this;
    }

    /**
     * @param view 根据view创建item布局
     * @return
     */
    static BaseViewHolder onCreate(View view) {
        return new BaseViewHolder(view);
    }

    /**
     * @param parent
     * @param layoutId
     * @return 创建默认布局
     */
    static BaseViewHolder onCreate(ViewGroup parent, int layoutId) {
        return new BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                layoutId, parent, false));
    }

    /**
     * @param parent
     * @return 创建加载布局
     */
    static BaseViewHolder onCreateLoadView(ViewGroup parent) {
        return new BaseViewHolder((LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loading_view, parent, false)));
    }

    /**
     * @return 获取item对象
     */
    public View getItemView() {
        return itemView;
    }

    /**
     * @return 获取data bind对象
     */
    public ViewDataBinding getBinding() {
        return binding;
    }

    /**
     * @param id  variableId
     * @param t   variable
     */
    public <T> BaseViewHolder setVariable(int id, T t) {
        binding.setVariable(id, t);
        binding.executePendingBindings();
        return this;
    }

    /**
     * @param handler 事件处理
     */
    public <T> BaseViewHolder setHandler(T handler) {
        binding.setVariable(BR.handler, handler);
        binding.executePendingBindings();
        return this;
    }

    /**
     * @param data 视图数据
     */
    public <T> BaseViewHolder setData(T data) {
        binding.setVariable(BR.data, data);
        binding.executePendingBindings();
        return this;
    }
}
