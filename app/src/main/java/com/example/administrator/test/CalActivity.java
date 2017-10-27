
package com.example.administrator.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.test.adapter.widgettest.MWeekAdapter;
import com.example.administrator.test.model.widgettest.MDataBean;
import com.example.administrator.test.widget.NOScrollGridView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalActivity extends AppCompatActivity {
    NOScrollGridView weekView;
    EditText etLastMDate, etMPeriod, etMDays;
    Button btnConfirm;

    MWeekAdapter mWeekAdapter;

    private Date lastMDate;
    private int mPeriod, mDays;

    private List<MDataBean> mFirstDataList;// 第一个月的数据
    private List<MDataBean> mSecondDataList;// 第二个月的数据
    private List<MDataBean> mThirdDataList;// 第三个月的数据
    DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
    Calendar cal;
    private final String TAG = "经期日历";

    private final String[] weekTag = {
            "日", "一", "二", "三", "四", "五", "六"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);
        cal = Calendar.getInstance();

        etLastMDate = (EditText) findViewById(R.id.act_cal_et_last_m_date);
        etMDays = (EditText) findViewById(R.id.act_cal_et_m_days);
        etMPeriod = (EditText) findViewById(R.id.act_cal_et_m_period);

        weekView = (NOScrollGridView) findViewById(R.id.act_cal_gv_week);
        mWeekAdapter = new MWeekAdapter(CalActivity.this, null);
        weekView.setAdapter(mWeekAdapter);
        btnConfirm = (Button) findViewById(R.id.act_cal_btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lastMDate = format.parse("2017年06月07日");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // mPeriod = Integer.parseInt(etMPeriod.getText().toString());
                // mDays = Integer.parseInt(etMDays.getText().toString());
                mPeriod = 28;
                mDays = 5;
                mFirstDataList = getFirstMDataList(lastMDate, mPeriod, mDays);
                mWeekAdapter.setDataList(mFirstDataList);
                mWeekAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<MDataBean> getFirstMDataList(Date lastMDate, int mPeriod, int mDays) {
        List<MDataBean> dataList = new ArrayList<>();
        cal.setTime(lastMDate);
        // 定位到该月第一天
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Log.d(TAG, TAG + "" + format.format(cal.getTime()));
        // 获取该月第一天是星期几
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d(TAG, TAG + "-这个月第一天是星期" + firstDayOfWeek);
        // 那么该月日历上这一天之前的都是空白的
        for (int i = 0; i < firstDayOfWeek - 1; i++) {
            MDataBean bean = new MDataBean();
            bean.setDay(-1);
            bean.setMonthDay(false);
            bean.setSafeDay(false);
            bean.setMPeriod(false);
            bean.setOvipositDay(false);
            bean.setOvipositPeriod(false);
            dataList.add(bean);
        }
        // 开始处理这个月的数据
        cal.setTime(lastMDate);
        int dayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// 这个月的天数
        Log.d(TAG, TAG + ":这个月的天数 = " + dayNum);
        int lastMDay = cal.get(Calendar.DAY_OF_MONTH);
        Log.d(TAG, TAG + ":上一次的月经日是几号->" + lastMDay);
        // 处理上一次月经日前的安全期数据, 共有（上一次月经日-1）这么多天的安全期
        for (int j = 0; j < lastMDay - 1; j++) {
            Log.d(TAG, TAG + ":当前遍历到的天数 = " + (j + 1));
            MDataBean bean = new MDataBean();
            bean.setDay(j + 1);
            bean.setMonthDay(true);
            bean.setSafeDay(true);
            bean.setMPeriod(false);
            bean.setOvipositDay(false);
            bean.setOvipositPeriod(false);
            dataList.add(bean);
        }
        // 处理上一次月经期
        int lastDayOfmPeriod = mDays + lastMDay;
        int restDayOfMPeriod;// 上一次月经期还剩几天没有遍历
        for (int i = lastMDay; i < lastDayOfmPeriod + 1; i++) {
            if (i >= dayNum) {// 如果超过当月最后一天则遍历结束
                // 记录下经期还有几天没有遍历
                restDayOfMPeriod = mPeriod - (lastDayOfmPeriod - i);
                Log.d(TAG, TAG + "-未遍历的经期天数：" + restDayOfMPeriod);
                break;
            }
            MDataBean bean = new MDataBean();
            bean.setDay(i);
            bean.setMonthDay(true);
            bean.setSafeDay(false);
            bean.setMPeriod(true);
            bean.setOvipositDay(false);
            bean.setOvipositPeriod(false);
            dataList.add(bean);
        }
        return dataList;
    }
}
