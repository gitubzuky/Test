package com.example.administrator.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.test.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */

public class TestRvAdapter extends BaseRvAdapter<TestRvAdapter.ViewHolder> {
    View itemView;
    Context context;
    List<String> datalist;

    public TestRvAdapter(Context context, List<String> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_test, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    protected void doinBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends BaseRvAdapter.ViewHolder{
        TextView tvTest;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTest = (TextView) itemView.findViewById(R.id.tv_item_test);
        }
    }
}
