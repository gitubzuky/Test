
package com.example.administrator.test.adapter.widgettest;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.administrator.test.R;
import com.example.administrator.test.adapter.OnVLayoutItemClickListener;
import com.example.administrator.test.adapter.OnVLayoutItemLongClickListener;

/**
 * Created by Zuky on 2017/10/23.
 * <p>Description: 控件测试页面逻辑</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */

public class WidgetLinearAdapter
        extends DelegateAdapter.Adapter<WidgetLinearAdapter.LinearViewHolder> {
    Activity act;
    private OnVLayoutItemClickListener onItemClickListener;
    private OnVLayoutItemLongClickListener onItemLongClickListener;

    public WidgetLinearAdapter(Activity act) {
        this.act = act;
    }

    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(
                LayoutInflater.from(act).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tvContent.setText(position + "");
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    public void setOnItemClickListener(OnVLayoutItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnVLayoutItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    // class MainViewHolder extends RecyclerView.ViewHolder {
    // public ImageView iv;
    // public TextView tvTitle;
    // public TextView tvContent;
    //
    // public MainViewHolder(View itemView) {
    // super(itemView);
    // iv = (ImageView) itemView.findViewById(R.id.iv_avatar);
    // tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    // tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    // }
    //
    class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public ImageView iv;
        public TextView tvTitle;
        public TextView tvContent;

        public LinearViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null){
                return onItemLongClickListener.onVLayoutItemLongClick(v, (Integer) v.getTag());
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onVLayoutItemClick(v, (Integer) v.getTag());
            }
        }
    }
}
