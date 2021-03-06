
package com.example.administrator.test.adapter.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.adapter.BaseHolderAdapter;
import com.example.administrator.test.adapter.BaseRvAdapter;
import com.example.administrator.test.model.home.FunctionBean;

import java.util.List;

/**
 * Created by Zuky on 2016/9/13.
 * <p>
 * Description: 首页功能列表RvAdapter
 * </p>
 * <p>
 * Tips:
 * </p>
 * <p>
 * Version: 1.0
 * </p>
 * <p>
 * Update by Zuky on 2017/11/25.
 * </p>
 */
public class MainAdapter extends BaseRvAdapter {
    private View view;
    private List<FunctionBean> dataList;
    private Context context;

    public MainAdapter(Context context, List<FunctionBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.layout_item_type1, parent,
                        false);
                holder = new Type1ViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.layout_item_type2, parent,
                        false);
                holder = new Type2ViewHolder(view);
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                if (!TextUtils.isEmpty(dataList.get(position).getText())) {
                    ((Type1ViewHolder) holder).tvText.setText(dataList.get(position).getText());
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(dataList.get(position).getText())) {
                    ((Type2ViewHolder) holder).tvText.setText(dataList.get(position).getText());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class Type1ViewHolder extends BaseRvAdapter.ViewHolder {
        private TextView tvText;
        private ImageView ivItem;

        public Type1ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tv_test);
            ivItem = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }

    public class Type2ViewHolder extends BaseRvAdapter.ViewHolder {
        private TextView tvText;

        public Type2ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }
}
