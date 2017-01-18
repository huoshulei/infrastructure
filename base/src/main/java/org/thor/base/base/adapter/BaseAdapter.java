package org.thor.base.base.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.thor.base.base.BaseViewModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/11/1 9:28
 * 修改人:    ICOGN
 * 修改时间:  2016/11/1 9:28
 * 备注:
 * 版本:
 */
@Deprecated
 abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
   /* private int            layoutId; //布局文件id
    //    private List mData;
    private List<T>        mData;
    private BaseViewModule viewModule;
    private final int     LOAD_VIEW = 1;
    private       boolean loading   = false;//是否开启加载更多功能
    private View       loadView; //加载更多布局
    private boolean    mLoadingMoreEnable; //是否加载中
    private OnLoadMore loadMore;
    private Context    context;
    private View       mHeader;
    private LinearLayout linearLayout;
    private View       mFooter;
    private View       mEmpty;
    private static final int DEFAULT = 0;
    private static final int LOADING = 1;
    private static final int HEADER  = 2;
    private static final int FOOTER  = 3;
    private static final int EMPTY   = 4;

    public BaseAdapter(@LayoutRes int layoutId, @NonNull BaseViewModule viewModule) {
        if (layoutId == 0) throw new NullPointerException("布局文件不能为空");
        this.layoutId = layoutId;
        this.viewModule = viewModule;
        mData=new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != null && position == 0) return HEADER;
        if (position == mData.size() + getHeaderCount()) {
            if (loading) return LOADING;
            if (mFooter != null) return FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        BaseViewHolder v;
        switch (viewType) {
            case LOADING:
                v = getLoadView(parent);
                break;
            case HEADER:
                v = BaseViewHolder.onCreate(mHeader);
                break;
            case FOOTER:
                v = BaseViewHolder.onCreate(mFooter);
                break;
            case EMPTY:
                v = BaseViewHolder.onCreate(mEmpty);
                break;
            default:
                v = BaseViewHolder.onCreate(parent, layoutId);
                break;
        }
        return v;
    }

    *//**
     * @param parent
     * @return 加载更多布局
     *//*
    private BaseViewHolder getLoadView(ViewGroup parent) {
        return loadView == null ? BaseViewHolder.onCreateLoadView(parent) : BaseViewHolder.onCreate(loadView);
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case LOADING:
                LoadMore();
                break;
            case HEADER:
            case FOOTER:
            case EMPTY:
                break;
            default:
                convert(holder, mData.get(holder.getLayoutPosition() - getHeaderCount()), viewModule);
                break;
        }
    }

    *//**
     * @param holder     viewHolder
     * @param t          data
     * @param viewModule module
     *//*
    protected abstract void convert(BaseViewHolder holder, T t, Object viewModule);


    @Override
    public int getItemCount() {
        int i = loading ? 1 : 0;
        int size = this.mData==null?0:this.mData.size();
        return size + i + this.getHeaderCount() + this.getFooterCount();
    }

    *//**
     * @param loadMore 启用加载更多
     *//*
    public void setLoadMore(OnLoadMore loadMore) {
        this.loadMore = loadMore;
        openLoading();
    }

    *//**
     * 加载更多回调
     *//*
    private void LoadMore() {
        if (loading && mLoadingMoreEnable) {
            mLoadingMoreEnable = false;
            if (loadMore == null) return;
            loadMore.onLoadMore();
        }
    }

    *//**
     * 打开加载更多
     *//*
    private void openLoading() {
        this.loading = true;
    }

    *//**
     * 关闭加载更多
     *//*
    public void closeLoading() {
        loading = false;
        hideLoadView();
    }

    *//**
     * 显示加载更多view
     *//*
    private void showLoadView() {
        if (loadView != null) loadView.setVisibility(View.VISIBLE);
    }

    *//**
     * 隐藏加载更多view
     *//*
    public void hideLoadView() {
        mLoadingMoreEnable = false;
        if (loadView != null) loadView.setVisibility(View.GONE);
    }


    *//**
     * @param data 添加数据
     *//*
    public  void setData(List<T> data) {
        mData = new ArrayList<T>();
        mLoadingMoreEnable = true;
        showLoadView();
        mData.clear();
        mData.addAll(data);
        notifyItemRangeChanged(getHeaderCount(), data.size());
    }

    *//**
     * @param data 添加数据
     *//*
    public  void addData(List<T> data) {
        if (mData == null) mData = new ArrayList<T>();
        int count = mData.size() + getHeaderCount();
        mLoadingMoreEnable = true;
        mData.addAll(data);
        notifyItemRangeChanged(count, data.size());
    }

    *//**
     * @param data 添加数据
     *//*
    public  void addData(T data) {
        if (mData == null) mData = new ArrayList<T>();
        int count = mData.size() + getHeaderCount();
        mLoadingMoreEnable = true;
        mData.add(data);
        notifyItemChanged(count);
    }

    *//**
     * @param data 添加数据
     *//*
    public  void addData(List<T> data, int position) {
        mLoadingMoreEnable = true;
        mData.addAll(data);
        notifyItemRangeChanged(position, data.size());
    }

    *//**
     * @param data 添加数据
     *//*
    public  void addData(T data, int position) {
        mLoadingMoreEnable = true;
        mData.add(position, data);
        notifyItemChanged(position);
    }

    *//**
     * 清空适配器数据
     *//*
    public void clear() {
        if (mData != null) mData.clear();
        mData = null;
        notifyDataSetChanged();
    }

    *//**
     * @param position 删除角标为position的数据
     *//*
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    *//**
     * @param data 删除指定数据
     *//*
    public  void remove(T data) {
        if (mData != null) mData.remove(data);
        notifyDataSetChanged();
    }

    public List getData() {
        return mData;
    }

    public void setHeader(View mHeader) {
        this.mHeader = mHeader;
        linearLayout.addView(mHeader);
    }

    public View getFooter() {
        return mFooter;
    }

    public int getHeaderCount() {
        return this.mHeader == null ? 0 : 1;
    }

    public int getFooterCount() {
        return mFooter == null ? 0 : 1;
    }*/
}
