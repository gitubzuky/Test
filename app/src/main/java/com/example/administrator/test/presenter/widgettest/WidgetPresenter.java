package com.example.administrator.test.presenter.widgettest;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.adapter.OnVLayoutItemClickListener;
import com.example.administrator.test.adapter.widgettest.WidgetLinearAdapter;
import com.example.administrator.test.adapter.widgettest.WidgetSingleAdapter;
import com.example.administrator.test.view.widgettest.IWidgetTestView;
import com.lib.mylib.util.JniUtil;

/**
 * Created by Zuky on 2017/10/26.
 * <p>Description: 控件测试页面逻辑presenter</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */
public class WidgetPresenter implements IWidgetTestPresenter {
    IWidgetTestView widgetTestView;

    JniUtil jni;

    public WidgetPresenter(IWidgetTestView widgetTestView) {
        this.widgetTestView = widgetTestView;
        jni = new JniUtil();
    }

    @Override
    public void initVLayoutAdapter(Activity act) {
        WidgetSingleAdapter singleAdapter = new WidgetSingleAdapter(act);
        singleAdapter.setOnItemClickListener(new OnVLayoutItemClickListener() {
            @Override
            public void onVLayoutItemClick(View view, int position) {
                widgetTestView.showToast(jni.stringFromJNI1(position), Toast.LENGTH_SHORT);
            }
        });
        widgetTestView.vLayoutAddAdapter(singleAdapter);
        WidgetLinearAdapter linearAdapter = new WidgetLinearAdapter(act);
        linearAdapter.setOnItemClickListener(new OnVLayoutItemClickListener() {
            @Override
            public void onVLayoutItemClick(View view, int position) {
//                widgetTestView.showToast("纵向item点击："+position, Toast.LENGTH_SHORT);
                jni.setCppCallListener(new JniUtil.CppCallListener() {
                    @Override
                    public void onCppCall(int num) {
                        widgetTestView.showToast("c++代码调用了一波java代码，参数：" + num, Toast.LENGTH_SHORT);
                    }
                });
//                widgetTestView.showToast(JniUtil.stringFromJNI1(position), Toast.LENGTH_SHORT);
                jni.stringFromJNI2(position);
            }
        });
        widgetTestView.vLayoutAddAdapter(linearAdapter);

        widgetTestView.vLayoutNotifyDataChange();
    }
}
