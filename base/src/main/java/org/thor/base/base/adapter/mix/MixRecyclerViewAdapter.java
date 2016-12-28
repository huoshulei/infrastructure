package org.thor.base.base.adapter.mix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.*;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/28 10:02.
 */

public class MixRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List           items;
    private TypePool       typePool;
    private Context        mContext;
    private LayoutInflater inflater;
    //    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView   recyclerView;
    private OnScrollLoad   onScrollListener;

    public MixRecyclerViewAdapter() {
        this(new ArrayList());
    }

    public MixRecyclerViewAdapter(@NonNull List items) {
        this(items, new TypePool());
    }

    private MixRecyclerViewAdapter(@NonNull List items, @NonNull TypePool typePool) {
        this.items = items;
        this.typePool = typePool;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView == null) this.recyclerView = recyclerView;
        if (mContext == null) mContext = recyclerView.getContext();
        if (inflater == null) this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return typePool.valueAt(viewType).onCreateViewHolder(parent, inflater);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item = items.get(position);
//        typePool.get(item.getClass()).onBindViewHolder(holder, item);
        typePool.valueAt(holder.getItemViewType()).onBindViewHolder(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return indexOf(items.get(position).getClass());
    }

    /**
     * 注入视图关系数据绑定
     *
     * @param clazz    item
     * @param provider view holder
     */
    public MixRecyclerViewAdapter inject(@NonNull Class<?> clazz, @NonNull ItemViewProvider provider) {
        typePool.inject(clazz, provider);
        return this;
    }

    private int indexOf(@NonNull Class<?> clazz) {
        int index = typePool.indexOf(clazz);
        if (index >= 0) return index;
        throw new RuntimeException("未注入 {className}.class!"
                .replace("{className}", clazz.getSimpleName()));
    }


    /**
     * @param listener 启用加载更多
     */
    public void openLoadMore(OnLoadMoreListener listener) {
        if (recyclerView == null) throw new RuntimeException("先调用setAdapter()方法 然后添加滚动监听");
        onScrollListener = new OnScrollLoad(listener);
        recyclerView.setOnScrollListener(onScrollListener);
    }

    public void setData(List items) {
        clear();
        this.items.addAll(items);
        notifyItemRangeChanged(0, items.size());
    }

    public <T> void setData(T items) {
        clear();
        this.items.add(items);
        notifyItemChanged(0);
    }

    public void addData(List items) {
//        if (this.items.size() <= 0) throw new RuntimeException("首次添加数据请调用setData");
        int position = this.items.size();
        this.items.addAll(items);
        notifyItemRangeChanged(position, items.size());
    }

    public void addData(Object item) {
//        if (this.items.size() <= 0) throw new RuntimeException("首次添加数据请调用setData");
        int position = this.items.size();
        this.items.add(item);
        notifyItemChanged(position);
    }

    public void addData(List items, int position) {
//        if (this.items.size() <= 0) throw new RuntimeException("首次添加数据请调用setData");
        this.items.addAll(items);
        int itemCount = this.items.size();
        notifyItemRangeChanged(position, itemCount);
    }

    public void addData(Object item, int position) {
//        if (this.items.size() <= 0) throw new RuntimeException("首次添加数据请调用setData");
        this.items.add(item);
        int itemCount = this.items.size();
        notifyItemRangeChanged(position, itemCount);
    }

    public void remove(Object item) {
//        if (this.items.size() <= 0) throw new RuntimeException("首次添加数据请调用setData");
        int position = items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    /**
     * 无数据可加载时 关闭加载
     */
    public void closeLoading() {
        onScrollListener.closeLoading();
    }
}
