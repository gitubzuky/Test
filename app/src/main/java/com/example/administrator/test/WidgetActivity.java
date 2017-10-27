
package com.example.administrator.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.administrator.test.adapter.OnVLayoutItemClickListener;
import com.example.administrator.test.adapter.widgettest.WidgetLinearAdapter;
import com.example.administrator.test.adapter.widgettest.WidgetSingleAdapter;
import com.example.administrator.test.model.widgettest.WvBean;
import com.example.administrator.test.presenter.widgettest.IWidgetTestPresenter;
import com.example.administrator.test.presenter.widgettest.WidgetPresenter;
import com.example.administrator.test.view.widgettest.IWidgetTestView;
import com.example.administrator.test.widget.MyTestSwitch;

import java.util.ArrayList;

public class WidgetActivity extends AppCompatActivity
        implements IWidgetTestView, MyTestSwitch.OnStateChangeListener, OnVLayoutItemClickListener {
    MyTestSwitch myTestSwitch;
    // WheelView wv;
    ArrayList<WvBean> list;

    RecyclerView rv;
    DelegateAdapter adapters;

    IWidgetTestPresenter widgetTestPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        myTestSwitch = (MyTestSwitch) findViewById(R.id.mytestswitch);
        // wv = (WheelView) findViewById(R.id.wv);

        /** 默认蓝色，这里修改为红色 */
        myTestSwitch.setOpenBackgroundColor(Color.parseColor("#f66359"));
        myTestSwitch.setState(true);
        myTestSwitch.setOnStateChangeListener(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTestSwitch.changeState(false);
                startActivity(new Intent(WidgetActivity.this, CalActivity.class));
            }
        });

        showWv();

        initPresenter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_v_layout);
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        adapters = new DelegateAdapter(layoutManager);
        WidgetSingleAdapter singleAdapter = new WidgetSingleAdapter(WidgetActivity.this);
        singleAdapter.setOnItemClickListener(this);
        adapters.addAdapter(singleAdapter);
        WidgetLinearAdapter linearAdapter = new WidgetLinearAdapter(WidgetActivity.this);
        linearAdapter.setOnItemClickListener(this);
        adapters.addAdapter(linearAdapter);

        recyclerView.setAdapter(adapters);
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        widgetTestPresenter = new WidgetPresenter();
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
    public void onStateChange(MyTestSwitch sv, boolean isOpen) {
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
            adapters.notifyDataSetChanged();
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
        if (myTestSwitch != null){
            myTestSwitch.changeState(state);
        }
    }

    @Override
    public void onVLayoutItemClick(View view, int position) {
        showToast(""+position, Toast.LENGTH_SHORT);
    }
}
