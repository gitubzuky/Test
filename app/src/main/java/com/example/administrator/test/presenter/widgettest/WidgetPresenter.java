package com.example.administrator.test.presenter.widgettest;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.adapter.OnVLayoutItemClickListener;
import com.example.administrator.test.adapter.widgettest.WidgetLinearAdapter;
import com.example.administrator.test.adapter.widgettest.WidgetSingleAdapter;
import com.example.administrator.test.view.widgettest.IWidgetTestView;

/**
 * 控件测试页面逻辑
 * Created by zuky on 2017/10/26.
 */
public class WidgetPresenter implements IWidgetTestPresenter {
    IWidgetTestView widgetTestView;
    public WidgetPresenter(IWidgetTestView widgetTestView) {
        this.widgetTestView = widgetTestView;
    }

    @Override
    public void initVLayoutAdapter(Activity act) {
        WidgetSingleAdapter singleAdapter = new WidgetSingleAdapter(act);
        singleAdapter.setOnItemClickListener(new OnVLayoutItemClickListener() {
            @Override
            public void onVLayoutItemClick(View view, int position) {
                widgetTestView.showToast("横向item点击："+position, Toast.LENGTH_SHORT);
            }
        });
        widgetTestView.vLayoutAddAdapter(singleAdapter);
        WidgetLinearAdapter linearAdapter = new WidgetLinearAdapter(act);
        linearAdapter.setOnItemClickListener(new OnVLayoutItemClickListener() {
            @Override
            public void onVLayoutItemClick(View view, int position) {
                widgetTestView.showToast("纵向item点击："+position, Toast.LENGTH_SHORT);
            }
        });
        widgetTestView.vLayoutAddAdapter(linearAdapter);

        widgetTestView.vLayoutNotifyDataChange();
    }
}
