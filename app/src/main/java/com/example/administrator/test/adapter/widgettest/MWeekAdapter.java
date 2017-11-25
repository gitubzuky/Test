
package com.example.administrator.test.adapter.widgettest;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.model.widgettest.MDataBean;

import java.util.List;

/**
 * Created by Zuky on 2017/6/26.
 * <p>Description: 日历的Adapter</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */

public class MWeekAdapter extends BaseAdapter {
    private List<MDataBean> dataList;
    private LayoutInflater inflater;
    private Activity act;

    public MWeekAdapter(Activity act, List<MDataBean> dataList) {
        this.dataList = dataList;
        this.act = act;
        inflater = LayoutInflater.from(act);
    }

    public void setDataList(List<MDataBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_m_week, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        boolean isMonthDay = dataList.get(position).isMonthDay();// 是否是当月日期
        boolean isMPeriod = dataList.get(position).isMPeriod();// 是否是月经期
        boolean isOvipositDay = dataList.get(position).isOvipositDay();// 是否排卵日
        boolean isOvipositPeriod = dataList.get(position).isOvipositPeriod();// 是否排卵期
        boolean isSafeDay = dataList.get(position).isSafeDay();// 是否安全期
        int day = dataList.get(position).getDay();// 日

        if (!isMonthDay) {
            viewHolder.tvDay.setVisibility(View.INVISIBLE);
            viewHolder.tvOviposit.setVisibility(View.INVISIBLE);
            return convertView;
        } else {
            viewHolder.tvDay.setText(String.valueOf(day));
            if (isSafeDay) {// 安全期
                viewHolder.tvDay.setBackgroundResource(0);
                viewHolder.tvDay.setTextColor(
                        act.getResources().getColor(R.color.color_green_39a63a));
                viewHolder.tvOviposit.setVisibility(View.INVISIBLE);
            } else if (isMPeriod) {// 月经期
                viewHolder.tvDay.setBackgroundResource(R.drawable.shape_red_fe8989_dot);
                viewHolder.tvDay.setTextColor(Color.WHITE);
                viewHolder.tvOviposit.setVisibility(View.INVISIBLE);
            } else if (isOvipositPeriod) {// 排卵期
                viewHolder.tvDay.setBackgroundResource(R.drawable.shape_orange_ffa87d_dot);
                viewHolder.tvDay.setTextColor(Color.WHITE);
                if (isOvipositDay) {
                    viewHolder.tvOviposit.setVisibility(View.VISIBLE);// 排卵日
                } else {// 非排卵日
                    viewHolder.tvOviposit.setVisibility(View.INVISIBLE);
                }
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tvDay;
        private TextView tvOviposit;

        public ViewHolder(View itemView) {
            tvDay = (TextView) itemView.findViewById(R.id.adapter_m_week_tv_day);
            tvOviposit = (TextView) itemView.findViewById(R.id.adapter_m_week_tv_oviposit);
        }
    }
}
