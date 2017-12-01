
package com.example.administrator.test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zuky on 2016/10/14.
 * <p>Description: Base的RecyclerView Adapter抽象类</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */
public abstract class BaseRvAdapter<BH extends BaseRvAdapter.ViewHolder>
        extends RecyclerView.Adapter<BH> implements View.OnClickListener, View.OnLongClickListener {

    private OnRvItemClickListener rvItemClickListener;
    private OnRvItemLongClickListener rvItemLongClickListener;

    @Override
    public BH onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
         * 主要防止在继承BaseAapter时忘记重载onCreateViewHolder(),干脆就加多一个抽象方法来处理业务逻辑
         */
        BH holder = doOnCreateViewHolder(parent, viewType);
        holder.rootView.setOnClickListener(this);
        holder.rootView.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BH holder, int position) {
        holder.rootView.setTag(position);
        /*
         * 主要防止在继承BaseAapter时忘记重载onBindViewHolder(),干脆就加多一个抽象方法来处理业务逻辑
         */
        doOnBindViewHolder(holder, position);
    }

    /**
     * 添加原本onCreateViewHolder()中的逻辑
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BH doOnCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 添加原本onBindViewHolder()中的逻辑
     * @param holder
     * @param position
     */
    protected abstract void doOnBindViewHolder(BH holder, int position);

    /**
     * 主要是弥补RecyclerView中没有的onItemClickListener和onItemClickListener，
     * 要是后续需要开放其他RecyclerView没有的事件可以在这里仿照着添加回调
     */
    public void setRvItemClickListener(OnRvItemClickListener rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
    }

    /**
     * 主要是弥补RecyclerView中没有的onItemClickListener和onItemClickListener，
     * 要是后续需要开放其他RecyclerView没有的事件可以在这里仿照着添加回调
     */
    public void setRvItemLongClickListener(OnRvItemLongClickListener rvItemLongClickListener) {
        this.rvItemLongClickListener = rvItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (rvItemClickListener != null) {
            rvItemClickListener.OnRvItemClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (rvItemLongClickListener != null) {
            return rvItemLongClickListener.OnRvItemLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

    public interface OnRvItemClickListener {
        void OnRvItemClick(View view, int position);
    }

    public interface OnRvItemLongClickListener {
        boolean OnRvItemLongClick(View view, int position);
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }
    }
}
