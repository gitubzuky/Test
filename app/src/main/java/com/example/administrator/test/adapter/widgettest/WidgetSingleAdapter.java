
package com.example.administrator.test.adapter.widgettest;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.example.administrator.test.R;
import com.example.administrator.test.adapter.BaseRvAdapter;
import com.example.administrator.test.adapter.OnVLayoutItemClickListener;
import com.example.administrator.test.adapter.OnVLayoutItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class WidgetSingleAdapter
        extends DelegateAdapter.Adapter<WidgetSingleAdapter.SingleViewHolder> {
    Activity act;
    private OnVLayoutItemClickListener onItemClickListener;
    private OnVLayoutItemLongClickListener onItemLongClickListener;

    public WidgetSingleAdapter(Activity act) {
        this.act = act;
    }

    @Override
    public SingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleViewHolder(
                LayoutInflater.from(act).inflate(R.layout.layout_item_single, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
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
    class SingleViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rv;

        public SingleViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.layout_item_single_rv);

            List<String> dataList = new ArrayList<>();

            for (int i = 0; i < 15; i++) {
                dataList.add(i + "");
            }

            SimpleRvAdapter testRvAdapter = new SimpleRvAdapter(act, dataList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(linearLayoutManager);

            testRvAdapter.setRvItemClickListener(new BaseRvAdapter.OnRvItemClickListener() {
                @Override
                public void OnRvItemClick(View view, int position) {
                    if (onItemClickListener != null){
                        onItemClickListener.onVLayoutItemClick(view, position);
                    }
                }
            });

            testRvAdapter.setRvItemLongClickListener(new BaseRvAdapter.OnRvItemLongClickListener() {
                @Override
                public boolean OnRvItemLongClick(View view, int position) {
                    if (onItemLongClickListener != null) {
                        return onItemLongClickListener.onVLayoutItemLongClick(view, position);
                    }
                    return true;
                }
            });
            rv.setAdapter(testRvAdapter);
        }
    }

    public void setOnItemClickListener(OnVLayoutItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnVLayoutItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
