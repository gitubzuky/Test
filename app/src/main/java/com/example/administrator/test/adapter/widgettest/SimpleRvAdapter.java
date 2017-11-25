
package com.example.administrator.test.adapter.widgettest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.adapter.BaseRvAdapter;

import java.util.List;

/**
 * Created by Zuky on 2016/10/14.
 * <p>Description: 简单单文本列表Adapter</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */
public class SimpleRvAdapter extends BaseRvAdapter<SimpleRvAdapter.ViewHolder> {
    View itemView;
    Context context;
    List<String> datalist;

    public SimpleRvAdapter(Context context, List<String> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_test, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        if (datalist != null && !datalist.isEmpty()) {
            holder.tvTest.setText(datalist.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends BaseRvAdapter.ViewHolder {
        TextView tvTest;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTest = (TextView) itemView.findViewById(R.id.tv_item_test);
        }
    }
}
