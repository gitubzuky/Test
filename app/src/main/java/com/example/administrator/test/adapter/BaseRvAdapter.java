package com.example.administrator.test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/10/14.
 */
public abstract class BaseRvAdapter<BH extends BaseRvAdapter.ViewHolder> extends RecyclerView.Adapter<BH> {

    private OnRvItemClickListener rvItemClickListener;
    private OnRvItemLongClickListener rvItemLongClickListener;
    @Override
    public void onBindViewHolder(BH holder, final int position) {
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvItemClickListener.OnRvItemClick(v, position);
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                rvItemLongClickListener.OnRvItemLongClick(v, position);
                return true;
            }
        });
         /*
          主要防止在继承BaseAapter时忘记重载onBindViewHolder(),干脆就加多一个抽象方法来处理业务逻辑
          */
        doinBindViewHolder(holder, position);
    }

    protected abstract void doinBindViewHolder(BH holder, int position);

    /*
       主要是弥补RecyclerView中没有的onItemClickListener和onItemClickListener，
       要是后续需要开放其他RecyclerView没有的事件可以在这里仿照着添加回调
     */
    public void setRvItemClickListener(OnRvItemClickListener rvItemClickListener) {
        if(rvItemClickListener != null){
            this.rvItemClickListener = rvItemClickListener;
        }else{
        }
    }

    public void setRvItemLongClickListener(OnRvItemLongClickListener rvItemLongClickListener) {
        if(rvItemLongClickListener != null){
            this.rvItemLongClickListener = rvItemLongClickListener;
        }else{

        }
    }

    public interface OnRvItemClickListener {
        void OnRvItemClick(View view, int position);
    }

    public interface OnRvItemLongClickListener {
        void OnRvItemLongClick(View view, int position);
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder{
        View rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }
    }
}
