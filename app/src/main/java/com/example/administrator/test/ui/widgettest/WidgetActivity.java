
package com.example.administrator.test.ui.widgettest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.administrator.test.R;
import com.example.administrator.test.model.widgettest.WvBean;
import com.example.administrator.test.presenter.widgettest.IWidgetTestPresenter;
import com.example.administrator.test.presenter.widgettest.WidgetPresenter;
import com.example.administrator.test.ui.other.CalActivity;
import com.example.administrator.test.view.widgettest.IWidgetTestView;
import com.example.administrator.test.widget.CustomSwitch;

import java.util.ArrayList;

/**
 * 控件测试页面
 */
public class WidgetActivity extends AppCompatActivity
        implements IWidgetTestView, CustomSwitch.OnStateChangeListener {
    CustomSwitch customSwitch;
    // WheelView wv;
    ArrayList<WvBean> list;

    RecyclerView recyclerView;
    DelegateAdapter adapters;

    IWidgetTestPresenter widgetTestPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        customSwitch = (CustomSwitch) findViewById(R.id.mytestswitch);
        // wv = (WheelView) findViewById(R.id.wv);

        /** 默认蓝色，这里修改为红色 */
        customSwitch.setOpenBackgroundColor(Color.parseColor("#f66359"));
        customSwitch.setState(true);
        customSwitch.setOnStateChangeListener(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSwitch.changeState(false);
                startActivity(new Intent(WidgetActivity.this, CalActivity.class));
            }
        });

        showWv();

        initPresenter();

        recyclerView = (RecyclerView) findViewById(R.id.rv_v_layout);
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        adapters = new DelegateAdapter(layoutManager);
        recyclerView.setAdapter(adapters);

        widgetTestPresenter.initVLayoutAdapter(this);
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        widgetTestPresenter = new WidgetPresenter(this);
    }

    private void showWv() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            WvBean bean = new WvBean();
            bean.setTag(0);
            bean.setDay(i + 1);
            list.add(bean);
        }
        // wv.setAdapter(new ArrayWheelAdapter(list));
        // wv.setItemsVisible(3);
        // wv.setCyclic(false);
        // wv.setLineSpacingMultiplier(2.5f);

    }

    @Override
    public void onStateChange(CustomSwitch sv, boolean isOpen) {
        if (isOpen) {
            Toast.makeText(this, "打开", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showToast(String toast, int duration) {
        Toast.makeText(this, toast, duration).show();
    }

    @Override
    public void vLayoutAddAdapter(DelegateAdapter.Adapter adapter) {
        if (adapters != null){
            adapters.addAdapter(adapter);
        }
    }

    @Override
    public void vLayoutNotifyDataChange() {
        if (adapters != null){
            adapters.notifyDataSetChanged();
        }
    }

    @Override
    public void changeState(boolean state) {
        if (customSwitch != null){
            customSwitch.changeState(state);
        }
    }
}
